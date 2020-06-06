package com.omar.time.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.omar.time.dto.AllProjectsDTO;
import com.omar.time.dto.ProjectCreationDTO;
import com.omar.time.dto.ProjectDTO;
import com.omar.time.dto.ProjectStatusUpdateDTO;
import com.omar.time.dto.ProjectUpdatingDTO;
import com.omar.time.model.Project;
import com.omar.time.model.StatusName;
import com.omar.time.model.User;
import com.omar.time.repository.ProjectRepository;
import com.omar.time.repository.UserRepository;
import com.omar.time.security.UserPrincipal;
import com.omar.time.util.ObjectMapperUtils;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	public Page<AllProjectsDTO> getAll(UserPrincipal userPrincipal, Pageable pageable) {
		User user = ObjectMapperUtils.map(userPrincipal, User.class);
		
		Page<Project> page = projectRepository.findByCreatedByOrAuthors(userPrincipal.getId(), user, pageable); 
		return new PageImpl<AllProjectsDTO>(ObjectMapperUtils.mapAll(page.getContent(), AllProjectsDTO.class), pageable, page.getTotalElements());
	}
	
	public ProjectDTO get(UserPrincipal userPrincipal, long projectId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
		} else {
			throw new RuntimeException("Project with id of " + projectId + " was not found");
		}

		return ObjectMapperUtils.map(project, ProjectDTO.class);
	}
	
	public ProjectDTO addAuthor(UserPrincipal userPrincipal, long projectId, long userId) {
		if(userPrincipal.getId() == userId) {
			throw new RuntimeException("Something went wrong");
		}
		
		Optional<Project> result = projectRepository.findById(projectId);
		Optional<User> userResult = userRepository.findById(userId);
		
		Project project = null;
		User user = null;
		
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
			
			if(userResult.isPresent()) {
				user = userResult.get();
				project.setId(projectId);
				
				for(User author: project.getAuthors()) {
					if(author.getId() == userId) {
						throw new RuntimeException("User is already an author");
					}
				}
				
				project.addAuthor(user);
			} else {
				throw new RuntimeException("User with id of " + userId + " was not found");
			}
		} else {
			throw new RuntimeException("Project with id of " + projectId + " was not found");
		}
		
		project = projectRepository.save(project);
		return ObjectMapperUtils.map(project, ProjectDTO.class);
	}
	
	public ProjectDTO create(ProjectCreationDTO projectCreationDTO) {
		Project project = ObjectMapperUtils.map(projectCreationDTO, Project.class);
		project.setStatus(StatusName.ACTIVE);
		project = projectRepository.save(project);
		return ObjectMapperUtils.map(project, ProjectDTO.class);
        
    }
	
	public ProjectDTO update(UserPrincipal userPrincipal, ProjectUpdatingDTO projectUpdatingDTO, long projectId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
			project = ObjectMapperUtils.map(projectUpdatingDTO, Project.class);
			if(project.getStatus() != StatusName.ACTIVE) {
				throw new RuntimeException("You cannot update cancelled or closed projects");
			}
			project.setId(projectId);
		} else {
			throw new RuntimeException("Project with id of " + projectId + " was not found");
		}
		project = projectRepository.save(project);
		return ObjectMapperUtils.map(project, ProjectDTO.class);
    }
	
	public ProjectDTO updateStatus(UserPrincipal userPrincipal, ProjectStatusUpdateDTO projectStatusUpdateDTO, long projectId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
			project = ObjectMapperUtils.map(projectStatusUpdateDTO, Project.class);
			project.setId(projectId);
		} else {
			throw new RuntimeException("Project with id of " + projectId + " was not found");
		}
		
		project = projectRepository.save(project);
		return ObjectMapperUtils.map(project, ProjectDTO.class);
	}

    public boolean delete(UserPrincipal userPrincipal, long id) {
        Optional<Project> result = projectRepository.findById(id);
		
        Project project = null;
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
            projectRepository.deleteById(id);
		} else {
			throw new RuntimeException("Project with id of " + id + " was not found");
        }
		
		return true;
    }
    
    public boolean deleteAuthorityFromProject(UserPrincipal userPrincipal, long projectId) {
    	Optional<Project> result = projectRepository.findById(projectId);
    	Optional<User> userResult = userRepository.findById(userPrincipal.getId());
    	
        Project project = null;
        User author = null; 
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
			
			if(userResult.isPresent()) {
				project.deleteAuthor(author);
			} else {
				throw new RuntimeException("User with id of " + userPrincipal.getId() + " was not found");
			}
			
			projectRepository.save(project);
		} else {
			throw new RuntimeException("Project with id of " + projectId + " was not found");
        }
    	return true;
    }
    
    public boolean deleteAuthorFromProject(UserPrincipal userPrincipal, long projectId, long userId) {
    	Optional<Project> result = projectRepository.findById(projectId);
    	Optional<User> userResult = userRepository.findById(userPrincipal.getId());
    	
    	 Project project = null;
         User author = null; 
 		if(result.isPresent()) {
 			project = result.get();
 			UtilService.handleUnathorized(project, userPrincipal);
 			
 			if(userResult.isPresent()) {
 				project.deleteAuthor(author);
 			} else {
 				throw new RuntimeException("User with id of " + userPrincipal.getId() + " was not found");
 			}
 			
 			projectRepository.save(project);
 		} else {
 			throw new RuntimeException("Project with id of " + projectId + " was not found");
         }
     	return true;
    }
	
}
