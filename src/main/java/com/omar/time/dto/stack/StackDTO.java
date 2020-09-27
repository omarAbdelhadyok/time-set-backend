package com.omar.time.dto.stack;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.omar.time.dto.Create;
import com.omar.time.dto.Update;
import com.omar.time.dto.card.CardDTO;
import com.omar.time.dto.user.UserDateDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StackDTO extends UserDateDTO {

	private long id;
	
<<<<<<< HEAD
	@NotBlank(groups = {Create.class, Update.class}, message = "{errors.validation.title.notBlank}")
	@Size(max = 50, groups = {Create.class, Update.class}, message = "{errors.validation.title.maxLength}")
=======
	@NotBlank(groups = {Create.class, Update.class}, message = "{errors.title.notBlank}")
	@Size(max = 50, groups = {Create.class, Update.class}, message = "{errors.title.maxLength}")
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
	private String title;
	
	private List<CardDTO> cards;
	
}
