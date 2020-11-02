package com.omar.time.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.omar.time.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	Page<Task> findByCardId(long cardId, Pageable pageable);
    Optional<Task> findByIdAndCardId(long id, long cardId);
    
    @Query("SELECT t FROM Task t"
    		+ " JOIN Card c ON t.card = c.id"
    		+ " JOIN Stack s ON c.stack = s.id"
    		+ " JOIN Project p ON s.project = p.id"
    		+ " JOIN p.editors e"
    		+ " WHERE t.id = :id AND (p.createdBy = :userId OR e.id = :userId)")
    Optional<Task> findTask(@Param(value = "userId") final long userId, @Param(value = "id") final long id);
    
}
