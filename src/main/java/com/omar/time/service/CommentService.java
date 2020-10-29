package com.omar.time.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omar.time.dto.comment.CommentDTO;
import com.omar.time.model.Card;
import com.omar.time.model.Comment;
import com.omar.time.repository.CardRepository;
import com.omar.time.repository.CommentRepository;
import com.omar.time.security.UserPrincipal;
import com.omar.time.util.ObjectMapperUtils;

@Service
public class CommentService {

	private CommentRepository commentRepository;
	private CardRepository cardRepository;
	
	
	@Autowired
	public CommentService(CommentRepository commentRepository, CardRepository cardRepository) {
		this.commentRepository = commentRepository;
		this.cardRepository = cardRepository;
	}
	
	@Transactional
	public Comment create(UserPrincipal userPrincipal, CommentDTO commentDTO, long cardId) {
		Card card = cardRepository.findCard(userPrincipal.getId(), cardId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.card.notFound")
		);
		
		Comment comment = ObjectMapperUtils.map(commentDTO, Comment.class);
		comment.setCard(card);
		
		return commentRepository.save(comment);
    }
	
	@Transactional
	public Comment update(UserPrincipal userPrincipal, CommentDTO commentDTO) {
		Comment comment = commentRepository.findComment(userPrincipal.getId(), commentDTO.getId()).orElseThrow(() ->
			new EntityNotFoundException("errors.app.comment.notFound")
		);
		
		ObjectMapperUtils.copyPropertiesForUpdate(commentDTO, comment);
		
		return commentRepository.save(comment);
    }
	
	@Transactional
	public boolean delete(UserPrincipal userPrincipal, long commentId) {
		commentRepository.deleteById(commentId);
				
		return true;
    }
	
}
