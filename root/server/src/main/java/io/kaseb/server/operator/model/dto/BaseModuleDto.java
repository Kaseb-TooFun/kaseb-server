package io.kaseb.server.operator.model.dto;

import io.kaseb.server.module.model.entities.ModuleEntity;
import lombok.Data;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
public class BaseModuleDto {
    private String id;
    private String link;
    private String style;
    private Long creationDateTimestamp;

    public BaseModuleDto(ModuleEntity moduleEntity) {
        if (moduleEntity == null)
            return;
        this.id = moduleEntity.getId();
        this.link = moduleEntity.getLink();
        this.style = moduleEntity.getStyle();
        this.creationDateTimestamp = moduleEntity.getCreationDate().getTime();
    }
}
