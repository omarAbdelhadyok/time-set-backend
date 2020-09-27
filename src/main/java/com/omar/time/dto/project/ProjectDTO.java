package com.omar.time.dto.project;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.omar.time.dto.Create;
import com.omar.time.dto.Update;
import com.omar.time.dto.UpdateStatus;
import com.omar.time.dto.stack.StackDTO;
import com.omar.time.dto.user.UserDateDTO;
import com.omar.time.model.enums.StatusName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDTO extends UserDateDTO {

	private long id;
	
	@NotBlank(groups = {Create.class, Update.class}, message = "{errors.validation.title.notBlank}")
	@Size(max = 50, groups = {Create.class, Update.class}, message = "{errors.validation.title.maxLength}")
	private String title;
	
	@NotBlank(groups = {Create.class, Update.class}, message = "{errors.validation.description.notBlank}")
	@Size(max = 250, groups = {Create.class, Update.class}, message = "{errors.validation.description.maxLength}")
	private String description;
	
	@NotNull(groups = {Update.class, UpdateStatus.class}, message = "{errors.validation.status.notNull}")
	private StatusName status;
	
	private List<StackDTO> stacks;
	
	private String img;

}
