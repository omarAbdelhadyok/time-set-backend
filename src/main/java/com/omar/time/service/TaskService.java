package com.omar.time.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omar.time.dto.task.TaskDTO;
import com.omar.time.exception.BadRequestException;
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

	private TaskRepository taskRepository;
	private ProjectRepository projectRepository;
	
	@Autowired
	public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
		this.taskRepository = taskRepository;
		this.projectRepository = projectRepository;
	}
	
		
	public Task create(UserPrincipal userPrincipal, TaskDTO taskDTO, long projectId, long stackId, long cardId) {
		Project project = projectRepository.findById(projectId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);
		
		UtilService.handleUnathorized(project, userPrincipal);
		Stack stack = UtilService.getStackFromProject(project, stackId);
		Card card = UtilService.getCardFromStack(stack, cardId);
		Task task = ObjectMapperUtils.map(taskDTO, Task.class);
		task.setStatus(StatusName.ACTIVE);
		task.setCard(card);
        
		return taskRepository.save(task);
    }
	
	@Transactional
	public Task update(UserPrincipal userPrincipal, TaskDTO taskDTO, long projectId, long stackId, long cardId, long taskId) {
		Project project = projectRepository.findById(projectId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);
		
		UtilService.handleUnathorized(project, userPrincipal);
		Stack stack = UtilService.getStackFromProject(project, stackId);
		Card card = UtilService.getCardFromStack(stack, cardId);
		Task task = UtilService.getTaskFromCard(card, taskId);	
		ObjectMapperUtils.copyPropertiesForUpdate(taskDTO, task);
		if(task.getStatus() != StatusName.ACTIVE) {
			throw new BadRequestException("errors.app.task.cancelledClosedNotUpdatable");
		}
		task.setId(taskId);	
		task.setCard(card);
		
        return taskRepository.save(task);
    }
	
	@Transactional
	public Task updateStatus(UserPrincipal userPrincipal, TaskDTO taskDTO, long projectId, long stackId, long cardId, long taskId) {
		Project project = projectRepository.findById(projectId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);
		
		UtilService.handleUnathorized(project, userPrincipal);
		Stack stack = UtilService.getStackFromProject(project, stackId);
		Card card = UtilService.getCardFromStack(stack, cardId);
		Task task = UtilService.getTaskFromCard(card, taskId);
		ObjectMapperUtils.copyPropertiesForUpdate(taskDTO, task);
		task.setId(taskId);	
		task.setCard(card);
		
		return taskRepository.save(task);
	}
	
	public boolean delete(UserPrincipal userPrincipal, long projectId, long stackId, long cardId, long taskId) {
		Project project = projectRepository.findById(projectId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);
	
		UtilService.handleUnathorized(project, userPrincipal);
		Stack stack = UtilService.getStackFromProject(project, stackId);
		Card card = UtilService.getCardFromStack(stack, cardId);
		Task task = UtilService.getTaskFromCard(card, taskId);
		task.dismissCard();
        taskRepository.deleteById(taskId);
		
		return true;
    }

}
