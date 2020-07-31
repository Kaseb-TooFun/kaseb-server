package io.kaseb.server.website.model.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateWebsiteConfigRequestDto {
    private List<String> configValues;
}
