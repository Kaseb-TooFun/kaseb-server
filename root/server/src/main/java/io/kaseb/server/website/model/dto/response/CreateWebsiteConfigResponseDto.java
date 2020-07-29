package io.kaseb.server.website.model.dto.response;

import io.kaseb.server.user.model.dto.ConfigDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateWebsiteConfigResponseDto {
    private ConfigDto config;
}
