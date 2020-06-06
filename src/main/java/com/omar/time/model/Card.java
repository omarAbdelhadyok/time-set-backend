package com.omar.time.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.omar.time.model.audit.UserDateAudit;

@Entity
@Table(name = "cards")
public class Card extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4666330781050608563L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(max = 50)
	private String title;
	
	@ManyToOne
    @JoinColumn(name = "stack_id")
    @NotNull
    private Stack stack;
	
	@OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comment> comments;
	
	@OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Task> tasks;
	
	public Card() {}

	public Card(long id, String title, Stack stack) {
		this.id = id;
		this.title = title;
		this.stack = stack;
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

	public void setStack(Stack stack) {
		this.stack = stack;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public void dismissStack() {
		this.stack.dismissCard(this);
		this.stack.dismissProject();
		this.stack = null;
	}
	
	public void dismissTask(Task task) {
		this.tasks.remove(task);
	}
	
	public void dismissComment(Comment comment) {
		this.comments.remove(comment);
	}

	@Override
	public String toString() {
		return "Card [id=" + id + ", title=" + title + ", stack=" + stack + ", tasks=" + tasks + "]";
	}
	
	
}