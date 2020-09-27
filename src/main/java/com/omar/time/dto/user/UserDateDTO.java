package com.omar.time.dto.user;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDateDTO {
	
	private Instant createdAt;
		
	private Instant updatedAt;
	
	private Long createdBy;

    private Long updatedBy;

}
