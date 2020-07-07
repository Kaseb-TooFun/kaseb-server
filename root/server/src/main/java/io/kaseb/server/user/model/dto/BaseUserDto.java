package io.kaseb.server.user.model.dto;

import io.kaseb.server.user.model.entities.UserEntity;
import lombok.Data;

@Data
public class BaseUserDto {
    private String uniqueId;
    private String username;

    public BaseUserDto(UserEntity user) {

    }
}
