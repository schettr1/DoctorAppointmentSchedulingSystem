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
import com.sbc.entity.Doctor;
import com.sbc.entity.User;
import com.sbc.projection.Doctor1;
import com.sbc.projection.Doctor2;
import com.sbc.projection.Doctor3;
import com.sbc.projection.Doctor4;
import com.sbc.resource.hateoas.Doctor1Resource;
import com.sbc.resource.hateoas.Doctor2Resource;
import com.sbc.resource.hateoas.Doctor3Resource;
import com.sbc.resource.hateoas.Doctor4Resource;
import com.sbc.resource.hateoas.DoctorResource;
import com.sbc.resource.hateoas.User1Resource;
import com.sbc.resource.hateoas.UserResource;
import com.sbc.service.DoctorService;

@RestController
@RequestMapping(path="/reactjs_restful")
public class DoctorController {
	
	@Autowired
	private DoctorService doctorService;
	
	private static final Logger LOG = LoggerFactory.getLogger(DoctorController.class);
	
	/**
	 * save Doctor 
	 * (when using "application/x-www-form-urlencoded",
	 * you must remove @RequestBody annotation because Http request won't recognize this annotation)
	 * @param - User
	 * @return - User
	 * @authorized - Admin
	 */
	@PostMapping(path="/admin/save_doctor", consumes = "application/json", produces = "application/json")
	public ResponseEntity<UserResource> save(@RequestBody User1 user) {
		
        LOG.info("user=" + user.toString());
        
        User u = doctorService.saveDoctor(user);

		/* create UserResource instance */
        UserResource resource = new UserResource(u);
		
		/* UserResource instance has 2 parts - body and link. Initialize the instance body user */
		resource.user = u;
		
		/* create selflink */
		Link selfLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(DoctorController.class).findById(u.getId()))
				.withSelfRel();
		
		/* add selflink to the instance */
		resource.add(selfLink);
		
