package com.omar.time.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omar.time.dto.StackCreationDTO;
import com.omar.time.dto.StackDTO;
import com.omar.time.dto.StackUpdatingDTO;
import com.omar.time.security.CurrentUser;
import com.omar.time.security.UserPrincipal;
import com.omar.time.service.StackService;

@RestController
@RequestMapping("/api/stacks")
public class StackController {

	@Autowired
	private StackService stackService;
	
	
	@PostMapping("/{projectId}")
	public StackDTO create(@CurrentUser UserPrincipal userPrincipal,
			@RequestBody StackCreationDTO stackCreateUpdateDTO,
			@PathVariable long projectId) {
		return stackService.create(userPrincipal, stackCreateUpdateDTO, projectId);
	}
	
	@PutMapping("/{projectId}/{stackId}")
	public StackDTO update(@CurrentUser UserPrincipal userPrincipal,
			@RequestBody StackUpdatingDTO stackUpdatingDTO,
			@PathVariable long projectId,
			@PathVariable long stackId) {
		return stackService.update(userPrincipal, stackUpdatingDTO, projectId, stackId);
	}
	
	@DeleteMapping("/{projectId}/{stackId}")
	public boolean delete(@CurrentUser UserPrincipal userPrincipal,
			@PathVariable long projectId,
			@PathVariable long stackId) {
		return stackService.delete(userPrincipal ,projectId, stackId);
	}
	
}
