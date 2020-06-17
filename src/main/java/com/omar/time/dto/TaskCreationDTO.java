package com.omar.time.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.omar.time.model.Project;

public class TaskCreationDTO {

	@NotNull
	private String task;
	
	private LocalDateTime dueDate;
	

	private Project project;

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public LocalDateTime getUnit() {
		return dueDate;
	}

	public void setUnit(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
}
