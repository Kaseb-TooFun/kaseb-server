package io.kaseb.server.authenticate.service;

import io.kaseb.server.authenticate.exceptions.AuthenticationException;
import io.kaseb.server.authenticate.model.dao.SessionRepo;
import io.kaseb.server.authenticate.model.dto.request.LoginRequestDto;
import io.kaseb.server.authenticate.model.dto.request.SignupRequestDto;
import io.kaseb.server.authenticate.model.dto.response.LoginResponseDto;
import io.kaseb.server.authenticate.model.dto.response.SignupResponseDto;
import io.kaseb.server.authenticate.model.entities.SessionEntity;
import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.user.model.dto.BaseUserDto;
import io.kaseb.server.user.model.entities.UserEntity;
import io.kaseb.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticateService {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_BASE = "bearer ";
    private final UserService userService;
    private final SessionRepo sessionRepo;

    public LoginResponseDto login(LoginRequestDto request, HttpServletResponse response) throws ServiceException {
        final String hashedPassword = hash(request.getPassword());
        final UserEntity userEntity = userService.login(request.getUsername(), hashedPassword);
        final Pair<SessionEntity, String> sessionPair = createSession(userEntity);
        final BaseUserDto userDto = new BaseUserDto(userEntity);
        setAuthenticationInfoInResponse(response, sessionPair);
        return new LoginResponseDto(userDto, sessionPair.getSecond());
    }

    private void setAuthenticationInfoInResponse(HttpServletResponse response, Pair<SessionEntity, String> sessionPair) {
        response.addHeader(AUTHORIZATION_HEADER, AUTHORIZATION_HEADER_BASE + sessionPair.getSecond());
//        response.addCookie(createCookie(sessionPair.getSecond()));
    }

    private Cookie createCookie(String plainToken) {
        Cookie cookie = new Cookie(AUTHORIZATION_HEADER, plainToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(Integer.MAX_VALUE);
        return cookie;
    }

    private Pair<SessionEntity, String> createSession(UserEntity userEntity) {
        final String plainToken = createRandomToken();
        SessionEntity sessionEntity = new SessionEntity(hash(plainToken), userEntity);
        return Pair.of(sessionRepo.save(sessionEntity), plainToken);
    }

    private String createRandomToken() {
        final byte[] randomBytes = new byte[100];
        new Random(System.currentTimeMillis()).nextBytes(randomBytes);
        return Base64Utils.encodeToString(randomBytes);
    }

    public SignupResponseDto signup(SignupRequestDto request) throws ServiceException {
        final String hashedPassword = hash(request.getPassword());
        final UserEntity user = userService.signup(request.getUsername(), hashedPassword);
        final BaseUserDto userDto = new BaseUserDto(user);
        return new SignupResponseDto(userDto);
    }

    private String hash(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes(StandardCharsets.UTF_8));
            byte[] md5Bytes = md.digest();
            return Base64Utils.encodeToString(md5Bytes);
        } catch (NoSuchAlgorithmException e) {
            logger.error("no such algorithm {} base64", "MD5", e);
        }
        return "";
    }

    public UserEntity authenticate(HttpServletRequest request) throws AuthenticationException {
        final String plainToken = extractPlainToken(request);
        return authenticate(plainToken);
    }

    private String extractPlainToken(HttpServletRequest request) throws AuthenticationException {
        final String bearerTokenFromHeader = extractTokenFromHeader(request);
        if (!StringUtils.isEmpty(bearerTokenFromHeader))
            return bearerTokenFromHeader.replace(AUTHORIZATION_HEADER_BASE, "");
//        final String bearerTokenFromCookie = extractTokenFromCookie(request);
//        if (!StringUtils.isEmpty(bearerTokenFromCookie))
//            return bearerTokenFromCookie;
        throw new AuthenticationException();
    }

    private String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null)
            return null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER);
    }

    private UserEntity authenticate(String plainToken) throws AuthenticationException {
        final SessionEntity sessionEntity = sessionRepo.findByToken(hash(plainToken))
                .orElseThrow(AuthenticationException::new);
        sessionEntity.setLastActivityDate(new Date());
        sessionRepo.saveAndFlush(sessionEntity);
        return sessionEntity.getUser();
    }
}
