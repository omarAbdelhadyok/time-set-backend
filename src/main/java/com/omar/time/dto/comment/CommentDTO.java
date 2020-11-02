package com.omar.time.dto.comment;

import com.omar.time.dto.user.UserDateDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO extends UserDateDTO {

	private long id;
	
	private String comment;
	
}
