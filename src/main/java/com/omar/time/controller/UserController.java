package com.omar.time.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omar.time.dto.user.UserDTO;
import com.omar.time.model.Role;
import com.omar.time.repository.RoleRepository;
import com.omar.time.security.CurrentUser;
import com.omar.time.security.UserPrincipal;
import com.omar.time.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public UserDTO get(@CurrentUser UserPrincipal userPrincipal) {
		return userService.get(userPrincipal);
	}
	
	@GetMapping("/{userNameOrEmail}")
	public List<UserDTO> findUser(@PathVariable String userNameOrEmail) {
		return userService.findUser(userNameOrEmail);
	}
	
	@PostMapping("/role")
	public Role createRole(@RequestBody Role role) {
		return roleRepository.save(role);
	}

	@GetMapping("/resend-confirmation")
    public boolean resendConfirmation(@CurrentUser UserPrincipal userPrincipal) {
    	return userService.resendConfirmation(userPrincipal);
    }
}
