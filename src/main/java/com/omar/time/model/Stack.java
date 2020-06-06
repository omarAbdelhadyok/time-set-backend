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
@Table(name = "stacks")
public class Stack extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2496911141918545637L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(max = 50)
	private String title;
	
	@ManyToOne
    @JoinColumn(name = "project_id")
    @NotNull
    private Project project;
	
	@OneToMany(mappedBy = "stack", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Card> cards;
	
	public Stack() {}

	public Stack(long id, String title, Project project) {
		this.id = id;
		this.title = title;
		this.project = project;
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

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	
	public void dismissProject() {
		this.project.dismissStack(this);
		this.project = null;
	}
	
	public void dismissCard(Card card) {
		this.cards.remove(card);
	}

	@Override
	public String toString() {
		return "Stack [id=" + id + ", title=" + title + ", project=" + project + ", cards=" + cards + "]";
	}
	
}
