package com.omar.time.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omar.time.dto.StackCreationDTO;
import com.omar.time.dto.StackDTO;
import com.omar.time.model.Stack;
import com.omar.time.service.StackService;

@RestController
@RequestMapping("/api/stacks")
public class StackController {

	@Autowired
	private StackService stackService;
	
	@GetMapping("/project/{projectId}")
	public Page<StackDTO> getAll(@PathVariable long projectId, Pageable pagable) {
		return stackService.getAll(projectId, pagable);
	}
	
	@GetMapping("/{projectId}/{id}")
	public StackDTO get(@PathVariable long projectId, @PathVariable long id) {
		return stackService.get(projectId, id);
	}
	
	@PostMapping("/{projectId}")
	public Stack create(@RequestBody StackCreationDTO stackCreateUpdateDTO, @PathVariable long projectId) {
		return stackService.create(stackCreateUpdateDTO, projectId);
	}
	
	@PutMapping("/{id}")
	public Stack update(@RequestBody StackCreationDTO stackCreateUpdateDTO, @PathVariable long id) {
		return stackService.update(stackCreateUpdateDTO, id);
	}
	
	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable long id) {
		return stackService.delete(id);
	}
	
}
