package com.omar.time.dto;

import com.omar.time.model.StatusName;
import com.omar.time.model.UnitName;

public class TaskDTO extends UserDateDTO {

	private long id;
	
	private String task;
	
	private int duration;
	
	private UnitName unit;
	
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
	
}
