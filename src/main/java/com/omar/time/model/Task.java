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

@Entity
@Table(name = "tasks")
@DynamicUpdate
public class Task extends UserDateAudit {

	/**
	 * 
	 */
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
	
	public Task() {}
	
	public Task(long id, String task, LocalDateTime dueDate, StatusName status, Card card) {
		super();
		this.id = id;
		this.task = task;
		this.dueDate = dueDate;
		this.status = status;
		this.card = card;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public LocalDateTime getLocalDateTime() {
		return dueDate;
	}

	public void setLocalDateTime(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public StatusName getStatus() {
		return status;
	}

	public void setStatus(StatusName status) {
		this.status = status;
	}

	public void setCard(Card card) {
		this.card = card;
	}
	
	public void dismissCard() {
		this.card.dismissStack();
		this.card.dismissTask(this);
		this.card = null;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", task=" + task + ", dueDate=" + dueDate + ", status=" + status
				+ ", card=" + card + "]";
	}
	
}
