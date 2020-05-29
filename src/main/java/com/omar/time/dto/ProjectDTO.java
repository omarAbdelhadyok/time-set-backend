package com.omar.time.dto;

import java.util.List;

import com.omar.time.model.Comment;
import com.omar.time.model.StatusName;
import com.omar.time.model.Task;

public class ProjectDTO extends UserDateDTO {

	private long id;
	
	private String title;
	
	private String description;
	
	private StatusName status;
	
	private List<Task> tasks;
	
	private List<Comment> comments;
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
