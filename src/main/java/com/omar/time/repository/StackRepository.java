package com.omar.time.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.omar.time.model.Stack;

@Repository
public interface StackRepository extends JpaRepository<Stack, Long> {
	
	Page<Stack> findByProjectId(long projectId, Pageable pageable);
    Optional<Stack> findByIdAndProjectId(long id, long projectId);
    
    @Query("SELECT s FROM Stack s"
    		+ " JOIN Project p ON s.project = p.id"
    		+ " JOIN p.editors e"
    		+ " WHERE s.id = :id AND (p.createdBy = :userId OR e.id = :userId)")
    Optional<Stack> findStack(@Param(value = "userId") final long userId, @Param(value = "id") final long id);

}
