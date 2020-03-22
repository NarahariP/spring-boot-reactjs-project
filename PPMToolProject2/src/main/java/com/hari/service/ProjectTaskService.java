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

	public ProjectTask addProjectTask(ProjectTask projectTask, String projectIdentifier) {
		final BackLog backLog = ProjectUtils.projectIdentifierFunction.apply(projectIdentifier, backLogRepository);
		projectTask = ProjectUtils.projectTaskFunction.apply(backLog, projectTask);
		return Optional.of(projectTaskRepository.save(projectTask)).orElseThrow(
				() -> new BackLogNotFoundException(ProjectUtils.notAvailableFunction.apply(projectIdentifier)));
	}

	public List<ProjectTask> retriveProjectBackLog(String projectIdentifier) {
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier.toUpperCase())
				.orElseThrow(() -> new BackLogNotFoundException(
						ProjectUtils.taskNotAvailableWithProject.apply(projectIdentifier)));
	}

	public ProjectTask retriveProjectTask(String projectIdentifier, String projectSequence) {
		ProjectUtils.projectIdentifierFunction.apply(projectIdentifier, backLogRepository);
		final ProjectTask projectTask = projectTaskRepository.findByProjectSequence(projectSequence).orElseThrow(
				() -> new BackLogNotFoundException(ProjectUtils.taskNotAvailableFunction.apply(projectSequence)));
		if (!projectTask.getProjectIdentifier().equals(projectIdentifier.toUpperCase())) {
			throw new BackLogNotFoundException(
					ProjectUtils.taskNotAvailableWithProjectFunction.apply(projectIdentifier, projectSequence));
		}
		return projectTask;
	}

	public void deleteProjectTaskByProjectSequence(String projectIdentifier, String projectSequence) {
		final ProjectTask projectTask = retriveProjectTask(projectIdentifier, projectSequence);
		projectTaskRepository.delete(projectTask);
	}

}
