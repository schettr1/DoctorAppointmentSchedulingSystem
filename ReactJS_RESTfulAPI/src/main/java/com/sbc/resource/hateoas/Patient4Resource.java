package com.sbc.resource.hateoas;

import org.springframework.hateoas.ResourceSupport;

import com.sbc.projection.Patient4;

public class Patient4Resource extends ResourceSupport {

	public Patient4 patient;
	
	public Patient4Resource(Patient4 patient) {
		this.patient = patient;
	}
	
}
