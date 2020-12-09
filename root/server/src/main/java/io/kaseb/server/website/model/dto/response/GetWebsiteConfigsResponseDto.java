package io.kaseb.server.website.model.dto.response;

import io.kaseb.server.user.model.dto.ConfigDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetWebsiteConfigsResponseDto {
	private List<ConfigDto> configs;
}
