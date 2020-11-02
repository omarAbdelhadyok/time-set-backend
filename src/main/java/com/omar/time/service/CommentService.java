package com.omar.time.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omar.time.dto.comment.CreateCommentDTO;
import com.omar.time.dto.comment.UpdateCommentDTO;
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
	public Comment create(UserPrincipal userPrincipal, CreateCommentDTO createCommentDTO, long cardId) {
		Card card = cardRepository.findCard(userPrincipal.getId(), cardId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.card.notFound")
		);
		
		Comment comment = ObjectMapperUtils.map(createCommentDTO, Comment.class);
		comment.setCard(card);
		
		return commentRepository.save(comment);
    }
	
	@Transactional
	public Comment update(UserPrincipal userPrincipal, UpdateCommentDTO updateCommentDTO) {
		Comment comment = commentRepository.findComment(userPrincipal.getId(), updateCommentDTO.getId())
		.orElseThrow(() ->
			new EntityNotFoundException("errors.app.comment.notFound")
		);
		
		ObjectMapperUtils.copyPropertiesForUpdate(updateCommentDTO, comment);
		
		return commentRepository.save(comment);
    }
	
	@Transactional
	public boolean delete(UserPrincipal userPrincipal, long commentId) {
		commentRepository.deleteById(commentId);
				
		return true;
    }
	
}
