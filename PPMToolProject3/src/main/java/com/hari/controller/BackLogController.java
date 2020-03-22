package com.hari.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hari.model.ProjectTask;
import com.hari.service.ProjectTaskService;
import com.hari.utils.ProjectUtils;

@RestController
@RequestMapping("/backlog")
@CrossOrigin
public class BackLogController {

	@Autowired
	private ProjectTaskService projectTaskService;

	@PostMapping("/{projectIdentifier}")
	public ResponseEntity<?> addProjectTaskToBackLog(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
			@PathVariable String projectIdentifier, Principal principal) {
		if (ProjectUtils.bindingResultFunction.apply(result).size() != 0) {
			return new ResponseEntity<>(ProjectUtils.bindingResultFunction.apply(result), HttpStatus.BAD_REQUEST);
		}
		projectTask = projectTaskService.addProjectTask(projectTask, projectIdentifier.toUpperCase(),
				principal.getName());
		return new ResponseEntity<>(projectTask, HttpStatus.CREATED);
	}

	@GetMapping("/{projectIdentifier}")
	public ResponseEntity<List<ProjectTask>> getAllProjectTasks(@PathVariable String projectIdentifier,
			Principal principal) {
		return new ResponseEntity<>(
				projectTaskService.retriveProjectBackLog(projectIdentifier.toUpperCase(), principal.getName()),
				HttpStatus.OK);
	}

	@GetMapping("/{projectIdentifier}/{projectSequence}")
	public ResponseEntity<ProjectTask> getProjectTasks(@PathVariable String projectIdentifier,
			@PathVariable String projectSequence, Principal principal) {
		return new ResponseEntity<>(projectTaskService.retriveProjectTask(projectIdentifier.toUpperCase(),
				projectSequence, principal.getName()), HttpStatus.OK);
	}

	@DeleteMapping("/{projectIdentifier}/{projectSequence}")
	public ResponseEntity<?> deleteProjectTasks(@PathVariable String projectIdentifier,
			@PathVariable String projectSequence, Principal principal) {
		projectTaskService.deleteProjectTaskByProjectSequence(projectIdentifier.toUpperCase(), projectSequence,
				principal.getName());
		return new ResponseEntity<>(ProjectUtils.deleteTaskFunction.apply(projectSequence), HttpStatus.OK);
	}

}
