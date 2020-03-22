package com.hari.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hari.exception.ProjectNotFoundException;
import com.hari.model.Project;
import com.hari.model.User;
import com.hari.repositories.BackLogRepository;
import com.hari.repositories.ProjectRepository;
import com.hari.repositories.UserRepository;
import com.hari.utils.ProjectUtils;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BackLogRepository backLogRepository;

	public Project saveOrUpdateProject(Project project, String username) {
		if (project.getId() != null) {
			retriveProject(project.getProjectIdentifier(), username);
		} else {
			final Project existingProject = projectRepository
					.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()).get();
			if (project.getId() == null && existingProject != null) {
				throw new ProjectNotFoundException(
						ProjectUtils.availableFunction.apply(project.getProjectIdentifier().toUpperCase()));
			}
		}
		final User user = userRepository.findByUsername(username);
		project.setUser(user);
		project.setProjectOwner(username);
		project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
		project = ProjectUtils.backLogFunction.apply(project);
		if (project.getId() != null) {
			project.setBackLog(
					ProjectUtils.projectIdentifierFunction.apply(project.getProjectIdentifier(), backLogRepository));
		}
		return projectRepository.save(project);
	}

	public Project retriveProject(String projectIdentifier, String username) {
		return projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase()).filter(project -> {
			if (ProjectUtils.userBiPredicate.test(project.getProjectOwner(), username)) {
				return true;
			} else {
				throw new ProjectNotFoundException(
						ProjectUtils.projectNotAssociatedWithUserFunction.apply(projectIdentifier, username));
			}
		}).orElseThrow(() -> new ProjectNotFoundException(ProjectUtils.notAvailableFunction.apply(projectIdentifier)));
	}

	public List<Project> retriveAllProjects(String username) {
		return projectRepository.findByProjectOwner(username);
	}

	public void delete(Long id) {
		projectRepository.deleteById(id);
	}

}
