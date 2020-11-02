package com.omar.time.dto.task;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskDTO {
	
	@NotNull
	private long id;
	
	@NotBlank(message = "{errors.validation.task.notBlank}")
	@Size(max = 150, message = "{errors.validation.task.maxLength}")
	private String task;
	
	private LocalDateTime dueDate;

}
