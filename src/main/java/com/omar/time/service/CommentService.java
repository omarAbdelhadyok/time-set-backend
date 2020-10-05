package com.omar.time.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omar.time.dto.comment.CommentDTO;
import com.omar.time.model.Card;
import com.omar.time.model.Comment;
import com.omar.time.model.Project;
import com.omar.time.model.Stack;
import com.omar.time.repository.CommentRepository;
import com.omar.time.repository.ProjectRepository;
import com.omar.time.security.UserPrincipal;
import com.omar.time.util.ObjectMapperUtils;

@Service
public class CommentService {

	private CommentRepository commentRepository;	
	private ProjectRepository projectRepository;
	
	@Autowired
	public CommentService(CommentRepository commentRepository, ProjectRepository projectRepository) {
		this.commentRepository = commentRepository;
		this.projectRepository = projectRepository;
	}
	
	
	public Comment create(UserPrincipal userPrincipal, CommentDTO commentDTO, long projectId, long stackId, long cardId) {
		Project project = projectRepository.findById(projectId).orElseThrow(() ->
			new EntityNotFoundException("errors.app.project.notFound")
		);
		
		UtilService.handleUnathorized(project, userPrincipal);
		Stack stack = UtilService.getStackFromProject(project, stackId);
		Card card = UtilService.getCardFromStack(stack, cardId);	
		Comment comment = ObjectMapperUtils.map(commentDTO, Comment.class);
		comment.setCard(card);
        
		return commentRepository.save(comment);
    }
	
	@Transactional
	public Comment update(UserPrincipal userPrincipal, CommentDTO commentDTO, long projectId, long stackId, long cardId, long commentId) {
		Project project = projectRepository.findById(projectId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);
		
		UtilService.handleUnathorized(project, userPrincipal);
		Stack stack = UtilService.getStackFromProject(project, stackId);
		Card card = UtilService.getCardFromStack(stack, cardId);
		UtilService.getCommentFromCard(card, commentId);
		Comment comment = ObjectMapperUtils.map(commentDTO, Comment.class);
		comment.setId(commentId);	
		comment.setCard(card);
		
        return commentRepository.save(comment);
    }
	
	public boolean delete(UserPrincipal userPrincipal, long projectId, long stackId, long cardId, long commentId) {
		Project project = projectRepository.findById(projectId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);
		
		UtilService.handleUnathorized(project, userPrincipal);
		Stack stack = UtilService.getStackFromProject(project, stackId);
		Card card = UtilService.getCardFromStack(stack, cardId);
		Comment comment = UtilService.getCommentFromCard(card, commentId);
		comment.dismissCard();
		commentRepository.deleteById(commentId);
				
		return true;
    }
	
}
