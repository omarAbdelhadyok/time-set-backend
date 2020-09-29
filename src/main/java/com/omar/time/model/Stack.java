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
@Table(name = "stacks")
public class Stack extends UserDateAudit {

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

	public Stack(long id, String title, Project project) {
		this.id = id;
		this.title = title;
		this.project = project;
	}
	
	public void dismissProject() {
		this.project.dismissStack(this);
		this.project = null;
	}
	
	public void dismissCard(Card card) {
		this.cards.remove(card);
	}
	
}
