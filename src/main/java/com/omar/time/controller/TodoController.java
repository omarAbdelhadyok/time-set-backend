package com.omar.time.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omar.time.dto.todo.CreateTodoDTO;
import com.omar.time.dto.todo.TodoDto;
import com.omar.time.dto.todo.UpdateTodoDTO;
import com.omar.time.security.CurrentUser;
import com.omar.time.security.UserPrincipal;
import com.omar.time.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

	private TodoService todoService;
	
	
	@Autowired
	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}
	
	
	@GetMapping
	public Page<TodoDto> getAll(@CurrentUser UserPrincipal userPrincipal ,Pageable pageable) {
		return todoService.getAll(userPrincipal, pageable);
	}
	
//	@GetMapping("/{id}")
//	public TodoDto get(@CurrentUser UserPrincipal userPrincipal, @PathVariable long id) {
//		return todoService.get(userPrincipal, id);
//	}
	
	@PostMapping
	public TodoDto create(@Validated @RequestBody CreateTodoDTO createTodoDTO) {
		return todoService.create(createTodoDTO);
	}
	
	@PutMapping
	public TodoDto update(@CurrentUser UserPrincipal userPrincipal,
			@Validated @RequestBody UpdateTodoDTO updateTodoDTO) {
		return todoService.update(userPrincipal, updateTodoDTO);
	}
	
	@DeleteMapping("/{id}")
	public boolean delete(@CurrentUser UserPrincipal userPrincipal, @PathVariable long id) {
		return todoService.delete(userPrincipal, id);
	}
}
