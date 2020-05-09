package com.sbc.resource.hateoas;

import org.springframework.hateoas.ResourceSupport;

import com.sbc.projection.Doctor3;

public class Doctor3Resource extends ResourceSupport {

	public Doctor3 doctor;
	
	public Doctor3Resource(Doctor3 doctor) {
		this.doctor = doctor;
	}
	
}
