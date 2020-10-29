package com.omar.time.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omar.time.dto.Create;
import com.omar.time.dto.Update;
import com.omar.time.dto.stack.StackDTO;
import com.omar.time.security.CurrentUser;
import com.omar.time.security.UserPrincipal;
import com.omar.time.service.StackService;

@RestController
@RequestMapping("/api/stacks")
public class StackController {

	private StackService stackService;
	
	
	@Autowired
	public StackController(StackService stackService) {
		this.stackService = stackService;
	}
	
	@PostMapping("/{projectId}")
	public StackDTO create(@CurrentUser UserPrincipal userPrincipal,
			@Validated(Create.class) @RequestBody StackDTO stackDTO,
			@PathVariable long projectId) {
		return stackService.create(userPrincipal, stackDTO, projectId);
	}
	
	@PutMapping
	public StackDTO update(@CurrentUser UserPrincipal userPrincipal,
			@Validated(Update.class) @RequestBody StackDTO stackDTO) {
		return stackService.update(userPrincipal, stackDTO);
	}
	
	@DeleteMapping("/{stackId}")
	public boolean delete(@CurrentUser UserPrincipal userPrincipal, @PathVariable long stackId) {
		return stackService.delete(userPrincipal, stackId);
	}
	
}
