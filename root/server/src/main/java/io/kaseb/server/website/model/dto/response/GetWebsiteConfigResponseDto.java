package io.kaseb.server.website.model.dto.response;

import io.kaseb.server.user.model.Config;
import io.kaseb.server.user.model.dto.BaseWebsiteDto;
import io.kaseb.server.user.model.entities.WebsiteEntity;
import lombok.Data;

import java.util.List;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
public class GetWebsiteConfigResponseDto extends BaseWebsiteDto {
    private List<Config> configs;

    public GetWebsiteConfigResponseDto(WebsiteEntity websiteEntity) {
        super(websiteEntity);
        this.configs = websiteEntity.getConfigs();
    }
}
