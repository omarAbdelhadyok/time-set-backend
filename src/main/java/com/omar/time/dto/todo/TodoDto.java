package com.omar.time.dto.todo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.omar.time.dto.Create;
import com.omar.time.dto.Update;
import com.omar.time.model.enums.TodoStatusName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoDto {

	private long id;
	
	@NotBlank(groups = {Create.class, Update.class}, message = "{errors.todo.notBlank}")
	@Size(groups = {Create.class, Update.class}, max = 200, message = "{errors.todo.maxLength}")
	private String todo;
	
	@NotBlank(groups = {Create.class, Update.class}, message = "{errors.status.notNull}")
	private TodoStatusName status;
	
}
