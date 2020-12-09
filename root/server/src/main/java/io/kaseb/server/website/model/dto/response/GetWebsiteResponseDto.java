package io.kaseb.server.website.model.dto.response;

import io.kaseb.server.website.model.dto.WebsiteDto;
import io.kaseb.server.website.model.entities.WebsiteConfigEntity;
import io.kaseb.server.website.model.entities.WebsiteEntity;
import lombok.Data;

import java.util.List;

@Data
public class GetWebsiteResponseDto {
	private WebsiteDto website;

	public GetWebsiteResponseDto(WebsiteEntity websiteEntity, List<WebsiteConfigEntity> configs) {
		this.website = new WebsiteDto(websiteEntity, configs);
	}
}
