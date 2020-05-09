package com.sbc.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbc.converter.UserCredentials;
import com.sbc.entity.User;
import com.sbc.exception.DoctorNotFoundException;
import com.sbc.repository.UserRepository;

@Service
@Transactional
public class UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class); 
	
	@Autowired
	UserRepository userRepository; 
	

	//********************************** FIND USER CREDENTIALS *******************************************//
	public UserCredentials findUserCredentials(String username) {
		LOG.info("enter findUserCredentials() method.");
		User foundUser = userRepository.findByUsername(username);  
		LOG.info("calling findByUsername() method.");
		LOG.info("foundUser = " + foundUser.getUsername() + ", " + foundUser.getPassword());
		UserCredentials userCredentials = new UserCredentials(); 
		if (foundUser != null) {
			userCredentials.setUserId(foundUser.getId());
			userCredentials.setUsername(foundUser.getUsername());
			userCredentials.setPassword(foundUser.getPassword());
			userCredentials.setRole(foundUser.getAuthority().getRole().name());		// convert Enum=ROLE_DOCTOR(1) to String="ROLE_DOCTOR" using name()
		}
		return userCredentials;
	}
	
	
	//********************************** FIND USER BY ID *******************************************//
	public User findUserById(int id) {
		LOG.info("enter findUserById() method.");
		return userRepository.findById(id)
				.orElseThrow(
						() -> new DoctorNotFoundException("id=" + id + " cannot be found."));	
	}
	
}
