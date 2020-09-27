package com.omar.time.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	
	public Comment create(UserPrincipal userPrincipal, CommentDTO commentDTO, long projectId, long stackId, long cardId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		Stack stack = null;
		Card card = null;
		
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
			stack = UtilService.getStackFromProject(project, stackId);
			card = UtilService.getCardFromStack(stack, cardId);
		} else {
			throw new EntityNotFoundException("errors.app.project.notFound");
		}
		
		Comment comment = ObjectMapperUtils.map(commentDTO, Comment.class);
		
		comment.setCard(card);
        
		return commentRepository.save(comment);
    }
	
	public Comment update(UserPrincipal userPrincipal, CommentDTO commentDTO, long projectId, long stackId, long cardId, long commentId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		Stack stack = null;
		Card card = null;
		Comment comment = null;
		
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
			stack = UtilService.getStackFromProject(project, stackId);
			card = UtilService.getCardFromStack(stack, cardId);
			UtilService.getCommentFromCard(card, commentId);
			
			comment = ObjectMapperUtils.map(commentDTO, Comment.class);

			comment.setId(commentId);	
			comment.setCard(card);
		} else {
			throw new EntityNotFoundException("errors.app.project.notFound");
		}
		
        return commentRepository.save(comment);
    }
	
	public boolean delete(UserPrincipal userPrincipal, long projectId, long stackId, long cardId, long commentId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		Stack stack = null;
		Card card = null;
		Comment comment = null;
		
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
			stack = UtilService.getStackFromProject(project, stackId);
			card = UtilService.getCardFromStack(stack, cardId);
			comment = UtilService.getCommentFromCard(card, commentId);
			
			comment.dismissCard();
			commentRepository.deleteById(commentId);
		} else {
			throw new EntityNotFoundException("errors.app.project.notFound");
        }
		
		return true;
    }
	
}
