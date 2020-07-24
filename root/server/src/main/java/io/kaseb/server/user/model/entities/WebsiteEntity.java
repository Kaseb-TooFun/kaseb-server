package io.kaseb.server.user.model.entities;

import io.kaseb.server.user.model.Config;
import io.kaseb.server.user.model.converter.ConfigConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
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
    private boolean deleted;
    @Column(name = "configs", columnDefinition = "TEXT")
    @Convert(converter = ConfigConverter.class)
    @ElementCollection
    private List<Config> configs;

    public WebsiteEntity(UserEntity user, String url, String title) {
        this.user = user;
        this.url = url;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WebsiteEntity)) return false;
        WebsiteEntity that = (WebsiteEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
