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
import com.omar.time.model.Project;
import com.omar.time.model.StatusName;
import com.omar.time.repository.ProjectRepository;
import com.omar.time.security.UserPrincipal;
import com.omar.time.util.ObjectMapperUtils;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	
	public Page<AllProjectsDTO> getAll(UserPrincipal userPrincipal, Pageable pageable) {
		Page<Project> page = projectRepository.findByCreatedBy(userPrincipal.getId(), pageable); 
		return new PageImpl<AllProjectsDTO>(ObjectMapperUtils.mapAll(page.getContent(), AllProjectsDTO.class), pageable, page.getTotalElements());
	}
	
	public ProjectDTO get(long id) {
		Optional<Project> result = projectRepository.findById(id);
		
		Project project = null;
		
		if(result.isPresent()) {
			project = result.get();
		} else {
			throw new RuntimeException("Project with id of " + id + " was not found");
		}

		return ObjectMapperUtils.map(project, ProjectDTO.class);
	}
	
	public Project create(ProjectCreationDTO projectCreationDTO) {
		Project project = ObjectMapperUtils.map(projectCreationDTO, Project.class);
		project.setStatus(StatusName.ACTIVE);
        return projectRepository.save(project);
    }
	
	public Project update(ProjectCreationDTO projectCreationDTO, long id) {
		Project project = ObjectMapperUtils.map(projectCreationDTO, Project.class);
		project.setId(id);
        return projectRepository.save(project);
    }

    public boolean delete(long id) {
        Optional<Project> result = projectRepository.findById(id);
		
		if(result.isPresent()) {
            projectRepository.deleteById(id);
		} else {
			throw new RuntimeException("Project with id of " + id + " was not found");
        }
		
		return true;
    }
	
}
