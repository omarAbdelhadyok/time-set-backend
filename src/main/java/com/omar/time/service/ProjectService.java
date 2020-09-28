package com.omar.time.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.omar.time.dto.project.AllProjectsDTO;
import com.omar.time.dto.project.ProjectDTO;
import com.omar.time.exception.BadRequestException;
import com.omar.time.model.Project;
import com.omar.time.model.User;
import com.omar.time.model.enums.StatusName;
import com.omar.time.repository.ProjectRepository;
import com.omar.time.repository.UserRepository;
import com.omar.time.security.UserPrincipal;
import com.omar.time.util.ObjectMapperUtils;

@Service
public class ProjectService {

	private ProjectRepository projectRepository;
	private UserRepository userRepository;
	
	@Autowired
	public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
		this.projectRepository = projectRepository;
		this.userRepository = userRepository;
	}
	
	
	public Page<AllProjectsDTO> getAll(UserPrincipal userPrincipal, Pageable pageable) {
		User user = ObjectMapperUtils.map(userPrincipal, User.class);
		
		Page<Project> page = projectRepository.findByCreatedByOrAuthors(userPrincipal.getId(), user, pageable); 
		return new PageImpl<AllProjectsDTO>(ObjectMapperUtils.mapAll(
				page.getContent(), AllProjectsDTO.class), pageable, page.getTotalElements());
	}
	
	public ProjectDTO get(UserPrincipal userPrincipal, long projectId) {
		Project project = projectRepository.findById(projectId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);

		UtilService.handleUnathorized(project, userPrincipal);

		return ObjectMapperUtils.map(project, ProjectDTO.class);
	}
	
	public ProjectDTO addAuthor(UserPrincipal userPrincipal, long projectId, long userId) {
		if(userPrincipal.getId() == userId) {
			throw new BadRequestException("errors.app.project.alreadyOwner");
		}
		Project project = projectRepository.findById(projectId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);
		User user = userRepository.findById(userId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.user.notFound")
		);
			
		UtilService.handleUnathorized(project, userPrincipal);		
		project.setId(projectId);		
		for(User author: project.getAuthors()) {
			if(author.getId() == userId) {
				throw new BadRequestException("errors.app.project.alreadyAuthor");
			}
		}
		project.addAuthor(user);
		project = projectRepository.save(project);
		return ObjectMapperUtils.map(project, ProjectDTO.class);
	}
	
	public ProjectDTO create(ProjectDTO projectDTO) {
		Project project = ObjectMapperUtils.map(projectDTO, Project.class);
		project.setStatus(StatusName.ACTIVE);
		project = projectRepository.save(project);
		return ObjectMapperUtils.map(project, ProjectDTO.class);
    }
	
	public ProjectDTO update(UserPrincipal userPrincipal, ProjectDTO projectDTO, long projectId) {
		Project project = projectRepository.findById(projectId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);
		
		UtilService.handleUnathorized(project, userPrincipal);
		ObjectMapperUtils.copyPropertiesForUpdate(projectDTO, project);
		if(project.getStatus() != StatusName.ACTIVE) {
			throw new BadRequestException("errors.app.project.cancelledClosedNotUpdatable");
		}
		project.setId(projectId);
		project = projectRepository.save(project);
		
		return ObjectMapperUtils.map(project, ProjectDTO.class);
    }
	
	public ProjectDTO updateStatus(UserPrincipal userPrincipal, ProjectDTO projectDTO, long projectId) {
		Project project = projectRepository.findById(projectId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);
		
		UtilService.handleUnathorized(project, userPrincipal);
		ObjectMapperUtils.copyPropertiesForUpdate(projectDTO, project);
		project.setId(projectId);	
		project = projectRepository.save(project);
		
		return ObjectMapperUtils.map(project, ProjectDTO.class);
	}

    public boolean delete(UserPrincipal userPrincipal, long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> 
        	new EntityNotFoundException("errors.app.project.notFound")
		);
		
		UtilService.handleUnathorized(project, userPrincipal);
        projectRepository.deleteById(id);
        
		return true;
    }
    
    public boolean deleteAuthorityFromProject(UserPrincipal userPrincipal, long projectId) {
    	Project project = projectRepository.findById(projectId).orElseThrow(() -> 
    		new EntityNotFoundException("errors.app.project.notFound")
		);
    	User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> 
    		new EntityNotFoundException("errors.app.user.notFound")
		);
    	
		UtilService.handleUnathorized(project, userPrincipal);		
		project.deleteAuthor(user);
		projectRepository.save(project);
		
    	return true;
    }
    
    public boolean deleteAuthorFromProject(UserPrincipal userPrincipal, long projectId, long userId) {
    	Project project = projectRepository.findById(projectId).orElseThrow(() ->
    		new EntityNotFoundException("errors.app.project.notFound")
		);
    	User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> 
    		new EntityNotFoundException("errors.app.user.notFound")
		);
    	
		UtilService.handleUnathorized(project, userPrincipal);
		project.deleteAuthor(user);	
		projectRepository.save(project);
 		
     	return true;
    }
	
}
