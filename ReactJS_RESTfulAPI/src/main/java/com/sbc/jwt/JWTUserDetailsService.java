package com.sbc.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbc.converter.UserCredentials;
import com.sbc.entity.User;
import com.sbc.repository.UserRepository;

@Service
@Transactional
public class JWTUserDetailsService implements UserDetailsService {

	private static final Logger LOG = LoggerFactory.getLogger(JWTUserDetailsService.class); 
	
	@Autowired
	private UserRepository userRepository;  

	/* FIND USER USING USERNAME AND CONVERT IT TO USERCREDENTIALS OBJECT  */
	public UserCredentials findUserByUsername(String username) {
		User foundUser = userRepository.findByUsername(username);

		// convert User to UserCredentials
		UserCredentials userCredentials = new UserCredentials();
		if (foundUser != null) {
			userCredentials.setUsername(foundUser.getUsername());		
			userCredentials.setRole(foundUser.getAuthority().getRole().name());	// Enum = ROLE_DOCTOR(1), where name = ROLE_DOCTOR and value = 1
		}
		return userCredentials;
	}

	/* CREATE USERDETAILS FROM THE FOUND USER */
	@Override
	public UserDetails loadUserByUsername(String username) {
		LOG.info("inside loadUserByUsername()");
		
		/* search User by username */
		User foundUser = userRepository.findByUsername(username);

		/*
		 * convert User to UserDetails(which is understood by Spring Security) using
		 * UserBuilder
		 */
		UserBuilder userBuilder = null;
		if (foundUser != null) {
			userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
			userBuilder.disabled(!foundUser.isEnabled());
			userBuilder.password(foundUser.getPassword());
			userBuilder.authorities(foundUser.getAuthority().getRole().name());
		} else {
			throw new UsernameNotFoundException(username + " is invalid username.");
		}

		return userBuilder.build();
	}

	
	/**
	 * convert RoleEnum to String name
	 * @param 1
	 * @return "ROLE_DOCTOR"
	 */
	public String convertEnumToString(int num) {
		String role = "";
		switch(num) {
		case 1: 
			role = "ROLE_DOCTOR";
			break;
		case 2: 
			role =  "ROLE_PATIENT";
			break;
		case 3: 
			role =  "ROLE_ADMIN";
			break;			
		}
		return role;
	}
}
