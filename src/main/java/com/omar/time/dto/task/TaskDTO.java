package com.omar.time.dto.task;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	
<<<<<<< HEAD
	@NotBlank(groups = {Create.class, Update.class}, message = "{errors.validation.task.notBlank}")
	@Size(max = 150, groups = {Create.class, Update.class}, message = "{errors.validation.task.maxLength}")
=======
	@NotBlank(groups = {Create.class, Update.class}, message = "{errors.task.notBlank}")
	@Size(max = 150, groups = {Create.class, Update.class}, message = "{errors.task.maxLength}")
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
	private String task;
	
	private LocalDateTime dueDate;
	
<<<<<<< HEAD
	@NotNull(groups = {Update.class, UpdateStatus.class}, message = "{errors.validation.status.notNull}")
=======
	@NotNull(groups = {Update.class, UpdateStatus.class}, message = "{errors.status.notNull}")
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
	private StatusName status;
	
}
