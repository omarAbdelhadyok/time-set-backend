package com.omar.time.dto.todo;

import com.omar.time.model.enums.TodoStatusName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoDto {

	private long id;
	
	private String todo;
	
	private TodoStatusName status;
	
}
