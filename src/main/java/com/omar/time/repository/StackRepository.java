package com.omar.time.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omar.time.model.Stack;

@Repository
public interface StackRepository extends JpaRepository<Stack, Long> {
	
	Page<Stack> findByProjectId(long projectId, Pageable pageable);
    Optional<Stack> findByIdAndProjectId(long id, long projectId);

}
