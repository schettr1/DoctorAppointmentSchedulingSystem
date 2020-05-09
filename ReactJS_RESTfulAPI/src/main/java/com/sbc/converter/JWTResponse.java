package com.sbc.converter;

import java.io.Serializable;

public class JWTResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String access_token;
	private final String refresh_token;
	private final int userId;
	private final String role;
	
	// constructor + getter method
	
	public JWTResponse(String access_token, String refresh_token, int userId, String role) {
		this.access_token = access_token;
		this.refresh_token = refresh_token;
		this.userId = userId;
		this.role = role;
	}

	public String getAccess_token() {
		return access_token;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public int getUserId() {
		return userId;
	}

	public String getRole() {
		return role;
	}
	
	
}

