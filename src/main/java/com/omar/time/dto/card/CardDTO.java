package com.omar.time.dto.card;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.omar.time.dto.Create;
import com.omar.time.dto.Update;
import com.omar.time.dto.user.UserDateDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CardDTO extends UserDateDTO {

	private long id;
	
	@NotBlank(groups = {Create.class, Update.class}, message = "{errors.validation.title.notBlank}")
	@Size(max = 150, groups = {Create.class, Update.class}, message = "{errors.validation.title.maxLength}")
	private String title;
	
	@Size(max = 150, groups = {Create.class, Update.class}, message = "{errors.validation.description.maxLength}")
	private String description;
	
}
