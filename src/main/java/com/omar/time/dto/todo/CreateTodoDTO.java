package com.omar.time.dto.todo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTodoDTO {

	@NotBlank(message = "{errors.validation.todo.notBlank}")
	@Size(max = 200, message = "{errors.validation.todo.maxLength}")
	private String todo;
	
}
