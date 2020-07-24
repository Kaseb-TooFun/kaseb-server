package io.kaseb.server.user.model.dto.response;

import io.kaseb.server.user.model.dto.BaseWebsiteDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
@RequiredArgsConstructor
public class GetWebsitesResponseDto {
    private final List<BaseWebsiteDto> websites;
}
