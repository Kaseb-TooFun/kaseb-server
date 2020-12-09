package io.kaseb.server.module.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
@AllArgsConstructor
public class LatestModuleDto {
	private String url;
	private String style;
}
