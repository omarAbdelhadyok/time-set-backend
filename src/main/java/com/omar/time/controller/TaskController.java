package com.omar.time.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omar.time.dto.TaskCreationDTO;
import com.omar.time.dto.TaskDTO;
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
	
	@GetMapping("/project/{projectId}")
	public Page<TaskDTO> getAll(@PathVariable long projectId, Pageable pagable) {
		return taskService.getAll(projectId, pagable);
	}
	
	@GetMapping
	public Page<TaskDTO> getAll(@CurrentUser UserPrincipal userPrincipal, Pageable pagable) {
		return taskService.getAll(userPrincipal, pagable);
	}
	
	@GetMapping("/{projectId}/{id}")
	public TaskDTO get(@PathVariable long projectId, @PathVariable long id) {
		return taskService.get(projectId, id);
	}
	
	@PostMapping("/{projectId}")
	public Task create(@RequestBody TaskCreationDTO taskCreationDTO, @PathVariable long projectId) {
		return taskService.create(taskCreationDTO, projectId);
	}
	
	@PutMapping("/{id}")
	public Task update(@RequestBody TaskUpdatingDTO taskUpdatingDTO, @PathVariable long id) {
		return taskService.update(taskUpdatingDTO, id);
	}
	
	@PatchMapping("/{id}")
	public Task updateStatus(@RequestBody TaskStatusUpdateDTO taskStatusUpdateDTO, @PathVariable long id) {
		return taskService.updateStatus(taskStatusUpdateDTO, id);
	}
	
	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable long id) {
		return taskService.delete(id);
	}
	
}
