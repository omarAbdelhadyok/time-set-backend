package com.omar.time.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
				.orElseThrow(() -> new RuntimeException("errors.app.server"));
		
		return ObjectMapperUtils.map(user, UserDTO.class);
	}
	
	public List<UserDTO> findUser(String userNameOrEmail) {
		List<User> result = userRepository.findByUsernameOrEmailContaining(userNameOrEmail, userNameOrEmail);
		
		if(result.isEmpty()) {
			throw new EntityNotFoundException("errors.app.user.notFound");
		}
		
		return ObjectMapperUtils.mapAll(result, UserDTO.class);
	}
	
	@Transactional
	public boolean resendConfirmation(UserPrincipal userPrincipal) {
		
		User user = ObjectMapperUtils.map(userPrincipal, User.class);
		
		if(user.isActivatedMail() == true) {
			throw new BadRequestException("errors.app.email.alreadyVerified");
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
	
	@Transactional
    public boolean confirmUserAccount(String confirmationToken) {
		//check if the confirmation token and user exist
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken).orElseThrow(
    		() -> new RuntimeException("Something went wrong")
		);
        User user = userRepository.findByEmail(token.getUser().getEmail()).orElseThrow(
    		() -> new EntityNotFoundException("errors.app.user.notFound")
        );
        
        //set user activatedMail to true
        user.setActivatedMail(true);
        //save user and delete confirmation token
    	userRepository.save(user);
        confirmationTokenRepository.deleteByTokenid(token.getTokenid());
		
        return true;
    }
	
}
