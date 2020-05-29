package com.omar.time.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.omar.time.dto.CommentCreationDTO;
import com.omar.time.dto.CommentDTO;
import com.omar.time.model.Comment;
import com.omar.time.model.Project;
import com.omar.time.repository.CommentRepository;
import com.omar.time.repository.ProjectRepository;
import com.omar.time.util.ObjectMapperUtils;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	
	public Page<CommentDTO> getAll(long projectId, Pageable pageable) {
		Page<Comment> page = commentRepository.findByProjectId(projectId, pageable);
		return new PageImpl<CommentDTO>(ObjectMapperUtils.mapAll(page.getContent(), CommentDTO.class), pageable, page.getTotalElements());
	}
	
	public CommentDTO get(long projectId, long id) {
		Optional<Comment> result = commentRepository.findByIdAndProjectId(id, projectId);
		
		Comment comment = null;
		
		if(result.isPresent()) {
			comment = result.get();
		} else {
			throw new RuntimeException("Comment with id of " + id + " was not found");
		}

		return ObjectMapperUtils.map(comment, CommentDTO.class);
	}
	
	public Comment create(CommentCreationDTO commentCreationDTO, long projectId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		
		if(result.isPresent()) {
			project = result.get();
		} else {
			throw new RuntimeException("Project with id of " + projectId + " was not found");
		}
		
		Comment comment = ObjectMapperUtils.map(commentCreationDTO, Comment.class);
		
		comment.setProject(project);
        
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
