package io.kaseb.server.user.model.dto;

import io.kaseb.server.operator.model.entities.OperatorEntity;
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

    public BaseUserDto(OperatorEntity operator) {
        this.id = operator.getId();
        this.username = operator.getUsername();
    }
}
