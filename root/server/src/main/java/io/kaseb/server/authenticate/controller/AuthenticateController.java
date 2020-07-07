package io.kaseb.server.authenticate.controller;

import io.kaseb.server.authenticate.model.dto.request.LoginRequestDto;
import io.kaseb.server.authenticate.model.dto.request.SignupRequestDto;
import io.kaseb.server.authenticate.model.dto.response.LoginResponseDto;
import io.kaseb.server.authenticate.model.dto.response.SignupResponseDto;
import io.kaseb.server.authenticate.service.AuthenticateService;
import io.kaseb.server.exceptions.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@RestController
@RequestMapping("/api/v1/authenticate")
@RequiredArgsConstructor
public class AuthenticateController {
    private final AuthenticateService authenticateService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto request) throws ServiceException {
        return ResponseEntity.ok(authenticateService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(
            @RequestBody LoginRequestDto request, HttpServletResponse response) throws ServiceException {
        return ResponseEntity.ok(authenticateService.login(request, response));
    }
}
