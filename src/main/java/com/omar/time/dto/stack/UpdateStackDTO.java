package com.omar.time.dto.stack;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStackDTO {
	
	@NotNull
	private long id;
	
	@NotBlank(message = "{errors.validation.title.notBlank}")
	@Size(max = 50, message = "{errors.validation.title.maxLength}")
	private String title;

}
