package com.sbc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbc.converter.AppointmentDetail2;
import com.sbc.entity.Appointment;
import com.sbc.exception.AppointmentNotFoundException;
import com.sbc.projection.Appointment1;
import com.sbc.projection.Appointment2;
import com.sbc.projection.Appointment3;
import com.sbc.resource.hateoas.Appointment1Resource;
import com.sbc.resource.hateoas.Appointment2Resource;
import com.sbc.resource.hateoas.Appointment3Resource;
import com.sbc.resource.hateoas.AppointmentResource;
import com.sbc.service.AppointmentService;

@RestController
@RequestMapping(path="/reactjs_restful")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
	
	private static final Logger LOG = LoggerFactory.getLogger(AppointmentController.class);
	
	/**
	 * save Appointment
	 * @param - appointment
	 * @authorized - patient 
	 */
	@PostMapping(path="/patient/save_appointment", consumes = "application/json", produces = "application/json")
	public ResponseEntity<AppointmentResource> save(@RequestBody Appointment appointment) {

		LOG.info("Logging message");
		LOG.info("appointment.getStarttime()=" + appointment.getStarttime());
		
		Appointment appt = appointmentService.save(appointment);
		
		/* create AppointmentResource instance */
		AppointmentResource resource = new AppointmentResource(appt);
		
		/* AppointmentResource instance has 2 parts - body and link. Initialize the instance body appointment */
		resource.appointment = appt;
		
		/* create selflink */
		Link selfLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(FeedbackController.class).findById(appointment.getId()))
				.withSelfRel();
		
		/* add selflink to the instance */
		resource.add(selfLink);
	
		return ResponseEntity.status((HttpStatus.OK)).body(resource);
		
	}
	
	
	
	/**
	 * get All Appointments  
	 * @param - null
	 * @return - List of Appointments
	 * @authorized - Admin 
	 */
	@GetMapping(path="/admin/get_all_appointments", produces = "application/json")
	public ResponseEntity<List<Appointment1Resource>> getAll() {
		
		List<Appointment1> appointmentList = appointmentService.getAll();
		
		/* create list of Appointment1Resource */
		List<Appointment1Resource> resourceList = new ArrayList<>();
		
		for(Appointment1 appointment: appointmentList) {
			
			/* create Appointment1Resource instance */
			Appointment1Resource resource1 = new Appointment1Resource(appointment);
			
			/* Appointment1Resource instance has 2 parts - body and link. Initialize the instance body appointment */
			resource1.appointment = appointment;
			
			/* create selflink */
			Link selfLink = ControllerLinkBuilder
					.linkTo(ControllerLinkBuilder.methodOn(FeedbackController.class).findById(appointment.getAppointment_id()))
					.withSelfRel();
			
			/* add selflink to the instance */
			resource1.add(selfLink);
			
			/* add instance to the Appointment1Resource list */
			resourceList.add(resource1);
		}
		
		return ResponseEntity.status((HttpStatus.OK)).body(resourceList);
	}
	
	
	
	/**
	 * get Appointment By Id 
	 * @param - id
	 * @return - appointment
	 * @authorized - User
	 */
	@GetMapping(path="/user/get_appointment/{id}", produces = "application/json")
	public ResponseEntity<AppointmentResource> findById(@PathVariable int id) {	
		
		Appointment appointment = appointmentService.getOnly(id);
		
		/* create AppointmentResource instance */
		AppointmentResource resource = new AppointmentResource(appointment);
		
		/* AppointmentResource instance has 2 parts - body and link. Initialize the instance body appointment */
		resource.appointment = appointment;
		
		/* create selflink */
		Link selfLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(FeedbackController.class).findById(appointment.getId()))
				.withSelfRel();
		
		/* add selflink to the instance */
		resource.add(selfLink);
	
		return ResponseEntity.status((HttpStatus.OK)).body(resource);
		
	}
	
	
	
	/**
	 * update Status of Appointment from BOOKED to RECEIVED
	 * @param appointmentId, Appointment
	 * @return Appointment 
	 * @authorized - Admin
	 */
	@PutMapping(path="/admin/update_status_by_appointmentId/{id}", produces = "application/json")
	public ResponseEntity<AppointmentResource> updateStatus(@PathVariable int id) {	
		
		Appointment app = appointmentService.updateStatus(id);
		
		/* create AppointmentResource instance */
		AppointmentResource resource = new AppointmentResource(app); 
		
		/* AppointmentResource instance has 2 parts - body and link. Initialize the instance body appointment */
		resource.appointment = app;
		
		/* create selflink */
		Link selfLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(FeedbackController.class).findById(app.getId()))
				.withSelfRel();
		
		/* add selflink to the instance */
		resource.add(selfLink);
	
		return ResponseEntity.status((HttpStatus.OK)).body(resource);
		
	}
	
	
	
	/**
	 * get All Appointments By DoctorId and After Selected DateTime 
	 * @throws ParseException 
	 * @throws Exception
	 * @param - int id, String Date
	 * @return - List of Appointments
	 * @authorized - Doctor 
	 */
	@GetMapping(path="/doctor_or_patient/get_appointments_by_doctorId_after_selected_datetime/{id}", produces = "application/json")
	public ResponseEntity<List<Appointment2Resource>> getAppointmentsByDoctorAfterSelectedDateTime(
					@PathVariable int id, 
					@RequestParam("stringDate") String stringDate) {
		
		// convert String date to Date object
		SimpleDateFormat sdf = new SimpleDateFormat("MM/DD/YYYY HH:MM");
		Date date;
		try {
			date = sdf.parse(stringDate);
		} catch (ParseException e) {
			throw new AppointmentNotFoundException("Date must be String value and format must be 'MM/DD/YYYY HH:MM' ");
		}
		
		List<Appointment2> appointmentList = appointmentService.getAppointmentsByDoctorAfterSelectedDateTime(id, date);
			
		/* create list of Appointment2Resource */
		List<Appointment2Resource> resourceList = new ArrayList<>();
		
		for(Appointment2 appointment: appointmentList) {
			
			/* create Appointment2Resource instance */
			Appointment2Resource resource = new Appointment2Resource(appointment);
			
			/* Appointment2Resource instance has 2 parts - body and link. Initialize the instance body appointment */
			resource.appointment = appointment;
			
			/* create selflink */
			Link selfLink = ControllerLinkBuilder
					.linkTo(ControllerLinkBuilder.methodOn(FeedbackController.class).findById(appointment.getId()))
					.withSelfRel();
			
			/* add selflink to the instance */
			resource.add(selfLink);
			
			/* add instance to the Appointment2Resource list */
			resourceList.add(resource);
		}
		
		return ResponseEntity.status((HttpStatus.OK)).body(resourceList);
	}
	

	/**
	 * get All Appointments By DoctorId on selected Date
	 * @throws ParseException 
	 * @throws Exception 
	 * @param - int id, String date
	 * @return - list of appoinments
	 * @authorized - Doctor
	 */
	@GetMapping(path="/doctor_or_patient/get_appointments_by_doctorId_on_selected_date/{id}", produces = "application/json")
	public ResponseEntity<List<Appointment2Resource>> getAppointmentsByDoctorOnSelectedDate(
					@PathVariable int id, 
					@RequestParam("stringDate") String stringDate) {
		
		LOG.info("search stringDate=" + stringDate);
		
		// check whether 'stringDate' format matches MySQL DATETIME format '2020-04-14 17:30:00'
		Date date;
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
		try {
			date = sdf.parse(stringDate);
		} catch (ParseException e) {
			throw new AppointmentNotFoundException("Date must be String value and format must be 'YYYY-MM-DD' ");
		}
		
		// get string date only '2020-04-14'. No time is required.
		stringDate = stringDate.substring(0, 10);		
		List<Appointment2> appointmentList = appointmentService.getAppointmentsByDoctorOnSelectedDate(id, stringDate);
		
		appointmentList.forEach(x -> LOG.info("appointment.getStarttime=" + x.getStarttime()));
		
		/* create list of Appointment2Resource */
		List<Appointment2Resource> resourceList = new ArrayList<>();
		
		for(Appointment2 appointment: appointmentList) {
			
			/* create Appointment2Resource instance */
			Appointment2Resource resource = new Appointment2Resource(appointment);
			
			/* Appointment2Resource instance has 2 parts - body and link. Initialize the instance body appointment */
			resource.appointment = appointment;
			
			/* create selflink */
			Link selfLink = ControllerLinkBuilder
					.linkTo(ControllerLinkBuilder.methodOn(FeedbackController.class).findById(appointment.getId()))
					.withSelfRel();
			
			/* add selflink to the instance */
			resource.add(selfLink);
			
			/* add instance to the Appointment2Resource list */
			resourceList.add(resource);
		}
		
		return ResponseEntity.status((HttpStatus.OK)).body(resourceList);
	}
	
	
	
	/**
	 * get All Appointments By Patient Id
	 * @throws ParseException 
	 * @throws Exception
	 * @param - id
	 * @return - List of Appointments
	 * @authroized - Admin, Patient
	 */
	@GetMapping(path="/doctor_or_patient/get_appointments_by_patientid/{id}", produces = "application/json")
	public ResponseEntity<List<Appointment3Resource>> getAppointmentsByPatientId(@PathVariable int id) {
		
		LOG.info("patient id=" + id);
		
		List<Appointment3> appointmentList = appointmentService.getAppointmentsByPatientId(id);
		
		appointmentList.forEach(x -> LOG.info("appointment.id=" + x.getAppid()));
		
		/* create list of Appointment3Resource */
		List<Appointment3Resource> resourceList = new ArrayList<>();
		
		for(Appointment3 appointment: appointmentList) {
			
			/* create Appointment3Resource instance */
			Appointment3Resource resource = new Appointment3Resource(appointment);
			
			/* Appointment3Resource instance has 2 parts - body and link. Initialize the instance body appointment */
			resource.appointment = appointment;
			
			/* create selflink */
			Link selfLink = ControllerLinkBuilder
					.linkTo(ControllerLinkBuilder.methodOn(FeedbackController.class).findById(appointment.getAppid()))
					.withSelfRel();
			
			/* add selflink to the instance */
			resource.add(selfLink);
			
			/* add instance to the Appointment3Resource list */
			resourceList.add(resource);
		}
		
		return ResponseEntity.status((HttpStatus.OK)).body(resourceList);
	}
	
	
	
	/**
	 * get All Appointments By Patient Id and Status
	 * @param patientId, status
	 * @return Appointment3 list
	 * @throws ParseException 
	 * @throws Exception 
	 * @authorized - Patient
	 */
	@GetMapping(path="/patient/get_appointments_by_patientid_and_status/{id}/status/{status}", produces = "application/json")
	public ResponseEntity<List<Appointment3Resource>> getAppointmentsByPatientIdAndStatus(@PathVariable int id, @PathVariable int status) {
		
		LOG.info("patient id=" + id);
		
		List<Appointment3> appointmentList = appointmentService.getAppointmentsByPatientIdAndStatus(id, status);
		
		appointmentList.forEach(x -> LOG.info("appointment.id=" + x.getAppid()));
		
		/* create list of Appointment3Resource */
		List<Appointment3Resource> resourceList = new ArrayList<>();
		
		for(Appointment3 appointment: appointmentList) {
			
			/* create Appointment3Resource instance */
			Appointment3Resource resource = new Appointment3Resource(appointment);
			
			/* Appointment3Resource instance has 2 parts - body and link. Initialize the instance body appointment */
			resource.appointment = appointment;
			
			/* create selflink */
			Link selfLink = ControllerLinkBuilder
					.linkTo(ControllerLinkBuilder.methodOn(FeedbackController.class).findById(appointment.getAppid()))
					.withSelfRel();
			
			/* add selflink to the instance */
			resource.add(selfLink);
			
			/* add instance to the Appointment3Resource list */
			resourceList.add(resource);
		}
		
		return ResponseEntity.status((HttpStatus.OK)).body(resourceList);
	}
	

	/**
	 * delete Appointment and AppointmentDetail using AppointmentId By Admin
	 * @param - appointmentID
	 * @return -
	 * @condition - none 
	 * @throws - Exception 
	 * @authorized - Admin
	 */
	@DeleteMapping(path="/admin/delete_appointment_and_appointmentdetail_by_appointmentId/{id}", produces = "application/json")
	public ResponseEntity<?> deleteAppointmentAndAppointmentDetailUsingAppointmentIdByAdmin(@PathVariable int id) {
		
		LOG.info("appointmentId=" + id);
		
		appointmentService.deleteAppointmentAndAppointmentDetailUsingAppointmentIdByAdmin(id);
		
		return ResponseEntity.status((HttpStatus.OK)).body(null);
	}
	
	
	
	/**
	 * delete Appointment and AppointmentDetail using AppointmentId By Patient
	 * @condition - Appointment starttime is more than 48 hours (172800 seconds) from cancellation time.
	 * @param - appointmentID
	 * @return - 
	 * @throws - Exception 
	 * @authorized - Admin
	 */
	@DeleteMapping(path="/patient/delete_appointment_and_appointmentdetail_by_appointmentId/{id}", produces = "application/json")
	public ResponseEntity<?> deleteAppointmentAndAppointmentDetailUsingAppointmentIdByPatient(@PathVariable int id) {
		
		LOG.info("appointmentId=" + id);
		
		appointmentService.deleteAppointmentAndAppointmentDetailUsingAppointmentIdByPatient(id);
		
		return ResponseEntity.status((HttpStatus.OK)).body(null);
	}
	
	
}
