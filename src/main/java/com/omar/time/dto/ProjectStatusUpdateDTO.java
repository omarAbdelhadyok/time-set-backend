package com.omar.time.dto;

import javax.validation.constraints.NotNull;

import com.omar.time.model.StatusName;

public class ProjectStatusUpdateDTO {

	private String title;
	
	private String description;
	
	@NotNull
	private StatusName status;
	
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

	public StatusName getStatus() {
		return status;
	}

	public void setStatus(StatusName status) {
		this.status = status;
	}
	
	
}
