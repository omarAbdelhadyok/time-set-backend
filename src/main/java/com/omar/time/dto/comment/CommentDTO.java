package com.omar.time.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.omar.time.dto.Create;
import com.omar.time.dto.Update;
import com.omar.time.dto.user.UserDateDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO extends UserDateDTO {

	private long id;
	
	@NotBlank(groups = {Create.class, Update.class}, message = "{errors.validation.comment.notBlank}")
	@Size(max = 250, groups = {Create.class, Update.class}, message = "{errors.validation.comment.maxLength}")
	private String comment;
	
}
