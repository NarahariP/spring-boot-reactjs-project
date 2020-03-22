package com.hari.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class BackLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer projectTaskSequence = 0;
	private String projectIdentifier;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id", nullable = false)
	@JsonIgnore
	private Project project;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, mappedBy = "backLog", orphanRemoval = true)
	private final List<ProjectTask> projectTasks = new ArrayList<>();

	public BackLog() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getProjectTaskSequence() {
		return projectTaskSequence;
	}

	public void setProjectTaskSequence(Integer projectTaskSequence) {
		this.projectTaskSequence = projectTaskSequence;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<ProjectTask> getProjectTasks() {
		return projectTasks;
	}

	@Override
	public String toString() {
		return "BackLog [id=" + id + ", projectTaskSequence=" + projectTaskSequence + ", projectIdentifier="
				+ projectIdentifier + ", project=" + project + ", projectTasks=" + projectTasks + "]";
	}

}
