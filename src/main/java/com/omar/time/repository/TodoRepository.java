package com.omar.time.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omar.time.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
	
	Page<Todo> findByCreatedBy(long userId, Pageable pageable);
	Optional<Todo> findByIdAndCreatedBy(Long id, long userId);

}
