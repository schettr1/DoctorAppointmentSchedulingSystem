package com.sbc.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sbc.controller.UserController;
import com.sbc.jwt.JWTUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

	private static final Logger LOG = LoggerFactory.getLogger(JWTRequestFilter.class); 
	
	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * 	Two Steps Process -
	 *  1. Check whether the request has Authorization Header. 
	 *     JWT Token must be in the form "Bearer token". Extract username/subject from the token.
	 *  2. Check Token validity.
	 */
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		LOG.info("inside doFilterInternal() method");
		
		final String requestTokenHeader = request.getHeader("Authorization");
		LOG.info("requestTokenHeader = " + requestTokenHeader);
		
		String username = null;
		String jwtToken = null;

		// skip this code if there is no Bearer Token in the Authorization Header
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtUtil.getUsernameFromToken(jwtToken);	// call method
				LOG.info("username=" + username);
			} catch (IllegalArgumentException e) {
				LOG.info("\nUnable to get JWT Token");
			} catch (ExpiredJwtException e) {
				LOG.info("\nJWT Token has expired");
			}
		} 

		// Skip this code if username is null
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			// check whether token is valid (username exists && token is not expired)
			if (jwtUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// validate the Token by setting the token in SecurityContextHolder
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}



}