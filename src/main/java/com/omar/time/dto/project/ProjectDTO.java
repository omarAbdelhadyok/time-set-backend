package com.omar.time.dto.project;

import java.util.List;

import com.omar.time.dto.stack.StackDTO;
import com.omar.time.dto.user.UserDateDTO;
import com.omar.time.model.enums.StatusName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDTO extends UserDateDTO {

	private long id;
	
	private String title;
	
	private String description;
	
	private StatusName status;
	
	private List<StackDTO> stacks;
	
	private String img;

}
