package com.omar.time.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.omar.time.dto.StackCreationDTO;
import com.omar.time.dto.StackDTO;
import com.omar.time.model.Project;
import com.omar.time.model.Stack;
import com.omar.time.repository.ProjectRepository;
import com.omar.time.repository.StackRepository;
import com.omar.time.util.ObjectMapperUtils;

@Service
public class StackService {

	@Autowired
	private StackRepository stackRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	
	public Page<StackDTO> getAll(long projectId, Pageable pageable) {
		Page<Stack> page = stackRepository.findByProjectId(projectId, pageable);
		return new PageImpl<StackDTO>(ObjectMapperUtils.mapAll(page.getContent(), StackDTO.class), pageable, page.getTotalElements());
	}
	
	public StackDTO get(long projectId, long id) {
		Optional<Stack> result = stackRepository.findByIdAndProjectId(id, projectId);
		
		Stack stack = null;
		
		if(result.isPresent()) {
			stack = result.get();
		} else {
			throw new RuntimeException("Stack with id of " + id + " was not found");
		}

		return ObjectMapperUtils.map(stack, StackDTO.class);
	}
	
	public Stack create(StackCreationDTO stackCreateUpdateDTO, long projectId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		
		if(result.isPresent()) {
			project = result.get();
		} else {
			throw new RuntimeException("Project with id of " + projectId + " was not found");
		}
		
		Stack stack = ObjectMapperUtils.map(stackCreateUpdateDTO, Stack.class);
		
		stack.setProject(project);
        
		return stackRepository.save(stack);
    }
	
	public Stack update(StackCreationDTO stackUpdatingDTO, long id) {
		Optional<Stack> result = stackRepository.findById(id);
		
		Stack stack = null;
		if(result.isPresent()) {
			stack = ObjectMapperUtils.map(stackUpdatingDTO, Stack.class);
			stack.setId(id);	
		} else {
			throw new RuntimeException("Stack with id of " + id + " was not found");
		}
		
        return stackRepository.save(stack);
    }
		
	public boolean delete(long id) {
        Optional<Stack> result = stackRepository.findById(id);
		
		if(result.isPresent()) {
            stackRepository.deleteById(id);
		} else {
			throw new RuntimeException("Stack with id of " + id + " was not found");
        }
		
		return true;
    }

}
