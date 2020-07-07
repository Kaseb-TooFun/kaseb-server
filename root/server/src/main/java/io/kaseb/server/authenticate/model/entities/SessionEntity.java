package io.kaseb.server.authenticate.model.entities;

import io.kaseb.server.user.model.entities.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Entity
@Table(name = "sessions")
@Getter
@NoArgsConstructor
public class SessionEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private final String id = UUID.randomUUID().toString();
    @Column(name = "token")
    private String token;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    @CreationTimestamp
    private Date creationDate;
    @Setter
    @Column(name = "last_activity_date")
    private Date lastActivityDate;

    public SessionEntity(String token, UserEntity user) {
        this.token = token;
        this.user = user;
    }
}