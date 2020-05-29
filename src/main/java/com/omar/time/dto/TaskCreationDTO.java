package com.omar.time.dto;

import javax.validation.constraints.NotNull;

import com.omar.time.model.Project;
import com.omar.time.model.UnitName;

public class TaskCreationDTO {

	@NotNull
	private String task;
	
	@NotNull
	private int duration;
	
	@NotNull
	private UnitName unit;
	

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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
}
