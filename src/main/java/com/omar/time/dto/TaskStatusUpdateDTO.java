package com.omar.time.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.omar.time.model.Project;
import com.omar.time.model.StatusName;

public class TaskStatusUpdateDTO {
	
	private String task;
	
	private LocalDateTime dueDate;
	
	@NotNull
	private StatusName status;
	
	private Project project;

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public StatusName getStatus() {
		return status;
	}

	public void setStatus(StatusName status) {
		this.status = status;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
