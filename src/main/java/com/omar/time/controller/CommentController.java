package com.omar.time.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omar.time.dto.Create;
import com.omar.time.dto.Update;
import com.omar.time.dto.comment.CommentDTO;
import com.omar.time.model.Comment;
import com.omar.time.security.CurrentUser;
import com.omar.time.security.UserPrincipal;
import com.omar.time.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	
	@PostMapping("/{projectId}/{stackId}/{cardId}")
	public Comment create(@CurrentUser UserPrincipal userPrincipal,
			@Validated(Create.class) @RequestBody CommentDTO commentDTO,
			@PathVariable long projectId,
			@PathVariable long stackId,
			@PathVariable long cardId) {
		return commentService.create(userPrincipal, commentDTO, projectId, stackId, cardId);
	}
	
	@PutMapping("/{projectId}/{stackId}/{cardId}/{commentId}")
	public Comment update(@CurrentUser UserPrincipal userPrincipal,
			@Validated(Update.class) @RequestBody CommentDTO commentDTO,
			@PathVariable long projectId,
			@PathVariable long stackId,
			@PathVariable long cardId,
			@PathVariable long commentId) {
		return commentService.update(userPrincipal, commentDTO, projectId, stackId, cardId, commentId);
	}
	
	@DeleteMapping("/{projectId}/{stackId}/{cardId}/{commentId}")
	public boolean delete(@CurrentUser UserPrincipal userPrincipal,
			@PathVariable long projectId,
			@PathVariable long stackId,
			@PathVariable long cardId,
			@PathVariable long commentId) {
		return commentService.delete(userPrincipal, projectId, stackId, cardId, commentId);
	}
	
}
