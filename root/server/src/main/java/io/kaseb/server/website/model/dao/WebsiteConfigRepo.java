package io.kaseb.server.website.model.dao;

import io.kaseb.server.website.model.entities.WebsiteConfigEntity;
import io.kaseb.server.website.model.entities.WebsiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebsiteConfigRepo extends JpaRepository<WebsiteConfigEntity, String> {
    List<WebsiteConfigEntity> findAllByWebsite(WebsiteEntity websiteEntity);
}
