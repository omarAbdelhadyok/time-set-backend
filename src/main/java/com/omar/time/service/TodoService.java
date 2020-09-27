package com.omar.time.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.omar.time.dto.todo.TodoDto;
import com.omar.time.exception.BadRequestException;
import com.omar.time.model.Todo;
import com.omar.time.model.enums.TodoStatusName;
import com.omar.time.repository.TodoRepository;
import com.omar.time.security.UserPrincipal;
import com.omar.time.util.ObjectMapperUtils;

@Service
public class TodoService {

	private TodoRepository todoRepository;
	
	
	@Autowired
	public TodoService(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}
	
	
	public Page<TodoDto> getAll(UserPrincipal userPrincipal, Pageable pageable) {
		Page<Todo> page = todoRepository.findByCreatedBy(userPrincipal.getId(), pageable); 
		return new PageImpl<TodoDto>(ObjectMapperUtils.mapAll(
				page.getContent(), TodoDto.class), pageable, page.getTotalElements());
	}
	
	public TodoDto get(UserPrincipal userPrincipal, long id) {
		Optional<Todo> result = todoRepository.findByIdAndCreatedBy(id, userPrincipal.getId());
		
		Todo todo = null;
		if(result.isPresent()) {
			todo = result.get();
		} else {
			throw new EntityNotFoundException("Todo Not Found");
		}

		return ObjectMapperUtils.map(todo, TodoDto.class);
	}
	
	public TodoDto create(TodoDto todoDto) {
		Todo todo = ObjectMapperUtils.map(todoDto, Todo.class);
		todo.setStatus(TodoStatusName.ACTIVE);
		todo = todoRepository.save(todo);
		return ObjectMapperUtils.map(todo, TodoDto.class);
    }
	
	public TodoDto update(UserPrincipal userPrincipal, long id, TodoDto todoDto) {
		Optional<Todo> result = todoRepository.findByIdAndCreatedBy(id, userPrincipal.getId());
		
		Todo todo = null;
		if(result.isPresent()) {
			todo = result.get();
			if(todo.getStatus() != TodoStatusName.ACTIVE) {
				throw new BadRequestException("Closed or cancelled todos cannot be updated");
			}
			todo.setId(id);
		} else {
			throw new EntityNotFoundException("Todo Not Found");
		}
		
		todo = todoRepository.save(todo);
		
		return ObjectMapperUtils.map(todo, TodoDto.class);
	}
	
	public boolean delete(UserPrincipal userPrincipal, long id) {
		Optional<Todo> result = todoRepository.findByIdAndCreatedBy(id, userPrincipal.getId());
		
		if(result.isPresent()) {
			todoRepository.deleteById(id);
		} else {
			throw new EntityNotFoundException("Todo Not Found");
		}
		
		return true;
	}
	
}
