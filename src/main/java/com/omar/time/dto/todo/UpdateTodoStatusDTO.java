package com.omar.time.dto.todo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.omar.time.model.enums.TodoStatusName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTodoStatusDTO {
	
	@NotNull
	private long id;
	
	@NotBlank(message = "{errors.validation.status.notNull}")
	private TodoStatusName status;

}
