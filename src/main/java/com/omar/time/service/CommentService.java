package com.omar.time.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.omar.time.dto.CommentCreationDTO;
import com.omar.time.dto.CommentUpdatingDTO;
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
	
	
	public Comment create(UserPrincipal userPrincipal, CommentCreationDTO commentCreationDTO, long projectId, long stackId, long cardId) {
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
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found");
		}
		
		Comment comment = ObjectMapperUtils.map(commentCreationDTO, Comment.class);
		
		comment.setCard(card);
        
		return commentRepository.save(comment);
    }
	
	public Comment update(UserPrincipal userPrincipal, CommentUpdatingDTO commentUpdatingDTO, long projectId, long stackId, long cardId, long commentId) {
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
			
			comment = ObjectMapperUtils.map(commentUpdatingDTO, Comment.class);

			comment.setId(commentId);	
			comment.setCard(card);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found");
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
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found");
        }
		
		return true;
    }
	
}
