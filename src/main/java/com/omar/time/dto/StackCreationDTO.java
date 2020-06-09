package com.omar.time.dto;

import javax.validation.constraints.NotNull;

import com.omar.time.model.Project;

public class StackCreationDTO extends UserDateDTO {

	@NotNull
	private String title;
	
	private Project project;
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	
}
