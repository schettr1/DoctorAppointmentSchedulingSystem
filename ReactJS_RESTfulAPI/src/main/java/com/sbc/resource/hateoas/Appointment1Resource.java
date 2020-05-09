package com.sbc.resource.hateoas;

import org.springframework.hateoas.ResourceSupport;

import com.sbc.projection.Appointment1;

public class Appointment1Resource extends ResourceSupport {

	public Appointment1 appointment;
	
	public Appointment1Resource(Appointment1 appointment) {
		this.appointment = appointment;
	}
	
}
