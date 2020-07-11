package io.kaseb.server.website.model.dto.response;

import io.kaseb.server.user.model.dto.BaseWebsiteDto;
import io.kaseb.server.user.model.entities.WebsiteEntity;
import lombok.Data;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
public class GetWebsiteConfigResponseDto extends BaseWebsiteDto {
    private String conifg;

    public GetWebsiteConfigResponseDto(WebsiteEntity websiteEntity) {
        super(websiteEntity);
        this.conifg = websiteEntity.getConfig();
    }
}
