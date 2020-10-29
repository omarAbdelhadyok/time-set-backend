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

import com.omar.time.model.audit.UserDateAudit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "comments")
public class Comment extends UserDateAudit {

	private static final long serialVersionUID = 4862412025483650698L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(max = 250)
	private String comment;
	
	@ManyToOne
    @JoinColumn(name = "card_id")
    @NotNull
    private Card card;

	public Comment(String comment, Card card) {
		this.comment = comment;
		this.card = card;
	}
	
}
