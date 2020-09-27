package com.omar.time.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omar.time.dto.stack.StackDTO;
import com.omar.time.model.Project;
import com.omar.time.model.Stack;
import com.omar.time.repository.ProjectRepository;
import com.omar.time.repository.StackRepository;
import com.omar.time.security.UserPrincipal;
import com.omar.time.util.ObjectMapperUtils;

@Service
public class StackService {

	@Autowired
	private StackRepository stackRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	
	public StackDTO create(UserPrincipal userPrincipal, StackDTO stackDTO, long projectId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
		} else {
			throw new EntityNotFoundException("errors.app.project.notFound");
		}
		
		Stack stack = ObjectMapperUtils.map(stackDTO, Stack.class);
		
		stack.setProject(project);
        
		stack = stackRepository.save(stack);
		return ObjectMapperUtils.map(stack, StackDTO.class);
    }
	
	public StackDTO update(UserPrincipal userPrincipal, StackDTO stackDTO, long projectId, long stackId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		Stack stack = null;
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
			UtilService.getStackFromProject(project, stackId);
			stack = ObjectMapperUtils.map(stackDTO, Stack.class);
			stack.setId(stackId);
			stack.setProject(project);
		} else {
			throw new EntityNotFoundException("errors.app.project.notFound");
		}
		
		stack = stackRepository.save(stack);
		return ObjectMapperUtils.map(stack, StackDTO.class);
    }
	
	public boolean delete(UserPrincipal userPrincipal, long projectId, long stackId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		Stack stack = null;
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
			stack = UtilService.getStackFromProject(project, stackId);
			stack.dismissProject();
			stackRepository.deleteById(stackId);
		} else {
			throw new EntityNotFoundException("errors.app.project.notFound");
        }
		
		return true;
    }

}
