package com.omar.time.dto;

import javax.validation.constraints.NotNull;

public class CardCreationDTO extends UserDateDTO {

	@NotNull
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
