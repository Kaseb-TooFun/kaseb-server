package io.kaseb.server.module.model.dao;

import io.kaseb.server.module.model.entities.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Repository
public interface ModuleRepo extends JpaRepository<ModuleEntity, String> {
    Optional<ModuleEntity> findFirstByOrderByCreationDateDesc();
}
