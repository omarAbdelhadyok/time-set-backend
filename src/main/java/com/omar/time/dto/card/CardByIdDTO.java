package com.omar.time.dto.card;

import java.util.List;

import com.omar.time.dto.comment.CommentDTO;
import com.omar.time.dto.task.TaskDTO;
import com.omar.time.dto.user.UserDateDTO;

public class CardByIdDTO extends UserDateDTO {

	private long id;
	
	private String title;
	
	private List<TaskDTO> tasks;
	
	private List<CommentDTO> comments;

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

	public List<TaskDTO> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskDTO> tasks) {
		this.tasks = tasks;
	}

	public List<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}
	
}
