package com.sbc.resource.hateoas;

import org.springframework.hateoas.ResourceSupport;

import com.sbc.projection.Doctor4;

public class Doctor4Resource extends ResourceSupport {

	public Doctor4 doctor;
	
	public Doctor4Resource(Doctor4 doctor) {
		this.doctor = doctor;
	}
	
}
