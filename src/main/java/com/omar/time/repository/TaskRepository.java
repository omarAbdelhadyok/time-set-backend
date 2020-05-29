package com.omar.time.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omar.time.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	Page<Task> findByProjectId(long projectId, Pageable pageable);
    Optional<Task> findByIdAndProjectId(long id, long projectId);
    Page<Task> findByCreatedBy(long userId, Pageable pageable);
}
