package com.omar.time.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omar.time.dto.stack.StackDTO;
import com.omar.time.model.Project;
import com.omar.time.model.Stack;
import com.omar.time.model.User;
import com.omar.time.repository.ProjectRepository;
import com.omar.time.repository.StackRepository;
import com.omar.time.security.UserPrincipal;
import com.omar.time.util.ObjectMapperUtils;

@Service
public class StackService {

	private StackRepository stackRepository;
	private ProjectRepository projectRepository;
	
	@Autowired
	public StackService(StackRepository stackRepository, ProjectRepository projectRepository) {
		this.stackRepository = stackRepository;
		this.projectRepository = projectRepository;
	}
	
	
	public StackDTO create(UserPrincipal userPrincipal, StackDTO stackDTO, long projectId) {
		User user = ObjectMapperUtils.map(userPrincipal, User.class);
		
		Project project = projectRepository.findByIdAndCreatedByOrEditors(projectId, userPrincipal.getId(), user)
		.orElseThrow(() ->
			new EntityNotFoundException("errors.app.project.notFound")
		);
		
		Stack stack = ObjectMapperUtils.map(stackDTO, Stack.class);
		stack.setProject(project);
		stack = stackRepository.save(stack);
		
		return ObjectMapperUtils.map(stack, StackDTO.class);
    }
	
	@Transactional
	public StackDTO update(UserPrincipal userPrincipal, StackDTO stackDTO, long projectId, long stackId) {
		Stack stack = stackRepository.findStack(userPrincipal.getId(), stackDTO.getId()).orElseThrow(() ->
			new EntityNotFoundException("errors.app.stack.notFound")
		);
		
		ObjectMapperUtils.copyPropertiesForUpdate(stackDTO, stack);
		stack = stackRepository.save(stack);
		
		return ObjectMapperUtils.map(stack, StackDTO.class);
    }
	
	public boolean delete(UserPrincipal userPrincipal, long projectId, long stackId) {
		Stack stack = stackRepository.findStack(userPrincipal.getId(), stackId).orElseThrow(() ->
			new EntityNotFoundException("errors.app.stack.notFound")
		);
		
		stack.dismissProject();
		stackRepository.delete(stack);
		
		return true;
    }

}
