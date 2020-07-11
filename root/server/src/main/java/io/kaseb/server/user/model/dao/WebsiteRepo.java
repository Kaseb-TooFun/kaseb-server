package io.kaseb.server.user.model.dao;

import io.kaseb.server.user.model.entities.WebsiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Repository
public interface WebsiteRepo extends JpaRepository<WebsiteEntity, String> {
    boolean existsByUrl(String url);

    Optional<WebsiteEntity> findByUrl(String websiteUrl);
}
