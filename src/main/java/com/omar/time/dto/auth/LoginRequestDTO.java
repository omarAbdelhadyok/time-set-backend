package com.omar.time.dto.auth;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "{errors.validation.user.usernameOrEmal.notBlank}")
    private String usernameOrEmail;

    @NotBlank(message = "{errors.validation.user.password.notBlank}")
    private String password;
	
}
