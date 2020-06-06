package com.omar.time.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omar.time.dto.TaskCreationDTO;
import com.omar.time.dto.TaskStatusUpdateDTO;
import com.omar.time.dto.TaskUpdatingDTO;
import com.omar.time.model.Task;
import com.omar.time.security.CurrentUser;
import com.omar.time.security.UserPrincipal;
import com.omar.time.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	
	@PostMapping("/{projectId}/{stackId}/{cardId}")
	public Task create(@CurrentUser UserPrincipal userPrincipal,
			@RequestBody TaskCreationDTO taskCreationDTO,
			@PathVariable long projectId,
			@PathVariable long stackId,
			@PathVariable long cardId) {
		return taskService.create(userPrincipal, taskCreationDTO, projectId, stackId, cardId);
	}
	
	@PutMapping("/{projectId}/{stackId}/{cardId}/{taskId}")
	public Task update(@CurrentUser UserPrincipal userPrincipal,
			@RequestBody TaskUpdatingDTO taskUpdatingDTO,
			@PathVariable long projectId,
			@PathVariable long stackId,
			@PathVariable long cardId,
			@PathVariable long taskId) {
		return taskService.update(userPrincipal, taskUpdatingDTO, projectId, stackId, cardId, taskId);
	}
	
	@PatchMapping("/{projectId}/{stackId}/{cardId}/{taskId}")
	public Task updateStatus(@CurrentUser UserPrincipal userPrincipal,
			@RequestBody TaskStatusUpdateDTO taskStatusUpdateDTO,
			@PathVariable long projectId,
			@PathVariable long stackId,
			@PathVariable long cardId,
			@PathVariable long taskId) {
		return taskService.updateStatus(userPrincipal, taskStatusUpdateDTO, projectId, stackId, cardId, taskId);
	}
	
	@DeleteMapping("/{projectId}/{stackId}/{cardId}/{taskId}")
	public boolean delete(@CurrentUser UserPrincipal userPrincipal,
			@PathVariable long projectId,
			@PathVariable long stackId,
			@PathVariable long cardId,
			@PathVariable long taskId) {
		return taskService.delete(userPrincipal, projectId, stackId, cardId, taskId);
	}
	
}
