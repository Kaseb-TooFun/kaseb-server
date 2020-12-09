package io.kaseb.server.user.model.dto;

import io.kaseb.server.website.model.entities.WebsiteConfigEntity;
import lombok.Data;

@Data
public class ConfigDto {
	private String id;
	private String value;
	private String name;
	private String goalType;
	private String goalSelector;
	private String goalLink;

	public ConfigDto(WebsiteConfigEntity entity) {
		this.id = entity.getId();
		this.value = entity.getConfigValue();
		this.name = entity.getName();
		this.goalType = entity.getGoalType();
		this.goalSelector = entity.getGoalSelector();
		this.goalLink = entity.getGoalLink();
	}
}
