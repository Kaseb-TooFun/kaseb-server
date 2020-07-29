package io.kaseb.server.user.model.dto;

import io.kaseb.server.website.model.entities.WebsiteConfigEntity;
import lombok.Data;

@Data
public class ConfigDto {
    private String id;
    private String value;

    public ConfigDto(WebsiteConfigEntity entity) {
        this.id = entity.getId();
        this.value = entity.getConfigValue();
    }
}
