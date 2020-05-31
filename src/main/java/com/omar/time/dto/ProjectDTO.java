package com.omar.time.dto;

import java.util.List;

import com.omar.time.model.Stack;
import com.omar.time.model.StatusName;

public class ProjectDTO extends UserDateDTO {

	private long id;
	
	private String title;
	
	private String description;
	
	private StatusName status;
	
	private List<Stack> stacks;
	

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

	public List<Stack> getStacks() {
		return stacks;
	}

	public void setStacks(List<Stack> stacks) {
		this.stacks = stacks;
	}

}
