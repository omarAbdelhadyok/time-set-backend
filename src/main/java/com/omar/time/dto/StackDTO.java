package com.omar.time.dto;

import java.util.List;

public class StackDTO extends UserDateDTO {

	private long id;
	
	private String title;
	
	private List<CardDTO> cards;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public List<CardDTO> getCards() {
		return cards;
	}

	public void setCards(List<CardDTO> cards) {
		this.cards = cards;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
