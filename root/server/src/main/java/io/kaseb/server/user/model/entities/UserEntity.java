package io.kaseb.server.user.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
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
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<WebsiteEntity> websites;

    public UserEntity(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }
}