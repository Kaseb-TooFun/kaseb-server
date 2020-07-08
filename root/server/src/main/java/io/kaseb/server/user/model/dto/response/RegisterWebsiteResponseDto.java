package io.kaseb.server.user.model.dto.response;

import io.kaseb.server.user.model.dto.BaseUserDto;
import io.kaseb.server.user.model.dto.BaseWebsiteDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
@RequiredArgsConstructor
public class RegisterWebsiteResponseDto {
    private final BaseWebsiteDto website;
    private final BaseUserDto user;
}
