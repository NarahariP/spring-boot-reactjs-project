package com.hari.service;

import java.util.Spliterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hari.exception.ProjectNotFoundException;
import com.hari.model.Project;
import com.hari.repositories.ProjectRepository;
import com.hari.utils.ProjectUtils;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public Project saveOrUpdateProject(Project project) {
		return projectRepository.save(project);
	}

	public Project retriveProject(String projectIdentifier) {
		return projectRepository.findByProjectIdentifier(projectIdentifier).orElseThrow(
				() -> new ProjectNotFoundException(ProjectUtils.notAvailableFunction.apply(projectIdentifier)));
	}

	public Spliterator<Project> retriveAllProjects() {
		return projectRepository.findAll().spliterator();
	}

	public void delete(Long id) {
		projectRepository.deleteById(id);
	}

}
