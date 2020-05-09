package com.sbc.resource.hateoas;

import org.springframework.hateoas.ResourceSupport;

import com.sbc.projection.Appointment2;

public class Appointment2Resource extends ResourceSupport {

	public Appointment2 appointment;
	
	public Appointment2Resource(Appointment2 appointment) {
		this.appointment = appointment;
	}
	
}
