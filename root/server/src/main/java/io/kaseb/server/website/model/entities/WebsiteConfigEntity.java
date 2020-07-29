package io.kaseb.server.website.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Entity
@Table(name = "website_config")
@NoArgsConstructor
@Data
public class WebsiteConfigEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private final String id = UUID.randomUUID().toString();
    @ManyToOne
    @JoinColumn(name = "website_id")
    private WebsiteEntity website;
    @Column(name = "config_value", columnDefinition = "TEXT")
    private String configValue;

    public WebsiteConfigEntity(WebsiteEntity website, String configValue) {
        this.website = website;
        this.configValue = configValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WebsiteConfigEntity)) return false;
        WebsiteConfigEntity that = (WebsiteConfigEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
