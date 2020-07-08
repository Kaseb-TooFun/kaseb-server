package io.kaseb.server.user.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Entity
@Table(name = "website")
@NoArgsConstructor
@Data
public class WebsiteEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private final String id = UUID.randomUUID().toString();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Column(name = "url")
    private String url;
    @Column(name = "title")
    private String title;
    @Column(name = "deleted", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deletd;

    public WebsiteEntity(UserEntity user, String url) {
        this.user = user;
        this.url = url;
    }
}
