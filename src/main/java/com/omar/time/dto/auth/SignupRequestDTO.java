package com.omar.time.dto.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SignupRequestDTO {

	@Size(max = 40, message = "{errors.validation.user.firstName.maxLength}")
    private String firstName;
    
    @Size(max = 40, message = "{errors.validation.user.lastName.maxLength}")
    private String lastName;

    @NotBlank(message = "{errors.validation.user.username.notBlank}")
    @Size(min = 3, max = 40, message = "{errors.validation.user.username.minMax}")
    private String username;
    
    @Size(max = 20, message = "{errors.validation.user.mobile.maxLength}")
    private String mobile;

    @NotBlank(message = "{errors.validation.user.email.notBlank}")
    @Size(max = 40, message = "{errors.validation.user.email.maxLength}")
    @Email(message = "{errors.validation.user.email.mailValidity}")
    private String email;

    @NotBlank(message = "{errors.validation.user.password.notBlank}")
    @Size(min = 8, max = 20, message = "{errors.validation.user.password.minMax}")
    private String password;
	
}
