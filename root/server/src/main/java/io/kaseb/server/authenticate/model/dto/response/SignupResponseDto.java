package io.kaseb.server.authenticate.model.dto.response;

import io.kaseb.server.user.model.dto.BaseUserDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SignupResponseDto {
    private final BaseUserDto user;
}
