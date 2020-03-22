package com.hari.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hari.model.ProjectTask;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

	Optional<List<ProjectTask>> findByProjectIdentifierOrderByPriority(String projectIdentifier);

	Optional<ProjectTask> findByProjectSequence(String projectSequence);

}
