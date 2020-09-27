package com.omar.time.dto.card;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.omar.time.dto.Create;
import com.omar.time.dto.Update;
import com.omar.time.dto.user.UserDateDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDTO extends UserDateDTO {

	private long id;
	
<<<<<<< HEAD
	@NotBlank(groups = {Create.class, Update.class}, message = "{errors.validation.title.notBlank}")
	@Size(max = 150, groups = {Create.class, Update.class}, message = "{errors.validation.title.maxLength}")
	private String title;
	
	@Size(max = 150, groups = {Create.class, Update.class}, message = "{errors.validation.description.maxLength}")
=======
	@NotBlank(groups = {Create.class, Update.class}, message = "{errors.title.notBlank}")
	@Size(max = 150, groups = {Create.class, Update.class}, message = "{errors.title.maxLength}")
	private String title;
	
	@Size(max = 150, groups = {Create.class, Update.class}, message = "{errors.description.maxLength}")
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
	private String description;
	
}
