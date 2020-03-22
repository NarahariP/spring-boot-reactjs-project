package com.hari.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hari.model.BackLog;

@Repository
public interface BackLogRepository extends CrudRepository<BackLog, Long> {

	Optional<BackLog> findByProjectIdentifier(String projectIdentifier);

}
