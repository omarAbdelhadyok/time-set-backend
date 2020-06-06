package com.omar.time.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omar.time.dto.UserDTO;
import com.omar.time.model.User;
import com.omar.time.repository.UserRepository;
import com.omar.time.util.ObjectMapperUtils;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	public List<UserDTO> findUser(String userNameOrEmail) {
		List<User> result = userRepository.findByUsernameOrEmailContaining(userNameOrEmail, userNameOrEmail);
		
		if(result.isEmpty()) {
			throw new RuntimeException("User was not found");
		}
		
		return ObjectMapperUtils.mapAll(result, UserDTO.class);
	}
	
}
