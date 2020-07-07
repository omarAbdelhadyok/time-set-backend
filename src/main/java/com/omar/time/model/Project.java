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
import com.omar.time.model.enums.StatusName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "projects")
public @Data class Project extends UserDateAudit {
	
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
	

	public Project(String title, String description, StatusName status) {
		this.title = title;
		this.description = description;
		this.status = status;
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
	
}
