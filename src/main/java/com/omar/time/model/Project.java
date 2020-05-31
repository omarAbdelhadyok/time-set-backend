package com.omar.time.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.omar.time.model.audit.UserDateAudit;

@Entity
@Table(name = "projects")
public class Project extends UserDateAudit {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6232773173017994344L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(max = 50)
	private String title;
	
	@NotNull
	@Size(max = 250)
	private String description;
	
	@NotNull
	private StatusName status;
		
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Stack> stacks;
	
	public Project() {}

	public Project(String title, String description, StatusName status) {
		this.title = title;
		this.description = description;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public StatusName getStatus() {
		return status;
	}

	public void setStatus(StatusName status) {
		this.status = status;
	}

	public List<Stack> getStacks() {
		return stacks;
	}

	public void setStacks(List<Stack> stacks) {
		this.stacks = stacks;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", title=" + title + ", description=" + description + ", status=" + status
				+ ", stacks=" + stacks + "]";
	}
	
}
