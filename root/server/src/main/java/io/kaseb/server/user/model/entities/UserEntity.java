package io.kaseb.server.user.model.entities;

import io.kaseb.server.website.model.entities.WebsiteEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
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
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
	private List<WebsiteEntity> websites;

	public UserEntity(String username, String hashedPassword) {
		this.username = username;
		this.hashedPassword = hashedPassword;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UserEntity)) return false;
		UserEntity that = (UserEntity) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("UserEntity{");
		sb.append("id='").append(id).append('\'');
		sb.append(", username='").append(username).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
