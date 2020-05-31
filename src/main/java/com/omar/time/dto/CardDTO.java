package com.omar.time.dto;

import java.util.List;

import com.omar.time.model.Comment;
import com.omar.time.model.Task;

public class CardDTO extends UserDateDTO {

	private long id;
	
	private String title;
	
	private List<Comment> comments;
	
	private List<Task> tasks;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String task) {
		this.title = task;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
}
