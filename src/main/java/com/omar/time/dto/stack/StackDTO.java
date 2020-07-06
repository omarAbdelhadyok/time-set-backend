package com.omar.time.dto.stack;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.omar.time.dto.Create;
import com.omar.time.dto.Update;
import com.omar.time.dto.card.CardDTO;
import com.omar.time.dto.user.UserDateDTO;

public class StackDTO extends UserDateDTO {

	private long id;
	
	@NotBlank(groups = {Create.class, Update.class})
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
