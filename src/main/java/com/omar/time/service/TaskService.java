package com.omar.time.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.omar.time.dto.task.TaskDTO;
import com.omar.time.model.Card;
import com.omar.time.model.Project;
import com.omar.time.model.Stack;
import com.omar.time.model.Task;
import com.omar.time.model.enums.StatusName;
import com.omar.time.repository.ProjectRepository;
import com.omar.time.repository.TaskRepository;
import com.omar.time.security.UserPrincipal;
import com.omar.time.util.ObjectMapperUtils;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
		
	public Task create(UserPrincipal userPrincipal, TaskDTO taskDTO, long projectId, long stackId, long cardId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		Stack stack = null;
		Card card = null;
		
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
			stack = UtilService.getStackFromProject(project, stackId);
			card = UtilService.getCardFromStack(stack, cardId);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found");
		}
		
		Task task = ObjectMapperUtils.map(taskDTO, Task.class);
		
		task.setStatus(StatusName.ACTIVE);
		task.setCard(card);
        
		return taskRepository.save(task);
    }
	
	public Task update(UserPrincipal userPrincipal, TaskDTO taskDTO, long projectId, long stackId, long cardId, long taskId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		Stack stack = null;
		Card card = null;
		Task task = null;
		
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
			stack = UtilService.getStackFromProject(project, stackId);
			card = UtilService.getCardFromStack(stack, cardId);
			UtilService.getTaskFromCard(card, taskId);
			
			task = ObjectMapperUtils.map(taskDTO, Task.class);
			if(task.getStatus() != StatusName.ACTIVE) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Closed or cancelled projects cannot be updated");
			}
			task.setId(taskId);	
			task.setCard(card);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found");
		}
		
        return taskRepository.save(task);
    }
	
	
	public Task updateStatus(UserPrincipal userPrincipal, TaskDTO taskDTO, long projectId, long stackId, long cardId, long taskId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		Stack stack = null;
		Card card = null;
		Task task = null;
		
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
			stack = UtilService.getStackFromProject(project, stackId);
			card = UtilService.getCardFromStack(stack, cardId);
			UtilService.getTaskFromCard(card, taskId);
			
			task = ObjectMapperUtils.map(taskDTO, Task.class);

			task.setId(taskId);	
			task.setCard(card);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found");
		}
		
		return taskRepository.save(task);
	}
	
	public boolean delete(UserPrincipal userPrincipal, long projectId, long stackId, long cardId, long taskId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		Stack stack = null;
		Card card = null;
		Task task = null;
		
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
			stack = UtilService.getStackFromProject(project, stackId);
			card = UtilService.getCardFromStack(stack, cardId);
			task = UtilService.getTaskFromCard(card, taskId);
			
			task.dismissCard();
            taskRepository.deleteById(taskId);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found");
        }
		
		return true;
    }

}
