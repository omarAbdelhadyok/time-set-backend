package com.omar.time.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.omar.time.dto.TaskCreationDTO;
import com.omar.time.dto.TaskDTO;
import com.omar.time.dto.TaskStatusUpdateDTO;
import com.omar.time.dto.TaskUpdatingDTO;
import com.omar.time.model.Card;
import com.omar.time.model.StatusName;
import com.omar.time.model.Task;
import com.omar.time.repository.CardRepository;
import com.omar.time.repository.TaskRepository;
import com.omar.time.util.ObjectMapperUtils;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private CardRepository cardRepository;
	
	
	public Page<TaskDTO> getAll(long cardId, Pageable pageable) {
		Page<Task> page = taskRepository.findByCardId(cardId, pageable);
		return new PageImpl<TaskDTO>(ObjectMapperUtils.mapAll(page.getContent(), TaskDTO.class), pageable, page.getTotalElements());
	}
	
	public TaskDTO get(long projectId, long id) {
		Optional<Task> result = taskRepository.findByIdAndCardId(id, projectId);
		
		Task task = null;
		
		if(result.isPresent()) {
			task = result.get();
		} else {
			throw new RuntimeException("Task with id of " + id + " was not found");
		}

		return ObjectMapperUtils.map(task, TaskDTO.class);
	}
	
	public Task create(TaskCreationDTO taskCreationDTO, long cardId) {
		Optional<Card> result = cardRepository.findById(cardId);
		
		Card card = null;
		
		if(result.isPresent()) {
			card = result.get();
		} else {
			throw new RuntimeException("Card with id of " + cardId + " was not found");
		}
		
		Task task = ObjectMapperUtils.map(taskCreationDTO, Task.class);
		
		task.setStatus(StatusName.ACTIVE);
		
		task.setCard(card);
        
		return taskRepository.save(task);
    }
	
	public Task update(TaskUpdatingDTO taskUpdatingDTO, long id) {
		Optional<Task> result = taskRepository.findById(id);
		
		Task task = null;
		if(result.isPresent()) {
			task = ObjectMapperUtils.map(taskUpdatingDTO, Task.class);
			if(task.getStatus() != StatusName.ACTIVE) {
				throw new RuntimeException("You cannot update cancelled or closed tasks");
			}
			task.setId(id);	
		} else {
			throw new RuntimeException("Task with id of " + id + " was not found");
		}
		
        return taskRepository.save(task);
    }
	
	public Task updateStatus(TaskStatusUpdateDTO taskStatusUpdateDTO, long id) {
		Optional<Task> result = taskRepository.findById(id);
		
		Task task = null;
		if(result.isPresent()) {
			task = ObjectMapperUtils.map(taskStatusUpdateDTO, Task.class);
			task.setId(id);
		} else {
			throw new RuntimeException("Task with id of " + id + " was not found");
		}
		
		return taskRepository.save(task);
	}
	
	public boolean delete(long id) {
        Optional<Task> result = taskRepository.findById(id);
		
		if(result.isPresent()) {
            taskRepository.deleteById(id);
		} else {
			throw new RuntimeException("Task with id of " + id + " was not found");
        }
		
		return true;
    }

}
