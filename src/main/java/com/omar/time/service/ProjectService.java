package com.omar.time.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		
		Page<Project> page = projectRepository.findByCreatedByOrEditors(userPrincipal.getId(), user, pageable); 
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
	
	@Transactional
	public boolean addEditor(UserPrincipal userPrincipal, long projectId, long editorId) {
		
		Project project = projectRepository.findById(projectId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);
		User user = userRepository.findById(editorId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.user.notFound")
		);
		
		//throw error if the author id is the same as the user id (owner) 
		if((userPrincipal.getId() == editorId) && (userPrincipal.getId() == project.getCreatedBy())) {
			throw new BadRequestException("errors.app.project.alreadyOwner");
		}

		//throw not found exception if user is not project owner nor he's an author
		UtilService.handleUnathorized(project, userPrincipal);
		
		//throw access denied exception if user is not the owner of this project
		if(project.getCreatedBy() != userPrincipal.getId()) {
			throw new AccessDeniedException("errors.app.project.notOwner");
		}
		
		//check if authorId already exist in authors list of this project
		for(User author: project.getEditors()) {
			if(author.getId() == editorId) {
				throw new BadRequestException("errors.app.project.alreadyEditor");
			}
		}
		project.addAuthor(user);
		project = projectRepository.save(project);
		return true;
	}
	
	public ProjectDTO create(ProjectDTO projectDTO) {
		Project project = ObjectMapperUtils.map(projectDTO, Project.class);
		project.setStatus(StatusName.ACTIVE);
		project = projectRepository.save(project);
		return ObjectMapperUtils.map(project, ProjectDTO.class);
    }
	
	@Transactional
	public ProjectDTO update(UserPrincipal userPrincipal, ProjectDTO projectDTO, long projectId) {
		Project project = projectRepository.findById(projectId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);
		
		UtilService.handleUnathorized(project, userPrincipal);
		ObjectMapperUtils.copyPropertiesForUpdate(projectDTO, project);
		
		//throw access denied exception if user is not the owner of this project
		if(project.getCreatedBy() != userPrincipal.getId()) {
			throw new AccessDeniedException("errors.app.project.notOwner");
		}
		
		//check if project is not active so it cannot be updated
		if(project.getStatus() != StatusName.ACTIVE) {
			throw new BadRequestException("errors.app.project.cancelledClosedNotUpdatable");
		}
		project.setId(projectId);
		project = projectRepository.save(project);
		
		return ObjectMapperUtils.map(project, ProjectDTO.class);
    }
	
	@Transactional
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
        
        //throw access denied exception if user is not the owner of this project
  		if(project.getCreatedBy() != userPrincipal.getId()) {
  			throw new AccessDeniedException("errors.app.project.notOwner");
  		}
		
		UtilService.handleUnathorized(project, userPrincipal);
        projectRepository.deleteById(id);
        
		return true;
    }
    
    @Transactional
    public boolean deleteAuthorityFromProject(UserPrincipal userPrincipal, long projectId) {
    	Project project = projectRepository.findById(projectId).orElseThrow(() -> 
    		new EntityNotFoundException("errors.app.project.notFound")
		);
    	User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> 
    		new EntityNotFoundException("errors.app.user.notFound")
		);
    	
		UtilService.handleUnathorized(project, userPrincipal);		
		project.deleteEditor(user);
		projectRepository.save(project);
		
    	return true;
    }
    
    @Transactional
    public boolean deleteEditorFromProject(UserPrincipal userPrincipal, long projectId, long editorId) {
    	Project project = projectRepository.findById(projectId).orElseThrow(() ->
    		new EntityNotFoundException("errors.app.project.notFound")
		);
    	User editor = userRepository.findById(editorId).orElseThrow(() -> 
    		new EntityNotFoundException("errors.app.user.notFound")
		);
    	
		UtilService.handleUnathorized(project, userPrincipal);
		
		//throw access denied exception if user is not the owner of this project
		if(project.getCreatedBy() != userPrincipal.getId()) {
			throw new AccessDeniedException("errors.app.project.notOwner");
		}
		project.deleteEditor(editor);	
		projectRepository.save(project);
 		
     	return true;
    }
	
}
