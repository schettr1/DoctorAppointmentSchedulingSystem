package com.sbc.resource.hateoas;

import org.springframework.hateoas.ResourceSupport;

import com.sbc.converter.User1;

public class User1Resource extends ResourceSupport {

	public User1 user;
	
	public User1Resource(User1 user) {
		this.user = user;
	}
	
}