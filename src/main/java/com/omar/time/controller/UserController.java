package com.omar.time.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omar.time.security.CurrentUser;
import com.omar.time.security.UserPrincipal;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	
	@GetMapping
	public UserPrincipal get(@CurrentUser UserPrincipal userPrincipal) {
		return userPrincipal;
	}
	
}
