package io.kaseb.server.operator.model.dto;

import io.kaseb.server.operator.model.entities.OperatorEntity;
import io.kaseb.server.user.model.dto.BaseUserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class OperatorDto extends BaseUserDto {
    private boolean activated;

    public OperatorDto(OperatorEntity operatorEntity) {
        super(operatorEntity);
        this.activated = operatorEntity.isActivated();
    }
}
