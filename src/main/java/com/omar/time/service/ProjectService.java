package com.omar.time.service;

import java.util.Optional;

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
<<<<<<< HEAD
			throw new EntityNotFoundException("errors.app.project.notFound");
=======
			throw new EntityNotFoundException("Project not found");
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
		}

		return ObjectMapperUtils.map(project, ProjectDTO.class);
	}
	
	public ProjectDTO addAuthor(UserPrincipal userPrincipal, long projectId, long userId) {
		if(userPrincipal.getId() == userId) {
<<<<<<< HEAD
			throw new BadRequestException("errors.app.project.alreadyOwner");
=======
			throw new BadRequestException("You are the owner of this project");
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
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
<<<<<<< HEAD
						throw new BadRequestException("errors.app.project.alreadyAuthor");
=======
						throw new BadRequestException("User is already an author");
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
					}
				}
				
				project.addAuthor(user);
			} else {
<<<<<<< HEAD
				throw new EntityNotFoundException("errors.app.user.notFound");
			}
		} else {
			throw new EntityNotFoundException("errors.app.project.notFound");
=======
				throw new EntityNotFoundException("User Not Found");
			}
		} else {
			throw new EntityNotFoundException("Project Not Found");
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
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
<<<<<<< HEAD
				throw new BadRequestException("errors.app.project.cancelledClosedNotUpdatable");
			}
			project.setId(projectId);
		} else {
			throw new EntityNotFoundException("errors.app.project.notFound");
=======
				throw new BadRequestException("Closed or cancelled projects cannot be updated");
			}
			project.setId(projectId);
		} else {
			throw new EntityNotFoundException("Project Not Found");
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
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
<<<<<<< HEAD
			throw new EntityNotFoundException("errors.app.project.notFound");
=======
			throw new EntityNotFoundException("Project Not Found");
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
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
<<<<<<< HEAD
			throw new EntityNotFoundException("errors.app.project.notFound");
=======
			throw new EntityNotFoundException("Project Not Found");
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
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
<<<<<<< HEAD
				throw new EntityNotFoundException("errors.app.user.notFound");
=======
				throw new EntityNotFoundException("User Not Found");
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
			}
			
			projectRepository.save(project);
		} else {
<<<<<<< HEAD
			throw new EntityNotFoundException("errors.app.project.notFound");
=======
			throw new EntityNotFoundException("Project Not Found");
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
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
<<<<<<< HEAD
 				throw new EntityNotFoundException("errors.app.user.notFound");
=======
 				throw new EntityNotFoundException("User Not Found");
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
 			}
 			
 			projectRepository.save(project);
 		} else {
<<<<<<< HEAD
 			throw new EntityNotFoundException("errors.app.project.notFound");
=======
 			throw new EntityNotFoundException("Project Not Found");
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
         }
     	return true;
    }
	
}
