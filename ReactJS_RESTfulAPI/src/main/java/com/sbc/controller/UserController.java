package com.sbc.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbc.converter.JWTResponse;
import com.sbc.converter.UserCredentials;
import com.sbc.entity.User;
import com.sbc.exception.InvalidTokenException;
import com.sbc.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;

import com.sbc.jwt.JWTUtil;
import com.sbc.resource.hateoas.UserResource;

@RestController
@RequestMapping(path="/reactjs_restful")  // not having @RequestMapping in controller gives "IllegalArgumentException: uriTemplate must not be null"
public class UserController {
	
	/**
	 * Any request to the REST api will be intercepted by the JWTRequestFilter.
	 * After access_token is validated, the request reaches the resource location uri.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class); 
	
	@Autowired
	private UserService userService;  
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTUtil jWTUtil; 

	@Autowired
	private UserDetailsService userDetailsService;
	
	
	// ================================= AUTHENTICATION + AUTHORIZATION ================================== //
		/**
		 * AUTHENTICATION - authenticates user credentials username and password
		 * AUTHORIZATION - states what each user has access to or role for each user  
		 * @param HttpServletRequest header contains { "Authorization": { "Basic": Base64Encode(username + ":" + password)}}
		 * @return JWTResponse(access_token, refresh_token, userId, role)
		 * @authorized - none
		 */
		@PostMapping(path = "/authorize", produces = "application/json")
		public ResponseEntity<JWTResponse> authorizationServer(HttpServletRequest request) throws Exception {
			
			LOG.info("request=" + request);
			
			String username="";
			String password="";
			
			// Get Username and Password from Authorization-Header
			String authorizationHeader = request.getHeader("Authorization");
			
			if (authorizationHeader != null && authorizationHeader.toLowerCase().startsWith("basic")) {
			    String base64Credentials = authorizationHeader.substring("Basic".length()).trim();
			    byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			    String credentials = new String(credDecoded, StandardCharsets.UTF_8);
			    final String[] values = credentials.split(":", 2);
			    username = values[0];
			    LOG.info("username=" + username);
			    password = values[1];
			    LOG.info("password=" + password);
			} 
			
			// call method to authenticate user credentials
			authenticateUserCredentials(username, password);		

			// find UserDetails
			final UserDetails userDetails = userDetailsService.loadUserByUsername(username);		
			LOG.info("userDetails=" + userDetails.getUsername() + ", " + userDetails.getPassword());	
			 
			// call method to generate access_token 
			final String access_token = jWTUtil.generateAccessToken(userDetails);		
			LOG.info("access_token=" + access_token);	
			
			// call method to generate refresh_token
			final String refresh_token = jWTUtil.generateRefreshToken(userDetails);	
			LOG.info("refresh_token=" + refresh_token);
			
			// find UserCredentials
			UserCredentials userCredentials = userService.findUserCredentials(username);
			LOG.info("foundUser=" + userCredentials.getUserId() + ", " + userCredentials.getUsername() + ", " + userCredentials.getPassword() + ", " + userCredentials.getRole());
			
			JWTResponse jwtResponse = new JWTResponse(access_token, refresh_token, userCredentials.getUserId(), userCredentials.getRole());
			
			return ResponseEntity.ok(jwtResponse);
		}
		
		
		private void authenticateUserCredentials(String username, String password) throws Exception {
			try {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			} catch (DisabledException ex) {
				throw new Exception("USER_DISABLED", ex);
			} catch (BadCredentialsException ex) {
				throw new Exception("INVALID_CREDENTIALS", ex);
			}
		}
		
		
		
		// ======================================= RENEW ACCESS-TOKEN USING REFRESH-TOKEN ======================================== //
		/**
		 * renew access_token using refresh_token
		 * only access_token is renewed, refresh_token remains the same as before
		 * FLAW : you send access_token to renew access_token without error. That's a glitch!
		 * @param HttpServletRequest header contains { "Authorization": { "Bearer ": refresh_token}}
		 * @return jwtResponse(access_token, refresh_token, userId, role)
		 * @authorized - none
		 */
		@PostMapping(path = "/renew_access_token", produces = "application/json")
		public ResponseEntity<JWTResponse> renew_accessToken(HttpServletRequest request) {
			
			LOG.info("Inside renew_accessToken()");
			LOG.info("request=" + request);
			
			String username = null;
			String refresh_token = null;
			
			// get Authorization-Header from the request
			final String authorizationHeader = request.getHeader("Authorization");
			
			// check whether Authorization-Header contains Bearer Token
			if (authorizationHeader != null && authorizationHeader.toLowerCase().startsWith("bearer ")) {
				refresh_token = authorizationHeader.substring(7); 
				LOG.info("refresh_token=" + refresh_token);
				try {
					username = jWTUtil.getUsernameFromToken(refresh_token);	// call method
				} catch (IllegalArgumentException e) {
					LOG.info("\nUnable to get JWT Token");
				} catch (ExpiredJwtException e) {
					LOG.info("\nJWT Token has expired");
				}
			} 
			else {
				throw new InvalidTokenException("Authorization-Header does not begin with string Bearer");
			}
			
			// find UserDetails
			final UserDetails userDetails = userDetailsService.loadUserByUsername(username);		
			
			// check whether refresh_token is expired
			jWTUtil.getExpirationDateFromToken(refresh_token);
			
			// renew access_token using userDetails
			final String access_token = jWTUtil.generateAccessToken(userDetails);			
			
			// find UserCredentials
			UserCredentials userCredentials = userService.findUserCredentials(username);	
			
			// create JWTResponse 
			JWTResponse jwtResponse = new JWTResponse(access_token, refresh_token, userCredentials.getUserId(), userCredentials.getRole());
		 
			return ResponseEntity.ok(jwtResponse);  		// if you put headers than JWTResponse must go inside .body() and not inside .ok()
		}
		
		
		
		// ================================= FIND USER BY ID ================================== //
		/**
		 * Find User By Id (for HOME page)
		 * @param userId
		 * @return User
		 * @authorized - Admin, Doctor, Patient
		 */
		@GetMapping(path = "/user/get_user_by_id/{id}")
		public ResponseEntity<UserResource> getUserById(@PathVariable int id) {
			
			LOG.info("getUserById()");
			
			User user = userService.findUserById(id);
			
			/* create UserResource instance */
			UserResource resource = new UserResource(user);
			
			/* UserResource instance has 2 parts - body and link. Initialize the instance body user */
			resource.user = user;
			
			/* create selflink */
			Link selfLink = ControllerLinkBuilder
					.linkTo(ControllerLinkBuilder.methodOn(UserController.class).getUserById(user.getId()))
					.withSelfRel();
			
			/* add selflink to the instance */
			resource.add(selfLink);
			
			return ResponseEntity.ok(resource);
		}
}
