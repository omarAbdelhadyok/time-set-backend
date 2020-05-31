package com.omar.time.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omar.time.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	Page<Comment> findByCardId(long cardId, Pageable pageable);
    Optional<Comment> findByIdAndCardId(long id, long cardId);
	
}
