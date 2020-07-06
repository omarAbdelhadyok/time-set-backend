package com.omar.time.dto.task;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.omar.time.dto.Create;
import com.omar.time.dto.Update;
import com.omar.time.dto.UpdateStatus;
import com.omar.time.dto.user.UserDateDTO;
import com.omar.time.model.enums.StatusName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO extends UserDateDTO {

	private long id;
	
	@NotBlank(groups = {Create.class, Update.class})
	private String task;
	
	private LocalDateTime dueDate;
	
	@NotNull(groups = {Update.class, UpdateStatus.class})
	private StatusName status;
	
}
