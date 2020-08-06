package io.kaseb.server.module.model.entities;

import io.kaseb.server.operator.model.entities.OperatorEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "modules")
public class ModuleEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id = UUID.randomUUID().toString();
    @Column(name = "link", columnDefinition = "varchar(500)")
    private String link;
    @Column(name = "creation_date")
    @CreationTimestamp
    private Date creationDate;
    @ManyToOne
    @JoinColumn(name = "operator_id")
    private OperatorEntity operator;

    public ModuleEntity(String link, OperatorEntity operatorEntity) {
        this.link = link;
        this.operator = operatorEntity;
    }
}
