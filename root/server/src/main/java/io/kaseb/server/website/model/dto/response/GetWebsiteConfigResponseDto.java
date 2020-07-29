package io.kaseb.server.website.model.dto.response;

import io.kaseb.server.user.model.dto.BaseWebsiteDto;
import io.kaseb.server.user.model.dto.ConfigDto;
import io.kaseb.server.website.model.entities.WebsiteConfigEntity;
import io.kaseb.server.website.model.entities.WebsiteEntity;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
public class GetWebsiteConfigResponseDto extends BaseWebsiteDto {
    private List<ConfigDto> configs;

    public GetWebsiteConfigResponseDto(WebsiteEntity websiteEntity) {
        super(websiteEntity);
        final List<WebsiteConfigEntity> configEntities = websiteEntity.getConfigs();
        if (CollectionUtils.isEmpty(configEntities))
            this.configs = Collections.emptyList();
        else
            this.configs = websiteEntity.getConfigs().stream().map(ConfigDto::new).collect(Collectors.toList());
    }
}
