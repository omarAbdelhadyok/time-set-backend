package com.omar.time.dto.project;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
	
	@NotBlank(groups = {Create.class, Update.class})
	private String title;
	
	@NotBlank(groups = {Create.class, Update.class})
	private String description;
	
	@NotNull(groups = {Update.class, UpdateStatus.class})
	private StatusName status;
	
	private List<StackDTO> stacks;
	
	private String img;

}
