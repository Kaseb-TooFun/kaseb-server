package io.kaseb.server.authenticate.service;

import io.kaseb.server.authenticate.model.dto.request.LoginRequestDto;
import io.kaseb.server.authenticate.model.dto.request.SignupRequestDto;
import io.kaseb.server.authenticate.model.dto.response.LoginResponseDto;
import io.kaseb.server.authenticate.model.dto.response.SignupResponseDto;
import io.kaseb.server.exceptions.ServiceException;
import io.kaseb.server.user.model.dto.BaseUserDto;
import io.kaseb.server.user.model.entities.UserEntity;
import io.kaseb.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticateService {
    private final UserService userService;

    public LoginResponseDto login(LoginRequestDto request) throws ServiceException {
        final String hashedPassword = hash(request.getPassword());
        final UserEntity user = userService.login(request.getUsername(), hashedPassword);
        final BaseUserDto userDto = new BaseUserDto(user);
        return new LoginResponseDto(userDto);
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
        return null;
    }
}
