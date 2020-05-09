package com.sbc.resource.hateoas;

import org.springframework.hateoas.ResourceSupport;

import com.sbc.entity.AppointmentDetail;

public class AppointmentDetailResource extends ResourceSupport {

	public AppointmentDetail appointmentDetail;
	
	public AppointmentDetailResource(AppointmentDetail appointmentDetail) {
		this.appointmentDetail = appointmentDetail;
	}
	
}