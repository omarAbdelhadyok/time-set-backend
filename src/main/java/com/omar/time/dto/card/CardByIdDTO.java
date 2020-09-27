package com.omar.time.dto.card;

import java.util.List;

import com.omar.time.dto.comment.CommentDTO;
import com.omar.time.dto.task.TaskDTO;
import com.omar.time.dto.user.UserDateDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardByIdDTO extends UserDateDTO {

	private long id;
	
	private String title;
	
	private List<TaskDTO> tasks;
	
	private List<CommentDTO> comments;
	
}
