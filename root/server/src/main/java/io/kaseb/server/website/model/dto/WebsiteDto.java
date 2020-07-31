package io.kaseb.server.website.model.dto;

import io.kaseb.server.user.model.dto.BaseWebsiteDto;
import io.kaseb.server.user.model.dto.ConfigDto;
import io.kaseb.server.website.model.entities.WebsiteConfigEntity;
import io.kaseb.server.website.model.entities.WebsiteEntity;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class WebsiteDto extends BaseWebsiteDto {
    private List<ConfigDto> configs;

    public WebsiteDto(WebsiteEntity websiteEntity, List<WebsiteConfigEntity> configEntities) {
        super(websiteEntity);
        if (!CollectionUtils.isEmpty(configEntities))
            this.configs = configEntities.stream().map(ConfigDto::new).collect(Collectors.toList());
        else
            this.configs = Collections.emptyList();
    }
}