		return ResponseEntity.status((HttpStatus.CREATED)).body(resource);
	}
	
	
	/**
	 * get Doctor By Id 
	 * (for HATEOAS purpose only)
	 * @param doctorId
	 * @return DoctorResponse
	 */
	public ResponseEntity<DoctorResource> findById(@PathVariable int id) {	
		
		Doctor doc = doctorService.getOnly(id);
		
		/* create DoctorResource instance */
		DoctorResource resource = new DoctorResource(doc);
		
		/* DoctorResource instance has 2 parts - body and link. Initialize the instance body doctor */
		resource.doctor = doc;
		
		/* create selflink */
		Link selfLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(DoctorController.class).findById(doc.getId()))
				.withSelfRel();
		
		/* add selflink to the instance */
		resource.add(selfLink);
	
	return ResponseEntity.status((HttpStatus.OK)).body(resource);
	}
	

	/**
	 * get Doctor By Id (retrieve doctor data for updating purpose)
	 * @param doctorId
	 * @return Doctor4Response
	 * @authorized - Admin
	 */
	@GetMapping(path="/admin_or_doctor/get_doctor_by_Id/{id}", produces = "application/json")
	public ResponseEntity<Doctor4Resource> getDoctorById(@PathVariable int id) {	
		
		Doctor4 doc = doctorService.getDoctorById(id);
		
		/* create Doctor4Resource instance */
		Doctor4Resource resource = new Doctor4Resource(doc);
		
		/* Doctor4Resource instance has 2 parts - body and link. Initialize the instance body doctor */
		resource.doctor = doc;
		
		/* create selflink */
		Link selfLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(DoctorController.class).findById(doc.getId()))
				.withSelfRel();
		
		/* add selflink to the instance */
		resource.add(selfLink);
	
	return ResponseEntity.status((HttpStatus.OK)).body(resource);
	}
	
	
	/**
	 * update Doctor   
	 * @param - User, id
	 * @return - User
	 * @authorized - Admin, Doctor
	 */
	@PutMapping(path="/admin_or_doctor/update_doctor/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<User1Resource> update(@RequestBody User1 user1, @PathVariable int id) {
		
		LOG.info("user1=" + user1);
		
		User1 user = doctorService.update(user1, id);

		/* create User1Resource instance */
		User1Resource resource = new User1Resource(user);
		
		/* User1Resource instance has 2 parts - body and link. Initialize the instance body doctor */
		resource.user = user;
		
		/* create selflink */
		Link selfLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(DoctorController.class).findById(user.getId()))
				.withSelfRel();
		
		/* add selflink to the instance */
		resource.add(selfLink);
		
		return ResponseEntity.status((HttpStatus.OK)).body(resource);
	}
	
	
	/**
	 * get All Doctors
	 * @param
	 * @return list of doctors
	 * @throws Exception 
	 * @authorized - Admin, Patient
	 */
	@GetMapping(path="/admin_or_patient/get_all_doctors", produces = "application/json")
	public ResponseEntity<List<Doctor2Resource>> getAllDoctors() {
		
		List<Doctor2> doctor1List = doctorService.getAllDoctors();
		
		/* create list of Doctor2Resource */
		List<Doctor2Resource> resourceList = new ArrayList<>();
		
		for(Doctor2 doc: doctor1List) {
			
			/* create Doctor2Resource instance */
			Doctor2Resource resource = new Doctor2Resource(doc);
			
			/* Doctor1Resource instance has 2 parts - body and link. Initialize the instance body doctor */
			resource.doctor = doc;
			
			/* create selflink */
			Link selfLink = ControllerLinkBuilder
					.linkTo(ControllerLinkBuilder.methodOn(DoctorController.class).findById(doc.getId()))
					.withSelfRel();
			
			/* add selflink to the instance */
			resource.add(selfLink);
			
			/* add instance to the Doctor2Resource list */
			resourceList.add(resource);
		}
		
		return ResponseEntity.status((HttpStatus.OK)).body(resourceList);
	}
	

	
	/**
	 * get Doctors by CategoryId  
	 * @throws Exception 
	 * @param - id
	 * @return Doctor1
	 * @authorized - Admin, Patient
	 */
	@GetMapping(path="/admin_or_patient/get_doctors_by_category/{id}", produces = "application/json")
	public ResponseEntity<List<Doctor1Resource>> getDoctorsByCategory(@PathVariable int id) {
		
		List<Doctor1> doctor1List = doctorService.getDoctorsByCategory(id);
		
		/* create list of Doctor1Resource */
		List<Doctor1Resource> resourceList = new ArrayList<>();
		
		for(Doctor1 doc: doctor1List) {
			
			/* create Doctor1Resource instance */
			Doctor1Resource resource = new Doctor1Resource(doc);
			
			/* Doctor1Resource instance has 2 parts - body and link. Initialize the instance body doctor */
			resource.doctor = doc;
			
			/* create selflink */
			Link selfLink = ControllerLinkBuilder
					.linkTo(ControllerLinkBuilder.methodOn(DoctorController.class).findById(doc.getId()))
					.withSelfRel();
			
			/* add selflink to the instance */
			resource.add(selfLink);
			
			/* add instance to the Doctor1Resource list */
			resourceList.add(resource);
		}
		
		return ResponseEntity.status((HttpStatus.OK)).body(resourceList);
	}
	
	
	
	/**
	 * get Category and Name of Doctor by doctorId 
	 * @param doctorId
	 * @return categoryId, firstname and lastname 
	 * @throws Exception 
	 * @authorized - Admin, Patient
	 */
	@GetMapping(path="/admin_or_patient/get_category_and_name_by_doctorId/{id}", produces = "application/json")
	public ResponseEntity<Doctor3Resource> getCategoryAndNameByDoctorId(@PathVariable int id) {
		
		Doctor3 doc = doctorService.getCategoryAndNameByDoctorId(id);

		/* create Doctor3Resource instance */
		Doctor3Resource resource = new Doctor3Resource(doc);
		
		/* Doctor3Resource instance has 2 parts - body and link. Initialize the instance body doctor */
		resource.doctor = doc;
		
		/* create selflink */
		Link selfLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(DoctorController.class).findById(doc.getId()))
				.withSelfRel();
		
		/* add selflink to the instance */
		resource.add(selfLink);
		
		return ResponseEntity.status((HttpStatus.OK)).body(resource);
	}
	
	
}