package com.omar.time.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.omar.time.dto.user.UserDTO;
import com.omar.time.exception.BadRequestException;
import com.omar.time.model.ConfirmationToken;
import com.omar.time.model.User;
import com.omar.time.repository.ConfirmationTokenRepository;
import com.omar.time.repository.UserRepository;
import com.omar.time.security.UserPrincipal;
import com.omar.time.util.ObjectMapperUtils;

@Service
public class UserService {

	private UserRepository userRepository;
	private ConfirmationTokenRepository confirmationTokenRepository;
	private EmailSenderService emailSenderService;
	
	@Autowired
	public UserService(UserRepository userRepository,
			ConfirmationTokenRepository confirmationTokenRepository,
			EmailSenderService emailSenderService) {
		this.confirmationTokenRepository = confirmationTokenRepository;
		this.emailSenderService = emailSenderService;
		this.userRepository = userRepository;
	}
	
	public UserDTO get(UserPrincipal userPrincipal) {
		User user = userRepository.findById(userPrincipal.getId())
<<<<<<< HEAD
				.orElseThrow(() -> new RuntimeException("errors.app.server"));
=======
				.orElseThrow(() -> new RuntimeException("Something went wrong"));
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
		
		return ObjectMapperUtils.map(user, UserDTO.class);
	}
	
	public List<UserDTO> findUser(String userNameOrEmail) {
		List<User> result = userRepository.findByUsernameOrEmailContaining(userNameOrEmail, userNameOrEmail);
		
		if(result.isEmpty()) {
<<<<<<< HEAD
			throw new EntityNotFoundException("errors.app.user.notFound");
=======
			throw new EntityNotFoundException("User Not Found");
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
		}
		
		return ObjectMapperUtils.mapAll(result, UserDTO.class);
	}
	
	public boolean resendConfirmation(UserPrincipal userPrincipal) {
		
		User user = ObjectMapperUtils.map(userPrincipal, User.class);
		
		if(user.isActivatedMail() == true) {
<<<<<<< HEAD
			throw new BadRequestException("errors.app.email.alreadyVerified");
=======
			throw new BadRequestException("Email already verified!");
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
		}
		
		ConfirmationToken confirmationToken = null;
		
		Optional<ConfirmationToken> result = confirmationTokenRepository.findByUser(user);
		
		if(result.isEmpty()) {
			confirmationToken = new ConfirmationToken(user);
		} else {
			confirmationToken = result.get();
		}
		
		confirmationTokenRepository.save(confirmationToken);
        
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("Timeset");
        mailMessage.setText("To confirm your account, please click here : "
        +"http://localhost:8080/api/auth/confirm-account?token="+confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);
		
		return true;
	}
	
}
