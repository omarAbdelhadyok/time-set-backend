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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cards")
public class Card extends UserDateAudit {

	private static final long serialVersionUID = 4666330781050608563L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(max = 50)
	private String title;
	
	@Size(max = 150)
	private String description;
	
	@ManyToOne
    @JoinColumn(name = "stack_id")
    @NotNull
    private Stack stack;
	
	@OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comment> comments;
	
	@OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Task> tasks;

	public Card(long id, String title, Stack stack) {
		this.id = id;
		this.title = title;
		this.stack = stack;
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
	
}