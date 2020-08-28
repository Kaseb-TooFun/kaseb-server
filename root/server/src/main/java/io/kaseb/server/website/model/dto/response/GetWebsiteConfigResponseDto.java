package io.kaseb.server.website.model.dto.response;

import io.kaseb.server.user.model.dto.ConfigDto;
import io.kaseb.server.website.model.entities.WebsiteConfigEntity;
import lombok.Data;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
public class GetWebsiteConfigResponseDto extends ConfigDto {
    private String websiteUrl;

    public GetWebsiteConfigResponseDto(WebsiteConfigEntity entity, String websiteUrl) {
        super(entity);
        this.websiteUrl = websiteUrl;
    }
}
