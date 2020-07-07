package io.kaseb.server.authenticate.model.entities;

import io.kaseb.server.user.model.entities.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class SessionEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id = UUID.randomUUID().toString();
    @Column(name = "token")
    private String token;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public SessionEntity(String token, UserEntity user) {
        this.token = token;
        this.user = user;
    }
}