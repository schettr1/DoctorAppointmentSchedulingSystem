package com.sbc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbc.converter.AppointmentDetail2;
import com.sbc.entity.AppointmentDetail;
import com.sbc.projection.AppointmentDetail1;
import com.sbc.resource.hateoas.AppointmentDetail1Resource;
import com.sbc.resource.hateoas.AppointmentDetailResource;
import com.sbc.service.AppointmentDetailService;

@RestController
@RequestMapping(path="/reactjs_restful")
public class AppointmentDetailController {
	
	@Autowired
	private AppointmentDetailService appointmentDetailService;
	
	private static final Logger LOG = LoggerFactory.getLogger(AppointmentDetailController.class);
	
	
	/**
	 * update AppointmentDetail and Appointment Status By appointmentDetail Id
	 * @param AppointmentDetail, id
	 * @return AppointmentDetail
	 * @authorized - Doctor
	 */
	@PostMapping(path="/doctor/update_appointmentdetail", consumes = "application/json", produces = "application/json")
	public ResponseEntity<AppointmentDetailResource> updateAppointmentdetail(@RequestBody AppointmentDetail2 appointmentDetail) {
		
		LOG.info("AppointmentDetail = " + appointmentDetail.toString());
		
		AppointmentDetail adetail = appointmentDetailService.updateAppointmentdetail(appointmentDetail);

		/* create AppointmentDetailResource instance */
		AppointmentDetailResource resource = new AppointmentDetailResource(adetail);
		
		/* AppointmentDetailResource instance has 2 parts - body and link. Initialize the instance body appointmentDetail */
		resource.appointmentDetail = adetail;
		
		/* create selflink */
		Link selfLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(AppointmentDetailController.class).findById(adetail.getId()))
				.withSelfRel();
		
		/* add selflink to the instance */
		resource.add(selfLink);
		
		return ResponseEntity.status((HttpStatus.CREATED)).body(resource);
	}
	
	
	/**
	 * get AppointmentDetail By appointmentId 
	 * @param appointmentId
	 * @return AppointmentDetail1
	 * @authorized - Doctor
	 */
	@GetMapping(path="/doctor_or_patient/get_appointmentdetail_by_appointmentId/{id}", produces = "application/json")
	public ResponseEntity<AppointmentDetail1Resource> findById(@PathVariable int id) {	
		
		LOG.info("appointmentId=" + id);
		
		AppointmentDetail1 appointmentDetail = appointmentDetailService.getAppointmentDetailByAppointmentId(id);
		
		/* create AppointmentDetail1Resource instance */
		AppointmentDetail1Resource resource = new AppointmentDetail1Resource(appointmentDetail);
		
		/* appointmentDetail1 instance has 2 parts - body and link. Initialize the instance body appointmentDetail */
		resource.appointmentDetail = appointmentDetail;
		
		/* create selflink */
		Link selfLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(AppointmentDetailController.class).findById(appointmentDetail.getAppointment_id()))
				.withSelfRel();
		
		/* add selflink to the instance */
		resource.add(selfLink);
	
	return ResponseEntity.status((HttpStatus.OK)).body(resource);
	}
	
	
}