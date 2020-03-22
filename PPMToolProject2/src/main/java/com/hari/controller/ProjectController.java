package com.hari.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hari.exception.ProjectNotFoundException;
import com.hari.model.Project;
import com.hari.service.ProjectService;
import com.hari.utils.ProjectUtils;

@RestController
@RequestMapping("/")
@CrossOrigin
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@PostMapping("/projects/add-project")
	public ResponseEntity<?> saveProject(@RequestBody @Validated Project project, BindingResult result) {
		if (ProjectUtils.bindingResultFunction.apply(result).size() != 0) {
			return new ResponseEntity<>(ProjectUtils.bindingResultFunction.apply(result), HttpStatus.BAD_REQUEST);
		}
		try {
			return ResponseEntity
					.created(ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
							.buildAndExpand(projectService
									.saveOrUpdateProject(ProjectUtils.projectFunction.apply(project)).getId())
							.toUri())
					.build();
		} catch (final Exception exception) {
			throw new ProjectNotFoundException(ProjectUtils.availableFunction.apply(project.getProjectIdentifier()));
		}
	}

	@GetMapping("/projects/get-project/{projectIdentifier}")
	public ResponseEntity<Project> retriveProject(@PathVariable String projectIdentifier) {
		return new ResponseEntity<>(projectService.retriveProject(projectIdentifier), HttpStatus.OK);
	}

	@DeleteMapping("/projects/{projectIdentifier}")
	public ResponseEntity<?> deleteProject(@PathVariable String projectIdentifier) {
		projectService.delete(projectService.retriveProject(projectIdentifier).getId());
		return new ResponseEntity<>(ProjectUtils.deleteFunction.apply(projectIdentifier), HttpStatus.OK);
	}

	@GetMapping("/projects")
	public List<Project> getAllProjects() {
		return StreamSupport.stream(projectService.retriveAllProjects(), false).collect(Collectors.toList());
	}
}
