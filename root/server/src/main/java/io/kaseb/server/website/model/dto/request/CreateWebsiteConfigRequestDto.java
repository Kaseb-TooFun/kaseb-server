package io.kaseb.server.website.model.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateWebsiteConfigRequestDto {
	private List<String> configValues;
	private String name;
	private String goalSelector;
	private String goalType;
	private String goalLink;
}
