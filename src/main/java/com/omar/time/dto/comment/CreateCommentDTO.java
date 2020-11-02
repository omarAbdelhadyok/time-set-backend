package com.omar.time.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentDTO {

	@NotBlank(message = "{errors.validation.comment.notBlank}")
	@Size(max = 250, message = "{errors.validation.comment.maxLength}")
	private String comment;
	
}
