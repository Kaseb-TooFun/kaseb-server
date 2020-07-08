package io.kaseb.server.user.model.dto;

import io.kaseb.server.user.model.entities.WebsiteEntity;
import lombok.Data;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
public class BaseWebsiteDto {
    private final String id;
    private final String title;
    private final String url;

    public BaseWebsiteDto(WebsiteEntity websiteEntity) {
        this.id = websiteEntity.getId();
        this.title = websiteEntity.getTitle();
        this.url = websiteEntity.getUrl();
    }
}
