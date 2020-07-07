package com.omar.time.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.omar.time.model.audit.UserDateAudit;
import com.omar.time.model.enums.TodoStatusName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "todos")
public @Data class Todo extends UserDateAudit {

	private static final long serialVersionUID = 1693573394440176145L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 200)
	private String todo;
	
	@NotBlank
	private TodoStatusName status;

	
	public Todo(Long id, String todo, TodoStatusName status) {
		this.id = id;
		this.todo = todo;
		this.status = status;
	}

}
