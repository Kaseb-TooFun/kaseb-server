package io.kaseb.server.user.model.dto.request;

import io.kaseb.server.user.model.Config;
import lombok.Data;

import java.util.List;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
public class UpdateWebsiteRequestDto {
    private String title;
    private List<Config> configs;
}
