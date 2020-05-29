package com.omar.time.dto;

import javax.validation.constraints.NotNull;

import com.omar.time.model.Project;
import com.omar.time.model.StatusName;
import com.omar.time.model.UnitName;

public class TaskStatusUpdateDTO {
	
	private String task;
	
	private int duration;
	
	private UnitName unit;
	
	@NotNull
	private StatusName status;
	
	private Project project;

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public UnitName getUnit() {
		return unit;
	}

	public void setUnit(UnitName unit) {
		this.unit = unit;
	}

	public StatusName getStatus() {
		return status;
	}

	public void setStatus(StatusName status) {
		this.status = status;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
