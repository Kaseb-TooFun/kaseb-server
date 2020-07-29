package io.kaseb.server.website.model.dto.response;

import io.kaseb.server.website.model.dto.WebsiteDto;
import io.kaseb.server.website.model.entities.WebsiteEntity;
import lombok.Data;

@Data
public class GetWebsiteResponseDto {
    public GetWebsiteResponseDto(WebsiteEntity websiteEntity) {
        this.website = new WebsiteDto(websiteEntity);
    }

    private WebsiteDto website;
}
