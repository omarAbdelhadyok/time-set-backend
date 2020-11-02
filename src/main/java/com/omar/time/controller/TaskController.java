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

import com.omar.time.dto.task.CreateTaskDTO;
import com.omar.time.dto.task.TaskDTO;
import com.omar.time.dto.task.UpdateTaskDTO;
import com.omar.time.dto.task.UpdateTaskStatusDTO;
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
	public TaskDTO create(@CurrentUser UserPrincipal userPrincipal,
			@Validated @RequestBody CreateTaskDTO createTaskDTO,
			@PathVariable long cardId) {
		return taskService.create(userPrincipal, createTaskDTO, cardId);
	}
	
	@PutMapping
	public TaskDTO update(@CurrentUser UserPrincipal userPrincipal,
			@Validated @RequestBody UpdateTaskDTO updateTaskDTO) {
		return taskService.update(userPrincipal, updateTaskDTO);
	}
	
	@PatchMapping
	public TaskDTO updateStatus(@CurrentUser UserPrincipal userPrincipal,
			@Validated @RequestBody UpdateTaskStatusDTO updateTaskStatusDTO) {
		return taskService.updateStatus(userPrincipal, updateTaskStatusDTO);
	}
	
	@DeleteMapping("/{taskId}")
	public boolean delete(@CurrentUser UserPrincipal userPrincipal, @PathVariable long taskId) {
		return taskService.delete(userPrincipal, taskId);
	}
	
}
