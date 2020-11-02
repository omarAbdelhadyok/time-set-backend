package com.omar.time.dto.project;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.omar.time.model.enums.StatusName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProjectDTO {
	
	@NotNull
	private long id;

	@NotBlank(message = "{errors.validation.title.notBlank}")
	@Size(message = "{errors.validation.title.maxLength}")
	private String title;
	
	@NotBlank(message = "{errors.validation.description.notBlank}")
	@Size(max = 250, message = "{errors.validation.description.maxLength}")
	private String description;
	
	@NotNull(message = "{errors.validation.status.notNull}")
	private StatusName status;
	
}
