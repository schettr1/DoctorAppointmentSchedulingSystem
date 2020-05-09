package com.sbc.resource.hateoas;

import org.springframework.hateoas.ResourceSupport;

import com.sbc.projection.Doctor1;

public class Doctor1Resource extends ResourceSupport {

	public Doctor1 doctor;
	
	public Doctor1Resource(Doctor1 doctor) {
		this.doctor = doctor;
	}
	
}
