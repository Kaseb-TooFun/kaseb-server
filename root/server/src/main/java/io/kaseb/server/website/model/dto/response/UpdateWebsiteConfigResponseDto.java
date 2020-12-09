package io.kaseb.server.website.model.dto.response;

import io.kaseb.server.user.model.dto.ConfigDto;
import io.kaseb.server.website.model.entities.WebsiteConfigEntity;
import lombok.Data;

@Data
public class UpdateWebsiteConfigResponseDto {
	private ConfigDto config;

	public UpdateWebsiteConfigResponseDto(WebsiteConfigEntity configEntity) {
		this.config = new ConfigDto(configEntity);
	}
}
