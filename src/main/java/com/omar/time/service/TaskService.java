package com.omar.time.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omar.time.dto.task.CreateTaskDTO;
import com.omar.time.dto.task.TaskDTO;
import com.omar.time.dto.task.UpdateTaskDTO;
import com.omar.time.dto.task.UpdateTaskStatusDTO;
import com.omar.time.exception.BadRequestException;
import com.omar.time.model.Card;
import com.omar.time.model.Task;
import com.omar.time.model.enums.StatusName;
import com.omar.time.repository.CardRepository;
import com.omar.time.repository.TaskRepository;
import com.omar.time.security.UserPrincipal;
import com.omar.time.util.ObjectMapperUtils;

@Service
public class TaskService {

	private TaskRepository taskRepository;
	private CardRepository cardRepository;
	
	
	@Autowired
	public TaskService(TaskRepository taskRepository, CardRepository cardRepository) {
		this.taskRepository = taskRepository;
		this.cardRepository = cardRepository;
	}
		
	@Transactional
	public TaskDTO create(UserPrincipal userPrincipal, CreateTaskDTO createTaskDTO, long cardId) {
		Card card = cardRepository.findCard(userPrincipal.getId(), cardId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.card.notFound")
		);
				
		Task task = ObjectMapperUtils.map(createTaskDTO, Task.class);
		task.setStatus(StatusName.ACTIVE);
		task.setCard(card);
		
		task = taskRepository.save(task);
		
		return ObjectMapperUtils.map(task, TaskDTO.class);
    }
	
	@Transactional
	public TaskDTO update(UserPrincipal userPrincipal, UpdateTaskDTO updateTaskDTO) {		
		Task task = taskRepository.findTask(userPrincipal.getId(), updateTaskDTO.getId()).orElseThrow(() ->
			new EntityNotFoundException("errors.app.task.notFound")
		);
				
		ObjectMapperUtils.copyPropertiesForUpdate(updateTaskDTO, task);
		
		//cancelled or closed tasks cannot be updated
		if(task.getStatus() != StatusName.ACTIVE) {
			throw new BadRequestException("errors.app.task.cancelledClosedNotUpdatable");
		}
		
		task = taskRepository.save(task);
		
		return ObjectMapperUtils.map(task, TaskDTO.class);
    }
	
	@Transactional
	public TaskDTO updateStatus(UserPrincipal userPrincipal, UpdateTaskStatusDTO updateTaskStatusDTO) {
		Task task = taskRepository.findTask(userPrincipal.getId(), updateTaskStatusDTO.getId()).orElseThrow(() ->
			new EntityNotFoundException("errors.app.task.notFound")
		);
		
		ObjectMapperUtils.copyPropertiesForUpdate(updateTaskStatusDTO, task);
		
		task = taskRepository.save(task);
		
		return ObjectMapperUtils.map(task, TaskDTO.class);
	}
	
	@Transactional
	public boolean delete(UserPrincipal userPrincipal, long taskId) {
		taskRepository.deleteById(taskId);
		
		return true;
    }

}
