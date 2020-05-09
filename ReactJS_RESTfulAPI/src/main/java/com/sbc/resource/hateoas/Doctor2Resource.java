package com.sbc.resource.hateoas;

import org.springframework.hateoas.ResourceSupport;

import com.sbc.projection.Doctor2;

public class Doctor2Resource extends ResourceSupport {

	public Doctor2 doctor;
	
	public Doctor2Resource(Doctor2 doctor) {
		this.doctor = doctor;
	}
	
}
