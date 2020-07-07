package io.kaseb.server.user.model.dto;

import io.kaseb.server.user.model.entities.UserEntity;
import lombok.Data;

@Data
public class BaseUserDto {
    private String id;
    private String username;

    public BaseUserDto(UserEntity user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
