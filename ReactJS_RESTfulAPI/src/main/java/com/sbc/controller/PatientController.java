package com.sbc.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbc.converter.User1;
import com.sbc.entity.Patient;
import com.sbc.projection.Patient1;
import com.sbc.projection.Patient4;
import com.sbc.resource.hateoas.Patient1Resource;
import com.sbc.resource.hateoas.Patient4Resource;
import com.sbc.resource.hateoas.PatientResource;
import com.sbc.resource.hateoas.User1Resource;
import com.sbc.service.PatientService;

@RestController
@RequestMapping(path="/reactjs_restful")
public class PatientController {

	@Autowired
	private PatientService patientService;
	
	private static final Logger LOG = LoggerFactory.getLogger(PatientController.class);
	
	
	/**
	 * save Patient 
	 * @param - User1
	 * @return - User1
	 * @authorized - none
	 */
	@PostMapping(path="/save_patient", consumes = "application/json", produces = "application/json")
	public ResponseEntity<PatientResource> savePatient(@RequestBody User1 user1) {

		LOG.info("patient.toString()=" + user1.getFirstname() + user1.getLastname() + user1.getEmail() + user1.getPhone() + user1.getBirthdate() 
			+ user1.getStreet() + user1.getCity() + user1.getState() + user1.getZipcode() + user1.getGender() + user1.getUsername() + user1.getPassword());
		
		Patient _patient = patientService.savePatient(user1);

		/* create PatientResource instance */
		PatientResource resource = new PatientResource(_patient);
		
		/* PatientResource instance has 2 parts - body and link. Initialize the instance body doctor */
		resource.patient = _patient;
		
		/* create selflink */
		Link selfLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(PatientController.class).findById(_patient.getId()))
				.withSelfRel();
		
		/* add selflink to the instance */
		resource.add(selfLink);
		
		return ResponseEntity.status((HttpStatus.CREATED)).body(resource);
	}
	
	
	/**
	 * get Patient By Id (retrieve patient data for updating purpose)
	 * @param patient Id (int)
	 * @return Patient4
	 * @authorized - Admin, Doctor, Patient
	 */
	@GetMapping(path="/user/get_patient_by_Id/{id}", produces = "application/json")
	public ResponseEntity<Patient4Resource> getPatientById(@PathVariable int id) {	
		
		Patient4 _patient = patientService.getPatientById(id);
		
		/* create Patient4Resource instance */
		Patient4Resource resource = new Patient4Resource(_patient);
		
		/* Patient4Resource instance has 2 parts - body and link. Initialize the instance body patient */
		resource.patient = _patient;
		
		/* create selflink */
		Link selfLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(PatientController.class).findById(_patient.getId()))
				.withSelfRel();
		
		/* add selflink to the instance */
		resource.add(selfLink);
	
	return ResponseEntity.status((HttpStatus.OK)).body(resource);
	}
	
	

	/**
	 * update Patient   
	 * @param User1
	 * @return User1
	 * @authorized - Admin, Patient
	 */
	@PutMapping(path="/admin_or_patient/update_patient/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<User1Resource> update(@RequestBody User1 user1, @PathVariable int id) {
		
		User1 _user1 = patientService.updatePatient(user1, id);

		/* create User1Resource instance */
		User1Resource resource = new User1Resource(_user1);
		
		/* User1Resource instance has 2 parts - body and link. Initialize the instance body user */
		resource.user = _user1;
		
		/* create selflink */
		Link selfLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(PatientController.class).findById(_user1.getId()))
				.withSelfRel();
		
		/* add selflink to the instance */
		resource.add(selfLink);
		
		return ResponseEntity.status((HttpStatus.OK)).body(resource);
	}
	
	
	/**
	 * get All Patients
	 * @param 
	 * @return list of patients
	 * @throws Exception
	 * @authorized - Admin, Doctor 
	 */
	@GetMapping(path="/admin_or_doctor/get_all_patients", produces = "application/json")
	public ResponseEntity<List<Patient1Resource>> getAllPatients() {
	
		List<Patient1> patient1List = patientService.getAllPatients();
		
		/* create list of Patient1Resource */
		List<Patient1Resource> resourceList = new ArrayList<>();
		
		for(Patient1 _patient: patient1List) {
			
			/* create Patient1Resource instance */
			Patient1Resource resource = new Patient1Resource(_patient);
			
			/* Patient1Resource instance has 2 parts - body and link. Initialize the instance body patient */
			resource.patient = _patient;
			
			/* create selflink */
			Link selfLink = ControllerLinkBuilder
					.linkTo(ControllerLinkBuilder.methodOn(PatientController.class).findById(_patient.getId()))
					.withSelfRel();
			
			/* add selflink to the instance */
			resource.add(selfLink);
			
			/* add instance to the Patient1Resource list */
			resourceList.add(resource);
		}
		
		return ResponseEntity.status((HttpStatus.OK)).body(resourceList);
	}
	
	
	
	/**
	 * get Patient By Id 
	 * (used for HATEOAS purpose only)
	 * @param id 
	 * @return Patient
	 */
	public ResponseEntity<PatientResource> findById(@PathVariable int id) {	
		
		Patient _patient = patientService.getOnly(id);
		
		/* create PatientResource instance */
		PatientResource resource = new PatientResource(_patient);
		
		/* PatientResource instance has 2 parts - body and link. Initialize the instance body doctor */
		resource.patient = _patient;
		
		/* create selflink */
		Link selfLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(PatientController.class).findById(_patient.getId()))
				.withSelfRel();
		
		/* add selflink to the instance */
		resource.add(selfLink);
	
	return ResponseEntity.status((HttpStatus.OK)).body(resource);
	}
	
	
	
}
