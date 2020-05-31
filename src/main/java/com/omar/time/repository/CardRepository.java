package com.omar.time.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omar.time.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

	Page<Card> findByStackId(long stackId, Pageable pageable);
    Optional<Card> findByIdAndStackId(long id, long stackId);
	
}
