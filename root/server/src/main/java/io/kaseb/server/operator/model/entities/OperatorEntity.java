package io.kaseb.server.operator.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.kaseb.server.base.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "operators")
@Getter
@NoArgsConstructor
@Slf4j
public class OperatorEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private final String id = UUID.randomUUID().toString();
    @Column(name = "username")
    private String username;
    @Column(name = "hashed_password")
    private String hashedPassword;
    @Column(name = "activated", columnDefinition = "boolean default false")
    private boolean activated;

    public OperatorEntity(String username, String hashedPassword, boolean activated) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.activated = activated;
    }

    @JsonIgnore
    @Transient
    public boolean isSysadmin() {
        return Objects.equals(Constants.SYSADMIN_USERNAME, this.getUsername());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperatorEntity)) return false;
        OperatorEntity that = (OperatorEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public OperatorEntity updateActivationStatus(boolean activated) {
        logger.info("updating operator activation status . [operatorId : {}, active : {}]", this.id, activated);
        this.activated = activated;
        return this;
    }
}
