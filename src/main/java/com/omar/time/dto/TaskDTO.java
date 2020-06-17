package com.omar.time.dto;

import java.time.LocalDateTime;

import com.omar.time.model.StatusName;

public class TaskDTO extends UserDateDTO {

	private long id;
	
	private String task;
	
	private LocalDateTime dueDate;
	
	private StatusName status;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public StatusName getStatus() {
		return status;
	}

	public void setStatus(StatusName status) {
		this.status = status;
	}
	
}
