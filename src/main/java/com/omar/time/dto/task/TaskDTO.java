package com.omar.time.dto.task;

import java.time.LocalDateTime;

import com.omar.time.dto.user.UserDateDTO;
import com.omar.time.model.enums.StatusName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO extends UserDateDTO {

	private long id;
	
	private String task;
	
	private LocalDateTime dueDate;

	private StatusName status;
	
}
