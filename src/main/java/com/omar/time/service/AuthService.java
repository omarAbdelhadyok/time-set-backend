package com.omar.time.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omar.time.dto.auth.LoginRequestDTO;
import com.omar.time.dto.auth.SignupRequestDTO;
import com.omar.time.dto.jwt.JwtAuthDto;
import com.omar.time.exception.BadRequestException;
import com.omar.time.model.ConfirmationToken;
import com.omar.time.model.Role;
import com.omar.time.model.User;
import com.omar.time.model.enums.RoleName;
import com.omar.time.repository.ConfirmationTokenRepository;
import com.omar.time.repository.RoleRepository;
import com.omar.time.repository.UserRepository;
import com.omar.time.security.JwtTokenProvider;

@Service
public class AuthService {
	
	private AuthenticationManager authenticationManager;
	private JwtTokenProvider tokenProvider;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private ConfirmationTokenRepository confirmationTokenRepository;
    private EmailSenderService emailSenderService;
	
	@Autowired
	public AuthService(AuthenticationManager authenticationManager,
			JwtTokenProvider tokenProvider,
			UserRepository userRepository,
			RoleRepository roleRepository,
			PasswordEncoder passwordEncoder,
			ConfirmationTokenRepository confirmationTokenRepository,
			EmailSenderService emailSenderService) {
		this.authenticationManager = authenticationManager;
		this.tokenProvider = tokenProvider;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.confirmationTokenRepository = confirmationTokenRepository;
		this.emailSenderService = emailSenderService;
	}
	

	public ResponseEntity<?> login(LoginRequestDTO loginRequestDTO) {
		//new user name password authentication object
		Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequestDTO.getUsernameOrEmail(),
                loginRequestDTO.getPassword()
            )
        );

		//set authentication on security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //generate JWT token from the token provider
        String jwt = tokenProvider.generateToken(authentication);
        
        return ResponseEntity.ok(new JwtAuthDto(jwt));
	}
	
	@Transactional
	public ResponseEntity<?> signUp(SignupRequestDTO signupRequestDTO) {
		//make sure user name and email are unique
        if(userRepository.existsByUsername(signupRequestDTO.getUsername())) {
            throw new BadRequestException("errors.app.username.taken");
        }
        if(userRepository.existsByEmail(signupRequestDTO.getEmail())) {
            throw new BadRequestException("errors.app.email.taken");
        }
        
        //saving not encrypted password to be used with signing in
        String password = signupRequestDTO.getPassword();
        
        // Creating user's account
        User user = new User(signupRequestDTO.getFirstName(), signupRequestDTO.getLastName(), signupRequestDTO.getUsername(), 
        		signupRequestDTO.getMobile(), signupRequestDTO.getEmail(), signupRequestDTO.getPassword());
        
        //encrypting password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //check if role name exists
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
            .orElseThrow(() -> new BadRequestException("errors.app.role.notSet"));

        user.setRoles(Collections.singleton(userRole));
        User result = userRepository.save(user);

        //generating, saving and sending confirmation token to user email
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration");
        mailMessage.setFrom("Timeset");
        mailMessage.setText("To confirm your account, please click here : "
        +"http://localhost:8080/api/auth/confirm-account?token="+confirmationToken.getConfirmationToken());
        emailSenderService.sendEmail(mailMessage);
        
        //creating a login request object to auto login user
        LoginRequestDTO loginRequest = new LoginRequestDTO(result.getUsername(), password);

        return login(loginRequest);
    }
	
}
