package io.kaseb.server.user.model.dao;

import io.kaseb.server.user.model.entities.WebsiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Repository
public interface WebsiteRepo extends JpaRepository<WebsiteEntity, String> {
    boolean existsByUrl(String url);
}
