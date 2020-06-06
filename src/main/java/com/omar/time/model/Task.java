package com.omar.time.model;

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
	
	@NotNull
	private int duration;
	
	@NotNull
	private UnitName unit;
	
	@NotNull
	private StatusName status;
	
	@ManyToOne
    @JoinColumn(name = "card_id", updatable = false)
    @NotNull
    private Card card;
	
	public Task() {}

	public Task(String task, int duration, UnitName unit, StatusName status, Card card) {
		this.task = task;
		this.duration = duration;
		this.unit = unit;
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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public UnitName getUnit() {
		return unit;
	}

	public void setUnit(UnitName unit) {
		this.unit = unit;
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
		return "Task [id=" + id + ", task=" + task + ", duration=" + duration + ", unit=" + unit + ", status=" + status
				+ ", card=" + card + ", careate_at=" + getCreatedAt() + ", updated_at" + getUpdatedAt() + "]";
	}
	
}
