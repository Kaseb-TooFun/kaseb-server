package io.kaseb.server.authenticate.model.dao;

import io.kaseb.server.authenticate.model.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Repository
public interface SessionRepo extends JpaRepository<SessionEntity, String> {
    Optional<SessionEntity> findByToken(String token);
}
