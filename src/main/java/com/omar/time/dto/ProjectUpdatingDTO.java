package com.omar.time.dto;

import javax.validation.constraints.NotNull;

import com.omar.time.model.StatusName;

public class ProjectUpdatingDTO {

	@NotNull
	private String title;
	
	@NotNull
	private String description;
	
	@NotNull
	private StatusName status;
	
	private String img;
	
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}
