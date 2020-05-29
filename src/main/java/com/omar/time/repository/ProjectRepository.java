package com.omar.time.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omar.time.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findByCreatedBy(long userId, Pageable pageable);
	
}
