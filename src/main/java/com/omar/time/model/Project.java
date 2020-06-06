package com.omar.time.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	private String img;
		
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Stack> stacks;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "project_users",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> authors = new LinkedList<>();
	
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

	public List<User> getAuthors() {
		return authors;
	}

	public void setAuthors(List<User> authors) {
		this.authors = authors;
	}
	
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void addAuthor(User author) {
		this.authors.add(author);
	}
	
	public boolean deleteAuthor(User author) {
		boolean deleted = false;
		for(int index = 0; index < this.getAuthors().size(); index++) {
			if(this.getAuthors().get(index).getId() == author.getId()) {
				this.authors.remove(index);
				deleted = true;
			} else {
				throw new RuntimeException("User is not an author");
			}
		}
		
		return deleted;
	}

	public void dismissStack(Stack stack) {
        this.stacks.remove(stack);
    }
	
	@Override
	public String toString() {
		return "Project [id=" + id + ", title=" + title + ", description=" + description + ", status=" + status
				+ ", stacks=" + stacks + "]";
	}
	
}
