package com.omar.time.dto;

import java.util.List;

import com.omar.time.model.Card;

public class StackDTO extends UserDateDTO {

	private long id;
	
	private String title;
	
	private List<Card> cards;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
