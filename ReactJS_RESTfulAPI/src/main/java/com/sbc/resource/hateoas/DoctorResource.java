package com.sbc.resource.hateoas;

import org.springframework.hateoas.ResourceSupport;

import com.sbc.entity.Doctor;

public class DoctorResource extends ResourceSupport {

	public Doctor doctor;
	
	public DoctorResource(Doctor doctor) {
		this.doctor = doctor;
	}
	
}
