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

import com.omar.time.dto.AllProjectsDTO;
import com.omar.time.dto.ProjectCreationDTO;
import com.omar.time.dto.ProjectDTO;
import com.omar.time.dto.ProjectStatusUpdateDTO;
import com.omar.time.dto.ProjectUpdatingDTO;
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
	public ProjectDTO get(@CurrentUser UserPrincipal userPrincipal, @PathVariable long id) {
		return projectService.get(userPrincipal, id);
	}
	
	@GetMapping("/{id}/addAuthor/{userId}")
	public ProjectDTO addAuthor(@CurrentUser UserPrincipal userPrincipal, @PathVariable long id, @PathVariable long userId) {
		return projectService.addAuthor(userPrincipal, id, userId);
	}
	
	@PostMapping
	public ProjectDTO create(@RequestBody ProjectCreationDTO projectCreationDTO) {
		return projectService.create(projectCreationDTO);
	}
	
	@PutMapping("/{id}")
	public ProjectDTO update(@CurrentUser UserPrincipal userPrincipal, @RequestBody ProjectUpdatingDTO projectUpdatingDTO, @PathVariable long id) {
		return projectService.update(userPrincipal, projectUpdatingDTO, id);
	}
	
	@PatchMapping("/{id}")
	public ProjectDTO updateStatus(@CurrentUser UserPrincipal userPrincipal, @RequestBody ProjectStatusUpdateDTO projectStatusUpdateDTO, @PathVariable long id) {
		return projectService.updateStatus(userPrincipal, projectStatusUpdateDTO, id);
	}
	
	@DeleteMapping("/{id}")
	public boolean delete(@CurrentUser UserPrincipal userPrincipal, @PathVariable long id) {
		return projectService.delete(userPrincipal, id);
	}
	
	@DeleteMapping("/deleteProject/{projectId}")
	public boolean deleteAuthorityFromProject(@CurrentUser UserPrincipal userPrincipal, @PathVariable long projectId) {
		return projectService.deleteAuthorityFromProject(userPrincipal, projectId);
	}
	
	@DeleteMapping("/deleteAuthor/{projectId}/{userId}")
	public boolean deleteAuthorFromProject(@CurrentUser UserPrincipal userPrincipal, @PathVariable long projectId, @PathVariable long userId) {
		return projectService.deleteAuthorFromProject(userPrincipal, projectId, userId);
	}
}
