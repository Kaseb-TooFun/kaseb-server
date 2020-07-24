package io.kaseb.server.user.model.dto.request;

import lombok.Data;

import java.util.List;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
public class RegisterWebsiteConfigRequestDto {
    private List<String> configs;
}
