package com.omar.time.dto.project;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProjectDTO {

	@NotBlank(message = "{errors.validation.title.notBlank}")
	@Size(max = 50, message = "{errors.validation.title.maxLength}")
	private String title;
	
	@NotBlank(message = "{errors.validation.description.notBlank}")
	@Size(max = 250, message = "{errors.validation.description.maxLength}")
	private String description;
}
