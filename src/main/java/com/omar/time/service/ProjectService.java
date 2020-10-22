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
		User user = ObjectMapperUtils.map(userPrincipal, User.class);
		
		Project project = projectRepository.findByIdAndCreatedByOrEditors(projectId, userPrincipal.getId(), user).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);

		return ObjectMapperUtils.map(project, ProjectDTO.class);
	}
	
	@Transactional
	public boolean addEditor(UserPrincipal userPrincipal, long projectId, long editorId) {
		Project project = projectRepository.findByIdAndCreatedBy(projectId, userPrincipal.getId()).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);
		User user = userRepository.findById(editorId).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.user.notFound")
		);
		
		//throw error if the editor id is the same as owner id 
		if(userPrincipal.getId() == editorId) {
			throw new BadRequestException("errors.app.project.alreadyOwner");
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
	public ProjectDTO update(UserPrincipal userPrincipal, ProjectDTO projectDTO) {
		Project project = projectRepository.findByIdAndCreatedBy(projectDTO.getId(), userPrincipal.getId()).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);
		
		ObjectMapperUtils.copyPropertiesForUpdate(projectDTO, project);
		
		//check if project is not active so it cannot be updated
		if(project.getStatus() != StatusName.ACTIVE) {
			throw new BadRequestException("errors.app.project.cancelledClosedNotUpdatable");
		}
		
		project = projectRepository.save(project);
		
		return ObjectMapperUtils.map(project, ProjectDTO.class);
    }
	
	@Transactional
	public ProjectDTO updateStatus(UserPrincipal userPrincipal, ProjectDTO projectDTO) {
		Project project = projectRepository.findByIdAndCreatedBy(projectDTO.getId(), userPrincipal.getId()).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.project.notFound")
		);
		
		ObjectMapperUtils.copyPropertiesForUpdate(projectDTO, project);
		project = projectRepository.save(project);
		
		return ObjectMapperUtils.map(project, ProjectDTO.class);
	}

    public boolean delete(UserPrincipal userPrincipal, long projectId) {
        Project project = projectRepository.findByIdAndCreatedBy(projectId, userPrincipal.getId()).orElseThrow(() -> 
        	new EntityNotFoundException("errors.app.project.notFound")
		);
		
        projectRepository.delete(project);
		return true;
    }
    
    @Transactional
    public boolean deleteAuthorityFromProject(UserPrincipal userPrincipal, long projectId) {
    	User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> 
			new EntityNotFoundException("errors.app.user.notFound")
		);
    	Project project = projectRepository.findByIdAndCreatedByOrEditors(projectId, userPrincipal.getId(), user)
		.orElseThrow(() -> 
    		new EntityNotFoundException("errors.app.project.notFound")
		);
    	
		//check if user is owner
    	if(project.getCreatedBy() == user.getId()) {
    		throw new BadRequestException("errors.app.project.cannotTakeAction");
    	}
    	
		project.deleteEditor(user);
		projectRepository.save(project);
		
    	return true;
    }
    
    @Transactional
    public boolean deleteEditorFromProject(UserPrincipal userPrincipal, long projectId, long editorId) {
    	User editor = userRepository.findById(editorId).orElseThrow(() -> 
    		new EntityNotFoundException("errors.app.user.notFound")
		);
    	Project project = projectRepository.findByIdAndCreatedByOrEditors(projectId, userPrincipal.getId(), editor)
		.orElseThrow(() ->
    		new EntityNotFoundException("errors.app.project.notFound")
		);
		
		//throw access denied exception if user is not the owner of this project
		if(project.getCreatedBy() != userPrincipal.getId()) {
			throw new AccessDeniedException("errors.app.project.notOwner");
		}
		project.deleteEditor(editor);	
		projectRepository.save(project);
 		
     	return true;
    }
	
}
