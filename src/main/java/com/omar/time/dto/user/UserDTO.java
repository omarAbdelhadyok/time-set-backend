package com.omar.time.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

	private long id;

    private String firstName;
    
    private String lastName;

    private String username;

    private String email;
    
    private boolean isActivatedMail;	
}
