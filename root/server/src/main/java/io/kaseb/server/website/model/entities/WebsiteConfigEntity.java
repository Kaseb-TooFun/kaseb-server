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
    @Column(name = "name", columnDefinition = "TEXT")
    private String name;
    @Column(name = "goal_type", columnDefinition = "TEXT")
    private String goalType;
    @Column(name = "goal_selector", columnDefinition = "TEXT")
    private String goalSelector;
    @Column(name = "goal_link", columnDefinition = "TEXT")
    private String goalLink;


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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WebsiteConfigEntity{");
        sb.append("id='").append(id).append('\'');
        sb.append(", configValue='").append(configValue).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
