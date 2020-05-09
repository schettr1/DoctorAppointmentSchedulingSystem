package com.sbc.resource.hateoas;

import org.springframework.hateoas.ResourceSupport;

import com.sbc.projection.Appointment3;

public class Appointment3Resource extends ResourceSupport {

	public Appointment3 appointment;
	
	public Appointment3Resource(Appointment3 appointment) {
		this.appointment = appointment;
	}
	
}