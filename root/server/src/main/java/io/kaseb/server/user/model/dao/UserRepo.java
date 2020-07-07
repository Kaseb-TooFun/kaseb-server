package io.kaseb.server.user.model.dao;

import io.kaseb.server.user.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Repository
public interface UserRepo extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);
}
