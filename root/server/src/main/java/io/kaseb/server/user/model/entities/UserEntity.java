package io.kaseb.server.user.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class UserEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private final String id = UUID.randomUUID().toString();
    @Column(name = "username")
    private String username;
    @Column(name = "hashed_password")
    private String hashedPassword;

    public UserEntity(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }
}
