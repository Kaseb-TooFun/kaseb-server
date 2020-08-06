package io.kaseb.server.authenticate.service;

import io.kaseb.server.authenticate.exceptions.AuthenticationException;
import io.kaseb.server.authenticate.model.dao.SessionRepo;
import io.kaseb.server.authenticate.model.dto.request.LoginRequestDto;
import io.kaseb.server.authenticate.model.dto.request.SignupRequestDto;
import io.kaseb.server.authenticate.model.dto.response.LoginResponseDto;
import io.kaseb.server.authenticate.model.dto.response.SignupResponseDto;
import io.kaseb.server.authenticate.model.entities.SessionEntity;
import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.operator.model.entities.OperatorEntity;
import io.kaseb.server.operator.service.OperatorService;
import io.kaseb.server.user.exceptions.DuplicateUsernameException;
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
import java.util.Date;
import java.util.Random;
import static io.kaseb.server.util.HashUtils.hash;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticateService {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_BASE = "bearer ";
    private final UserService userService;
    private final OperatorService operatorService;
    private final SessionRepo sessionRepo;

    public LoginResponseDto login(LoginRequestDto request, HttpServletResponse response) throws ServiceException {
        final String hashedPassword = hash(request.getPassword());
        final Pair<SessionEntity, String> sessionPair;
        final BaseUserDto userDto;
        if (request.isOperator()) {
            final OperatorEntity operatorEntity = operatorService.login(request.getUsername(), hashedPassword);
            sessionPair = createSession(operatorEntity);
            userDto = new BaseUserDto(operatorEntity);
        } else {
            final UserEntity userEntity = userService.login(request.getUsername(), hashedPassword);
            sessionPair = createSession(userEntity);
            userDto = new BaseUserDto(userEntity);
        }
        setAuthenticationInfoInResponse(response, sessionPair.getSecond());
        return new LoginResponseDto(userDto, sessionPair.getSecond());
    }

    private void setAuthenticationInfoInResponse(HttpServletResponse response, String plainToken) {
        response.addHeader(AUTHORIZATION_HEADER, AUTHORIZATION_HEADER_BASE + plainToken);
        response.addCookie(createCookie(plainToken));
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

    private Pair<SessionEntity, String> createSession(OperatorEntity operatorEntity) {
        final String plainToken = createRandomToken();
        SessionEntity sessionEntity = new SessionEntity(hash(plainToken), operatorEntity);
        return Pair.of(sessionRepo.save(sessionEntity), plainToken);
    }

    private String createRandomToken() {
        final byte[] randomBytes = new byte[100];
        new Random(System.currentTimeMillis()).nextBytes(randomBytes);
        return Base64Utils.encodeToString(randomBytes);
    }

    public SignupResponseDto signup(SignupRequestDto request, HttpServletResponse response) throws ServiceException {
        final String hashedPassword = hash(request.getPassword());
        signup(request, hashedPassword);
        final Pair<?, String> sessionPair;
        final BaseUserDto userDto;
        if (request.isOperator()) {
            final OperatorEntity operatorEntity = operatorService.login(request.getUsername(), hashedPassword);
            sessionPair = createSession(operatorEntity);
            userDto = new BaseUserDto(operatorEntity);
        } else {
            final UserEntity userEntity = userService.login(request.getUsername(), hashedPassword);
            sessionPair = createSession(userEntity);
            userDto = new BaseUserDto(userEntity);

        }
        setAuthenticationInfoInResponse(response, sessionPair.getSecond());
        return new SignupResponseDto(userDto, sessionPair.getSecond());
    }

    private void signup(SignupRequestDto request, String hashedPassword) throws DuplicateUsernameException {
        if (request.isOperator())
            operatorService.signup(request.getUsername(), hashedPassword);
        else
            userService.signup(request.getUsername(), hashedPassword);
    }

    public SessionEntity authenticate(HttpServletRequest request) throws AuthenticationException {
        final String plainToken = extractPlainToken(request);
        return authenticate(plainToken);
    }

    private String extractPlainToken(HttpServletRequest request) throws AuthenticationException {
        logger.info("trying to extract plain token from request");
        String bearerTokenFromHeader = extractTokenFromHeader(request);
        if (bearerTokenFromHeader != null && !StringUtils.isEmpty(bearerTokenFromHeader))
            return bearerTokenFromHeader.replace(AUTHORIZATION_HEADER_BASE, "");
        logger.info("request does not have authorization header");
        String bearerTokenFromCookie = extractTokenFromCookie(request);
        if (bearerTokenFromCookie != null && !StringUtils.isEmpty(bearerTokenFromCookie))
            return bearerTokenFromCookie;
        logger.info("request does not have authorization cookie");
        throw new AuthenticationException();
    }

    private String extractTokenFromCookie(HttpServletRequest request) {
        if (request == null || request.getCookies() == null)
            return null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        return request == null ? null : request.getHeader(AUTHORIZATION_HEADER);
    }

    private SessionEntity authenticate(String plainToken) throws AuthenticationException {
        final SessionEntity sessionEntity = sessionRepo.findByToken(hash(plainToken))
                .orElseThrow(AuthenticationException::new);
        sessionEntity.setLastActivityDate(new Date());
        sessionRepo.saveAndFlush(sessionEntity);
        return sessionEntity;
    }
}
