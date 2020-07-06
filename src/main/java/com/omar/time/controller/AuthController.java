package com.omar.time.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omar.time.payload.LoginRequest;
import com.omar.time.payload.SignUpRequest;
import com.omar.time.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private AuthService authService;
    
    
    @Autowired
    public AuthController(AuthService authService) {
		this.authService = authService;
	}

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    	return this.authService.login(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    	return this.authService.signUp(signUpRequest);
    }

}