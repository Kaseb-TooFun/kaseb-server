package io.kaseb.server.authenticate.service;

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

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticateService {
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String AUTHORIZATION_HEADER_BASE = "bearer ";
    private final UserService userService;

    public LoginResponseDto login(LoginRequestDto request, HttpServletResponse response) throws ServiceException {
        final String hashedPassword = hash(request.getPassword());
        final UserEntity userEntity = userService.login(request.getUsername(), hashedPassword);
        final Pair<SessionEntity, String> sessionPair = createSession(userEntity);
        final BaseUserDto userDto = new BaseUserDto(userEntity);
        response.addHeader(AUTHORIZATION_HEADER, AUTHORIZATION_HEADER_BASE + sessionPair.getSecond());
        return new LoginResponseDto(userDto);
    }

    private Pair<SessionEntity, String> createSession(UserEntity userEntity) {
        final String plainToken = createRandomToken();
        SessionEntity sessionEntity = new SessionEntity(hash(plainToken), userEntity);
        return Pair.of(sessionEntity, plainToken);
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
}
