package com.omar.time.dto.card;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCardDTO {
	
	@NotNull
	private long id;
	
	@NotBlank(message = "{errors.validation.title.notBlank}")
	@Size(max = 150, message = "{errors.validation.title.maxLength}")
	private String title;
	
	@Size(max = 150, message = "{errors.validation.description.maxLength}")
	private String description;

}
