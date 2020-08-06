package io.kaseb.server.operator.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "operators")
@Getter
@NoArgsConstructor
public class OperatorEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private final String id = UUID.randomUUID().toString();
    @Column(name = "username")
    private String username;
    @Column(name = "hashed_password")
    private String hashedPassword;
    @Column(name = "activated",columnDefinition = "boolean default false")
    private boolean activated;

    public OperatorEntity(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
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
}
