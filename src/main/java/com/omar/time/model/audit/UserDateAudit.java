package com.omar.time.model.audit;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@MappedSuperclass
@JsonIgnoreProperties(
    value = {"createdBy", "updatedBy"},
    allowGetters = true
)
public abstract class UserDateAudit extends DateAudit {
    
	private static final long serialVersionUID = -8302738905364683701L;

	@CreatedBy
    @Column(nullable = false, updatable = false)
    private Long createdBy;

    @LastModifiedBy
    @Column(nullable = false)
    private Long updatedBy;

}