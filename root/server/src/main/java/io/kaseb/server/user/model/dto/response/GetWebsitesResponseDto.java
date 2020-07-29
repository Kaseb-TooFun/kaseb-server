package io.kaseb.server.user.model.dto.response;

import io.kaseb.server.user.model.dto.BaseWebsiteDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
@AllArgsConstructor
public class GetWebsitesResponseDto {
    private List<BaseWebsiteDto> websites;
}
