package com.omar.time.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omar.time.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	Page<Task> findByCardId(long cardId, Pageable pageable);
    Optional<Task> findByIdAndCardId(long id, long cardId);
    
}
