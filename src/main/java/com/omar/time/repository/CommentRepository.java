package com.omar.time.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.omar.time.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	Page<Comment> findByCardId(long cardId, Pageable pageable);
    Optional<Comment> findByIdAndCardId(long id, long cardId);
	
    @Query("SELECT m FROM Comment m"
    		+ " JOIN Card c ON m.card = c.id"
    		+ " JOIN Stack s ON c.stack = s.id"
    		+ " JOIN Project p ON s.project = p.id"
    		+ " JOIN p.editors e"
    		+ " WHERE c.id = :id AND (p.createdBy = :userId OR e.id = :userId)")
    Optional<Comment> findComment(@Param(value = "userId") final long userId, @Param(value = "id") final long id);
}
