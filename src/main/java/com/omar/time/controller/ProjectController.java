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

import com.omar.time.dto.AllProjectsDTO;
import com.omar.time.dto.ProjectCreationDTO;
import com.omar.time.dto.ProjectDTO;
import com.omar.time.model.Project;
import com.omar.time.security.CurrentUser;
import com.omar.time.security.UserPrincipal;
import com.omar.time.service.ProjectService;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	
	@GetMapping
	public Page<AllProjectsDTO> getAll(@CurrentUser UserPrincipal userPrincipal, Pageable pageable) {
		return projectService.getAll(userPrincipal, pageable);
	}
	
	@GetMapping("/{id}")
	public ProjectDTO get(@PathVariable long id) {
		return projectService.get(id);
	}
	
	@PostMapping
	public Project create(@RequestBody ProjectCreationDTO projectCreationDTO) {
		return projectService.create(projectCreationDTO);
	}
	
	@PutMapping("/{id}")
	public Project update(@RequestBody ProjectCreationDTO projectCreationDTO, @PathVariable long id) {
		return projectService.update(projectCreationDTO, id);
	}
	
	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable long id) {
		return projectService.delete(id);
	}
}
