package com.omar.time.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import com.omar.time.model.audit.UserDateAudit;
import com.omar.time.model.enums.StatusName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tasks")
@DynamicUpdate
public class Task extends UserDateAudit {

	private static final long serialVersionUID = -261060587250708700L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(max = 150)
	private String task;
	
	private LocalDateTime dueDate;
	
	@NotNull
	private StatusName status;
	
	@ManyToOne
    @JoinColumn(name = "card_id", updatable = false)
    @NotNull
    private Card card;
	
	
	public Task(long id, String task, LocalDateTime dueDate, StatusName status, Card card) {
		super();
		this.id = id;
		this.task = task;
		this.dueDate = dueDate;
		this.status = status;
		this.card = card;
	}
	
}
