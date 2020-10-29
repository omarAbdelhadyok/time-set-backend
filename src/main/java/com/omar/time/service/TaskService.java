package com.omar.time.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omar.time.dto.task.TaskDTO;
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
	public Task create(UserPrincipal userPrincipal, TaskDTO taskDTO, long cardId) {
		Card card = cardRepository.findCard(userPrincipal.getId(), cardId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.card.notFound")
		);
				
		Task task = ObjectMapperUtils.map(taskDTO, Task.class);
		task.setStatus(StatusName.ACTIVE);
		task.setCard(card);
		
		return taskRepository.save(task);
    }
	
	@Transactional
	public Task update(UserPrincipal userPrincipal, TaskDTO taskDTO) {		
		Task task = taskRepository.findTask(userPrincipal.getId(), taskDTO.getId()).orElseThrow(() ->
			new EntityNotFoundException("errors.app.task.notFound")
		);
				
		ObjectMapperUtils.copyPropertiesForUpdate(taskDTO, task);
		
		//cancelled or closed tasks cannot be updated
		if(task.getStatus() != StatusName.ACTIVE) {
			throw new BadRequestException("errors.app.task.cancelledClosedNotUpdatable");
		}
		
		return taskRepository.save(task);
    }
	
	@Transactional
	public Task updateStatus(UserPrincipal userPrincipal, TaskDTO taskDTO) {
		Task task = taskRepository.findTask(userPrincipal.getId(), taskDTO.getId()).orElseThrow(() ->
			new EntityNotFoundException("errors.app.task.notFound")
		);
		
		ObjectMapperUtils.copyPropertiesForUpdate(taskDTO, task);
		
		return taskRepository.save(task);
	}
	
	@Transactional
	public boolean delete(UserPrincipal userPrincipal, long taskId) {
		taskRepository.deleteById(taskId);
		
		return true;
    }

}
