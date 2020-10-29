package com.omar.time.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omar.time.dto.Create;
import com.omar.time.dto.Update;
import com.omar.time.dto.UpdateStatus;
import com.omar.time.dto.task.TaskDTO;
import com.omar.time.model.Task;
import com.omar.time.security.CurrentUser;
import com.omar.time.security.UserPrincipal;
import com.omar.time.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	private TaskService taskService;
	
	
	@Autowired
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	@PostMapping("/{cardId}")
	public Task create(@CurrentUser UserPrincipal userPrincipal,
			@Validated(Create.class) @RequestBody TaskDTO taskDTO,
			@PathVariable long cardId) {
		return taskService.create(userPrincipal, taskDTO, cardId);
	}
	
	@PutMapping
	public Task update(@CurrentUser UserPrincipal userPrincipal,
			@Validated(Update.class) @RequestBody TaskDTO taskDTO) {
		return taskService.update(userPrincipal, taskDTO);
	}
	
	@PatchMapping
	public Task updateStatus(@CurrentUser UserPrincipal userPrincipal,
			@Validated(UpdateStatus.class) @RequestBody TaskDTO taskDTO) {
		return taskService.updateStatus(userPrincipal, taskDTO);
	}
	
	@DeleteMapping("/{taskId}")
	public boolean delete(@CurrentUser UserPrincipal userPrincipal, @PathVariable long taskId) {
		return taskService.delete(userPrincipal, taskId);
	}
	
}
