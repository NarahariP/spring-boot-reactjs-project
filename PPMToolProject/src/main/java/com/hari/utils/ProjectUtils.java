package com.hari.utils;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.hari.exception.ValdiationFailedException;
import com.hari.model.Project;

public class ProjectUtils {

	public static Consumer<BindingResult> bindingResultConsumer = result -> {
		if (result.hasErrors()) {
			throw new ValdiationFailedException("Validation Failed! " + result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
		}
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
}
