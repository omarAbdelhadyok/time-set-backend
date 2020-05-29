package com.omar.time.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omar.time.dto.CommentCreationDTO;
import com.omar.time.dto.CommentDTO;
import com.omar.time.model.Comment;
import com.omar.time.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@GetMapping("/project/{projectId}")
	public Page<CommentDTO> getAll(@PathVariable long projectId, Pageable pageable) {
		return commentService.getAll(projectId, pageable);
	}
	
	@GetMapping("/{projectId}/{id}")
	public CommentDTO get(@PathVariable long projectId, @PathVariable long id) {
		return commentService.get(projectId, id);
	}
	
	@PostMapping("/{projectId}")
	public Comment create(@RequestBody CommentCreationDTO commentCreationDTO, @PathVariable long projectId) {
		return commentService.create(commentCreationDTO, projectId);
	}
	
	@PutMapping("/{id}")
	public Comment update(@RequestBody CommentCreationDTO commentCreationDTO, @PathVariable long id) {
		return commentService.update(commentCreationDTO, id);
	}
	
	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable long id) {
		return commentService.delete(id);
	}
	
}
