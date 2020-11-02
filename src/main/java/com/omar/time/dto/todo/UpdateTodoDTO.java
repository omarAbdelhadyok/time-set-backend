package com.omar.time.dto.todo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.omar.time.model.enums.TodoStatusName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTodoDTO {
	
	@NotNull
	private long id;
	
	@NotBlank(message = "{errors.validation.todo.notBlank}")
	@Size(max = 200, message = "{errors.validation.todo.maxLength}")
	private String todo;
	
	@NotBlank(message = "{errors.validation.status.notNull}")
	private TodoStatusName status;
	
}
