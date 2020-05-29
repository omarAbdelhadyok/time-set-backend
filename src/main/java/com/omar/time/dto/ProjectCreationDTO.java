package com.omar.time.dto;

import javax.validation.constraints.NotNull;

public class ProjectCreationDTO {

	@NotNull
	private String title;
	
	@NotNull
	private String description;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
