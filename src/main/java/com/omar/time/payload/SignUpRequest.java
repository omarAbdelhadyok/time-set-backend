package com.omar.time.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
	
    @Size(max = 40)
    private String firstName;
    
    @Size(max = 40)
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 15)
    private String username;
    
    @Size(max = 20)
    private String mobile;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 20)
    private String password;

}