package io.kaseb.server.website.model.dto.request;

import lombok.Data;

@Data
public class UpdateWebsiteConfigRequestDto {
	private String configValue;
	private String name;
	private String goalSelector;
	private String goalType;
	private String goalLink;
}
