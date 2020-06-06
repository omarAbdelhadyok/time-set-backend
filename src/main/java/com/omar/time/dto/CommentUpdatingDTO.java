package com.omar.time.dto;

import javax.validation.constraints.NotNull;

import com.omar.time.model.Card;

public class CommentUpdatingDTO {

	@NotNull
	private String comment;
	
	private Card card;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}
}
