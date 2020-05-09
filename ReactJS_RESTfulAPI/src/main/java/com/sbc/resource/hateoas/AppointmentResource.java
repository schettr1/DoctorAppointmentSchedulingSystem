package com.sbc.resource.hateoas;

import org.springframework.hateoas.ResourceSupport;

import com.sbc.entity.Appointment;

public class AppointmentResource extends ResourceSupport {

	public Appointment appointment;
	
	public AppointmentResource(Appointment appointment) {
		this.appointment = appointment;
	}
	
}
