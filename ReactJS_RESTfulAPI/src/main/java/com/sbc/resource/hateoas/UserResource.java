package com.sbc.resource.hateoas;

import org.springframework.hateoas.ResourceSupport;

import com.sbc.entity.User;

public class UserResource extends ResourceSupport {

	public User user;
	
	public UserResource(User user) {
		this.user = user;
	}
	
}