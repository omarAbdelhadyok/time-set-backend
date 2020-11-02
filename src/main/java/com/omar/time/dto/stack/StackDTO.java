package com.omar.time.dto.stack;

import java.util.List;

import com.omar.time.dto.card.CardDTO;
import com.omar.time.dto.user.UserDateDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StackDTO extends UserDateDTO {

	private long id;
	
	private String title;
	
	private List<CardDTO> cards;
	
}
