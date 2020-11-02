package com.omar.time.dto.project;

import javax.validation.constraints.NotNull;

import com.omar.time.model.enums.StatusName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProjectStatusDTO {
	
	@NotNull
	private long id;
	
	@NotNull(message = "{errors.validation.status.notNull}")
	private StatusName status;

}
