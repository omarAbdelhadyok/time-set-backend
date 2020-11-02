package com.omar.time.dto.task;

import javax.validation.constraints.NotNull;

import com.omar.time.model.enums.StatusName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskStatusDTO {
	
	@NotNull
	private long id;
	
	@NotNull(message = "{errors.validation.status.notNull}")
	private StatusName status;

}
