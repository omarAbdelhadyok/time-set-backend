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

import com.omar.time.dto.comment.CreateCommentDTO;
import com.omar.time.dto.comment.UpdateCommentDTO;
import com.omar.time.model.Comment;
import com.omar.time.security.CurrentUser;
import com.omar.time.security.UserPrincipal;
import com.omar.time.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	private CommentService commentService;
	
	
	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@PostMapping("/{cardId}")
	public Comment create(@CurrentUser UserPrincipal userPrincipal,
			@Validated @RequestBody CreateCommentDTO createCommentDTO,
			@PathVariable long cardId) {
		return commentService.create(userPrincipal, createCommentDTO, cardId);
	}
	
	@PutMapping
	public Comment update(@CurrentUser UserPrincipal userPrincipal,
			@Validated @RequestBody UpdateCommentDTO updateCommentDTO) {
		return commentService.update(userPrincipal, updateCommentDTO);
	}
	
	@DeleteMapping("/{commentId}")
	public boolean delete(@CurrentUser UserPrincipal userPrincipal, @PathVariable long commentId) {
		return commentService.delete(userPrincipal, commentId);
	}
	
}
