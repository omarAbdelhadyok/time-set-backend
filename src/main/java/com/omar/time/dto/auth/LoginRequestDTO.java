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

<<<<<<< HEAD
    @NotBlank(message = "{errors.validation.user.usernameOrEmal.notBlank}")
    private String usernameOrEmail;

    @NotBlank(message = "{errors.validation.user.password.notBlank}")
=======
    @NotBlank(message = "{errors.user.usernameOrEmal.notBlank}")
    private String usernameOrEmail;

    @NotBlank(message = "{errors.user.password.notBlank}")
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
    private String password;
	
}
