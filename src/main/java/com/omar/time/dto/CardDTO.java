package com.omar.time.dto;

public class CardDTO extends UserDateDTO {

	private long id;
	
	private String title;

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
	
}
