package com.sbc.resource.hateoas;

import org.springframework.hateoas.ResourceSupport;

import com.sbc.projection.Patient1;

public class Patient1Resource extends ResourceSupport {

	public Patient1 patient;
	
	public Patient1Resource(Patient1 patient) {
		this.patient = patient;
	}
	
}