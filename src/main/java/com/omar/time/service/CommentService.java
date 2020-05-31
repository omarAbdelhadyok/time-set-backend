package com.omar.time.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.omar.time.dto.CommentCreationDTO;
import com.omar.time.dto.CommentDTO;
import com.omar.time.model.Card;
import com.omar.time.model.Comment;
import com.omar.time.repository.CardRepository;
import com.omar.time.repository.CommentRepository;
import com.omar.time.util.ObjectMapperUtils;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private CardRepository cardRepository;
	
	
	public Page<CommentDTO> getAll(long cardId, Pageable pageable) {
		Page<Comment> page = commentRepository.findByCardId(cardId, pageable);
		return new PageImpl<CommentDTO>(ObjectMapperUtils.mapAll(page.getContent(), CommentDTO.class), pageable, page.getTotalElements());
	}
	
	public CommentDTO get(long projectId, long id) {
		Optional<Comment> result = commentRepository.findByIdAndCardId(id, projectId);
		
		Comment comment = null;
		
		if(result.isPresent()) {
			comment = result.get();
		} else {
			throw new RuntimeException("Comment with id of " + id + " was not found");
		}

		return ObjectMapperUtils.map(comment, CommentDTO.class);
	}
	
	public Comment create(CommentCreationDTO commentCreationDTO, long cardId) {
		Optional<Card> result = cardRepository.findById(cardId);
		
		Card card = null;
		
		if(result.isPresent()) {
			card = result.get();
		} else {
			throw new RuntimeException("Card with id of " + cardId + " was not found");
		}
		
		Comment comment = ObjectMapperUtils.map(commentCreationDTO, Comment.class);
		
		comment.setCard(card);
        
		return commentRepository.save(comment);
    }
	
	public Comment update(CommentCreationDTO commentCreationDTO, long id) {
		Comment comment = ObjectMapperUtils.map(commentCreationDTO, Comment.class);
		comment.setId(id);
        return commentRepository.save(comment);
    }
	
	public boolean delete(long id) {
        Optional<Comment> result = commentRepository.findById(id);
		
		if(result.isPresent()) {
            commentRepository.deleteById(id);
		} else {
			throw new RuntimeException("Comment with id of " + id + " was not found");
        }
		
		return true;
    }
	
}
