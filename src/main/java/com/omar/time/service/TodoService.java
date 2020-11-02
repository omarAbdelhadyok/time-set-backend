package com.omar.time.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omar.time.dto.todo.CreateTodoDTO;
import com.omar.time.dto.todo.TodoDto;
import com.omar.time.dto.todo.UpdateTodoDTO;
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
		Todo todo = todoRepository.findByIdAndCreatedBy(id, userPrincipal.getId()).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.todo.notFound")
		);
		
		return ObjectMapperUtils.map(todo, TodoDto.class);
	}
	
	public TodoDto create(CreateTodoDTO createTodoDTO) {
		Todo todo = ObjectMapperUtils.map(createTodoDTO, Todo.class);
		todo.setStatus(TodoStatusName.ACTIVE);
		todo = todoRepository.save(todo);
		
		return ObjectMapperUtils.map(todo, TodoDto.class);
    }
	
	//@ToDo review this method again
	@Transactional
	public TodoDto update(UserPrincipal userPrincipal, UpdateTodoDTO updateTodoDTO) {
		Todo todo = todoRepository.findByIdAndCreatedBy(updateTodoDTO.getId(), userPrincipal.getId()).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.todo.notFound")
		);
		
		if(todo.getStatus() != TodoStatusName.ACTIVE) {
			throw new BadRequestException("errors.app.todo.cancelledClosedNotUpdatable");
		}
		todo = todoRepository.save(todo);
		
		return ObjectMapperUtils.map(todo, TodoDto.class);
	}
	
	public boolean delete(UserPrincipal userPrincipal, long id) {
		todoRepository.findByIdAndCreatedBy(id, userPrincipal.getId()).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.todo.notFound")
		);
	
		todoRepository.deleteById(id);
		
		return true;
	}
	
}
