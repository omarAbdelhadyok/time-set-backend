package com.omar.time.service;

import java.util.Collections;

import javax.persistence.EntityNotFoundException;

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

	@Transactional
	public ResponseEntity<?> login(LoginRequestDTO loginRequestDTO) {
		Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequestDTO.getUsernameOrEmail(),
                loginRequestDTO.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthDto(jwt));
	}
	
	@Transactional
	public ResponseEntity<?> signUp(SignupRequestDTO signupRequestDTO) {
        if(userRepository.existsByUsername(signupRequestDTO.getUsername())) {
<<<<<<< HEAD
            throw new BadRequestException("errors.app.username.taken");
        }

        if(userRepository.existsByEmail(signupRequestDTO.getEmail())) {
            throw new BadRequestException("errors.app.email.taken");
=======
            throw new BadRequestException("Username is already taken!");
        }

        if(userRepository.existsByEmail(signupRequestDTO.getEmail())) {
            throw new BadRequestException("Email Address already taken!");
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
        }
        
        String password = signupRequestDTO.getPassword();

        // Creating user's account
        User user = new User(signupRequestDTO.getFirstName(), signupRequestDTO.getLastName(), signupRequestDTO.getUsername(), 
        		signupRequestDTO.getMobile(), signupRequestDTO.getEmail(), signupRequestDTO.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
<<<<<<< HEAD
            .orElseThrow(() -> new BadRequestException("errors.app.role.notSet"));
=======
            .orElseThrow(() -> new BadRequestException("User role not set"));
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);
        
        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);
        
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
<<<<<<< HEAD
        mailMessage.setSubject("Complete Registration");
=======
        mailMessage.setSubject("Complete Registration!");
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
        mailMessage.setFrom("Timeset");
        mailMessage.setText("To confirm your account, please click here : "
        +"http://localhost:8080/api/auth/confirm-account?token="+confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);
        
        LoginRequestDTO loginRequest = new LoginRequestDTO(result.getUsername(), password);

        return login(loginRequest);
    }

	@Transactional
    public boolean confirmUserAccount(String confirmationToken) {
<<<<<<< HEAD
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken)
        		.orElseThrow(() -> new RuntimeException("Something went wrong"));

        User user = userRepository.findByEmail(token.getUser().getEmail()).orElseThrow(() ->
        	new EntityNotFoundException("errors.app.user.notFound")
        );
        user.setActivatedMail(true);
        try {
        	userRepository.save(user);
            confirmationTokenRepository.deleteByTokenid(token.getTokenid());
		} catch (Exception e) {
			throw new RuntimeException("errors.app.server");
		}
        return true;
        
=======
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null) {
            User user = userRepository.findByEmail(token.getUser().getEmail()).orElseThrow(() ->
            	new EntityNotFoundException("User not found!")
            );
            user.setActivatedMail(true);
            try {
            	userRepository.save(user);
                confirmationTokenRepository.deleteByTokenid(token.getTokenid());
			} catch (Exception e) {
				throw new RuntimeException("Something went wrong");
			}
            return true;
        }
        throw new RuntimeException("Something went wrong");
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
    }
	
}
