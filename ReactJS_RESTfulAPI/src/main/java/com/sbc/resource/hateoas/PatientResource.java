package com.sbc.resource.hateoas;

import org.springframework.hateoas.ResourceSupport;
import com.sbc.entity.Patient;


public class PatientResource extends ResourceSupport {

	public Patient patient;
	
	public PatientResource(Patient patient) {
		this.patient = patient;
	}
	
}
