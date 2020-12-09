package io.kaseb.server.operator.model.dao;

import io.kaseb.server.operator.model.entities.OperatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperatorRepo extends JpaRepository<OperatorEntity, String> {
	Optional<OperatorEntity> findByUsername(String username);
}
