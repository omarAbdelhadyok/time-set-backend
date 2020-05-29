package com.omar.time.dto;

import javax.validation.constraints.NotNull;

public class CommentCreationDTO {

	@NotNull
	private String comment;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
