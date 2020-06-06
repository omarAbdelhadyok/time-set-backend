package com.omar.time.dto;

import javax.validation.constraints.NotNull;

import com.omar.time.model.User;

public class ProjectAddAuthorDTO {

	@NotNull
	private User author;

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
	
}
