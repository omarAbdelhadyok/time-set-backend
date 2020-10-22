package com.omar.time.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.omar.time.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

	Page<Card> findByStackId(long stackId, Pageable pageable);
    Optional<Card> findByIdAndStackId(long id, long stackId);
    
    @Query("SELECT c FROM Card c"
    		+ " JOIN Stack s ON c.stack = s.id"
    		+ " JOIN Project p ON s.project = p.id"
    		+ " JOIN p.editors e"
    		+ " WHERE c.id = :id AND (p.createdBy = :userId OR e.id = :userId)")
    Optional<Card> findCard(@Param(value = "userId") final long userId, @Param(value = "id") final long id);
	
}
