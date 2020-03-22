package com.hari.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hari.model.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

	Optional<Project> findByProjectIdentifier(String projectIdentifier);

	List<Project> findByProjectOwner(String username);

}
