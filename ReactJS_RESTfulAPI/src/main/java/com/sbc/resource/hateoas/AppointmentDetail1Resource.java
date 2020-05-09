package com.sbc.resource.hateoas;

import org.springframework.hateoas.ResourceSupport;

import com.sbc.projection.AppointmentDetail1;

public class AppointmentDetail1Resource extends ResourceSupport {

	public AppointmentDetail1 appointmentDetail;
	
	public AppointmentDetail1Resource(AppointmentDetail1 appointmentDetail) {
		this.appointmentDetail = appointmentDetail;
	}
	
}