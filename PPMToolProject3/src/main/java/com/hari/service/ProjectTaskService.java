package com.hari.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hari.exception.BackLogNotFoundException;
import com.hari.model.BackLog;
import com.hari.model.ProjectTask;
import com.hari.repositories.BackLogRepository;
import com.hari.repositories.ProjectTaskRepository;
import com.hari.utils.ProjectUtils;

@Service
public class ProjectTaskService {

	@Autowired
	private BackLogRepository backLogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	@Autowired
	private ProjectService projectService;

	public ProjectTask addProjectTask(ProjectTask projectTask, String projectIdentifier, String username) {
		projectService.retriveProject(projectIdentifier, username);
		final BackLog backLog = ProjectUtils.projectIdentifierFunction.apply(projectIdentifier, backLogRepository);
		projectTask = ProjectUtils.projectTaskFunction.apply(backLog, projectTask);
		return Optional.of(projectTaskRepository.save(projectTask)).orElseThrow(
				() -> new BackLogNotFoundException(ProjectUtils.notAvailableFunction.apply(projectIdentifier)));
	}

	public List<ProjectTask> retriveProjectBackLog(String projectIdentifier, String username) {
		projectService.retriveProject(projectIdentifier, username);
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier).orElseThrow(
				() -> new BackLogNotFoundException(ProjectUtils.taskNotAvailableWithProject.apply(projectIdentifier)));
	}

	public ProjectTask retriveProjectTask(String projectIdentifier, String projectSequence, String username) {
		projectService.retriveProject(projectIdentifier, username);
		ProjectUtils.projectIdentifierFunction.apply(projectIdentifier, backLogRepository);
		final ProjectTask projectTask = projectTaskRepository.findByProjectSequence(projectSequence).orElseThrow(
				() -> new BackLogNotFoundException(ProjectUtils.taskNotAvailableFunction.apply(projectSequence)));
		if (!projectTask.getProjectIdentifier().equals(projectIdentifier.toUpperCase())) {
			throw new BackLogNotFoundException(
					ProjectUtils.taskNotAvailableWithProjectFunction.apply(projectIdentifier, projectSequence));
		}
		return projectTask;
	}

	public void deleteProjectTaskByProjectSequence(String projectIdentifier, String projectSequence, String username) {
		final ProjectTask projectTask = retriveProjectTask(projectIdentifier, projectSequence, username);
		projectTaskRepository.delete(projectTask);
	}

}
