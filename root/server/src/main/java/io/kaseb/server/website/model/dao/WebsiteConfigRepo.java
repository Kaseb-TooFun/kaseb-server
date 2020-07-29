package io.kaseb.server.website.model.dao;

import io.kaseb.server.website.model.entities.WebsiteConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebsiteConfigRepo extends JpaRepository<WebsiteConfigEntity, String> {
}
