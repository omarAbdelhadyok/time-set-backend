package com.omar.time.dto.project;

import com.omar.time.dto.user.UserDateDTO;
import com.omar.time.model.enums.StatusName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllProjectsDTO extends UserDateDTO {

	private long id;
	
	private String title;
	
	private String description;
	
	private StatusName status;
	
	private String img;

}
