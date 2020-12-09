package io.kaseb.server.user.model.dto;

import io.kaseb.server.website.model.entities.WebsiteEntity;
import lombok.Data;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
public class BaseWebsiteDto {
	private String id;
	private String title;
	private String url;

	public BaseWebsiteDto(WebsiteEntity websiteEntity) {
		this.id = websiteEntity.getId();
		this.title = websiteEntity.getTitle();
		this.url = websiteEntity.getUrl();
	}
}
