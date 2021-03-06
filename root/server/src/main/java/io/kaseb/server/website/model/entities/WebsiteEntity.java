package io.kaseb.server.website.model.entities;

import io.kaseb.server.user.model.entities.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("WebsiteEntity{");
		sb.append("id='").append(id).append('\'');
		sb.append(", url='").append(url).append('\'');
		sb.append(", title='").append(title).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
