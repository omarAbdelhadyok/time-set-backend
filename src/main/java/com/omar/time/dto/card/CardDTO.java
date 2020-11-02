package com.omar.time.dto.card;

import com.omar.time.dto.user.UserDateDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CardDTO extends UserDateDTO {

	private long id;
	
	private String title;
	
	private String description;
	
}
