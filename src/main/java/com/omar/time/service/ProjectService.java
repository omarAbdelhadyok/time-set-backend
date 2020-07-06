package com.omar.time.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.omar.time.dto.project.AllProjectsDTO;
import com.omar.time.dto.project.ProjectDTO;
import com.omar.time.model.Project;
import com.omar.time.model.User;
import com.omar.time.model.enums.StatusName;
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
		return new PageImpl<AllProjectsDTO>(ObjectMapperUtils.mapAll(
				page.getContent(), AllProjectsDTO.class), pageable, page.getTotalElements());
	}
	
	public ProjectDTO get(UserPrincipal userPrincipal, long projectId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found");
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
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already an author");
					}
				}
				
				project.addAuthor(user);
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found");
		}
		
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
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
			ObjectMapperUtils.copyPropertiesForUpdate(projectDTO, project);
			if(project.getStatus() != StatusName.ACTIVE) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Closed or cancelled projects cannot be updated");
			}
			project.setId(projectId);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found");
		}
		project = projectRepository.save(project);
		return ObjectMapperUtils.map(project, ProjectDTO.class);
    }
	
	public ProjectDTO updateStatus(UserPrincipal userPrincipal, ProjectDTO projectDTO, long projectId) {
		Optional<Project> result = projectRepository.findById(projectId);
		
		Project project = null;
		if(result.isPresent()) {
			project = result.get();
			UtilService.handleUnathorized(project, userPrincipal);
			ObjectMapperUtils.copyPropertiesForUpdate(projectDTO, project);
			project.setId(projectId);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found");
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
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found");
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
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
			}
			
			projectRepository.save(project);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found");
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
 				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
 			}
 			
 			projectRepository.save(project);
 		} else {
 			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project Not Found");
         }
     	return true;
    }
	
}
