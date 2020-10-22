package com.omar.time.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.omar.time.dto.project.AllProjectsDTO;
import com.omar.time.dto.project.ProjectDTO;
import com.omar.time.security.CurrentUser;
import com.omar.time.security.UserPrincipal;
import com.omar.time.service.ProjectService;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

	private ProjectService projectService;
	
	
	@Autowired
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	@GetMapping
	public Page<AllProjectsDTO> getAll(@CurrentUser UserPrincipal userPrincipal, Pageable pageable) {
		return projectService.getAll(userPrincipal, pageable);
	}
	
	@GetMapping("/{id}")
	public ProjectDTO get(@CurrentUser UserPrincipal userPrincipal, @PathVariable long id) {
		return projectService.get(userPrincipal, id);
	}
	
	@GetMapping("/{id}/addEditor/{editorId}")
	public boolean addEditor(@CurrentUser UserPrincipal userPrincipal,
			@PathVariable long id,
			@PathVariable long editorId) {
		return projectService.addEditor(userPrincipal, id, editorId);
	}
	
	@PostMapping
	public ProjectDTO create(@Validated(Create.class) @RequestBody ProjectDTO projectDTO) {
		return projectService.create(projectDTO);
	}
	
	@PutMapping
	public ProjectDTO update(@CurrentUser UserPrincipal userPrincipal,
			@Validated(Update.class) @RequestBody ProjectDTO projectDTO) {
		return projectService.update(userPrincipal, projectDTO);
	}
	
	@PatchMapping
	public ProjectDTO updateStatus(@CurrentUser UserPrincipal userPrincipal,
			@Validated(UpdateStatus.class) @RequestBody ProjectDTO projectDTO) {
		return projectService.updateStatus(userPrincipal, projectDTO);
	}
	
	@DeleteMapping("/{id}")
	public boolean delete(@CurrentUser UserPrincipal userPrincipal, @PathVariable long id) {
		return projectService.delete(userPrincipal, id);
	}
	
	@DeleteMapping("/deleteProject/{projectId}")
	public boolean deleteAuthorityFromProject(@CurrentUser UserPrincipal userPrincipal,
			@PathVariable long projectId) {
		return projectService.deleteAuthorityFromProject(userPrincipal, projectId);
	}
	
	@DeleteMapping("/deleteEditor/{projectId}/{editorId}")
	public boolean deleteEditorFromProject(@CurrentUser UserPrincipal userPrincipal,
			@PathVariable long projectId,
			@PathVariable long editorId) {
		return projectService.deleteEditorFromProject(userPrincipal, projectId, editorId);
	}
}
