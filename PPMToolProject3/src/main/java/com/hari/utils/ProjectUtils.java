package com.hari.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.hari.exception.BackLogNotFoundException;
import com.hari.exception.ValdiationFailedException;
import com.hari.model.BackLog;
import com.hari.model.Project;
import com.hari.model.ProjectTask;
import com.hari.model.User;
import com.hari.repositories.BackLogRepository;

public class ProjectUtils {

	public static Consumer<BindingResult> bindingResultConsumer = result -> {
		if (result.hasErrors()) {
			throw new ValdiationFailedException("Validation Failed! " + result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
		}
	};

	public static Function<BindingResult, Map<String, String>> bindingResultFunction = result -> {
		final Map<String, String> errorMap = new HashMap<>();
		if (result.hasErrors()) {
			result.getFieldErrors().stream().forEach(filedError -> {
				errorMap.put(filedError.getField(), filedError.getDefaultMessage());
			});
		}
		return errorMap;
	};

	public static Function<Project, Project> projectFunction = project -> {
		project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
		return project;
	};

	public static Function<String, String> deleteFunction = projectIdentifier -> {
		return "Project with id " + projectIdentifier.toUpperCase() + " was deleted!";
	};

	public static Function<String, String> availableFunction = identifier -> {
		return "Project Available with id " + identifier;
	};

	public static Function<String, String> notAvailableFunction = identifier -> {
		return "Project Not Available with id " + identifier;
	};

	public static BiFunction<String, String, String> projectNotAssociatedWithUserFunction = (identifier, username) -> {
		return "Project available with id '" + identifier + "', But not associated with '" + username + "'";
	};

	public static Function<String, String> taskNotAvailableWithProject = (identifier) -> {
		return "Project Tasks are not available in Project " + identifier;
	};

	public static Function<String, String> taskNotAvailableFunction = identifier -> {
		return "Project Task Not Available with id " + identifier;
	};

	public static BiFunction<String, String, String> taskNotAvailableWithProjectFunction = (identifier, sequence) -> {
		return "Project Task " + sequence + " does not exist in Project " + identifier;
	};

	public static Function<String, String> deleteTaskFunction = sequence -> {
		return "Project Task with id " + sequence + " was deleted!";
	};

	public static Function<Project, Project> backLogFunction = project -> {
		if (project.getId() == null) {
			final BackLog backLog = new BackLog();
			project.setBackLog(backLog);
			backLog.setProject(project);
			backLog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
		}
		return project;
	};

	public static BiFunction<BackLog, ProjectTask, ProjectTask> projectTaskFunction = (backLog, projectTask) -> {
		Integer projectTaskSequence = backLog.getProjectTaskSequence();
		if (projectTask.getId() == null) {
			projectTaskSequence++;
		}
		backLog.setProjectTaskSequence(projectTaskSequence);
		projectTask.setProjectSequence(backLog.getProjectIdentifier() + "-" + projectTaskSequence);
		projectTask.setBackLog(backLog);
		projectTask.setProjectIdentifier(backLog.getProjectIdentifier().toUpperCase());
		if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
			projectTask.setPriority(3);
		}
		if (projectTask.getStatus() == null || projectTask.getStatus() == "") {
			projectTask.setStatus(ProjectEnum.TO_DO.toString());
		}
		return projectTask;
	};

	public static BiFunction<String, BackLogRepository, BackLog> projectIdentifierFunction = (projectIdentifier,
			backLogRepository) -> {
		return backLogRepository.findByProjectIdentifier(projectIdentifier.toUpperCase()).orElseThrow(
				() -> new BackLogNotFoundException(ProjectUtils.notAvailableFunction.apply(projectIdentifier)));
	};

	public static BiFunction<User, BCryptPasswordEncoder, User> userPasswordEncryptFunction = (user,
			bCryptPasswordEncoder) -> {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setConfirmPassword("");
		return user;
	};

	public static BiPredicate<String, String> userBiPredicate = (projectOwner, username) -> projectOwner
			.equals(username);

}
