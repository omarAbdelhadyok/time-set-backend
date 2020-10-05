package com.omar.time.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omar.time.dto.stack.StackDTO;
import com.omar.time.model.Project;
import com.omar.time.model.Stack;
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
		Project project = projectRepository.findById(projectId).orElseThrow(() ->
			new EntityNotFoundException("errors.app.project.notFound")
		);
		
		UtilService.handleUnathorized(project, userPrincipal);
		Stack stack = ObjectMapperUtils.map(stackDTO, Stack.class);
		stack.setProject(project);
		stack = stackRepository.save(stack);
		
		return ObjectMapperUtils.map(stack, StackDTO.class);
    }
	
	@Transactional
	public StackDTO update(UserPrincipal userPrincipal, StackDTO stackDTO, long projectId, long stackId) {
		Project project = projectRepository.findById(projectId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);
		
		UtilService.handleUnathorized(project, userPrincipal);
		UtilService.getStackFromProject(project, stackId);
		Stack stack = ObjectMapperUtils.map(stackDTO, Stack.class);
		stack.setId(stackId);
		stack.setProject(project);
		stack = stackRepository.save(stack);
		
		return ObjectMapperUtils.map(stack, StackDTO.class);
    }
	
	public boolean delete(UserPrincipal userPrincipal, long projectId, long stackId) {
		Project project = projectRepository.findById(projectId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);
		
		UtilService.handleUnathorized(project, userPrincipal);
		Stack stack = UtilService.getStackFromProject(project, stackId);
		stack.dismissProject();
		stackRepository.deleteById(stackId);
		
		return true;
    }

}
