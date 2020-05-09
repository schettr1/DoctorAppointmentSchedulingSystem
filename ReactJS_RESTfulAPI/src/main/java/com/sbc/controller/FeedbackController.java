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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbc.entity.Feedback;
import com.sbc.resource.hateoas.FeedbackResource;
import com.sbc.service.AppointmentService;
import com.sbc.service.FeedbackService;

@RestController
@RequestMapping(path="/reactjs_restful")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;
	
	private static final Logger LOG = LoggerFactory.getLogger(FeedbackController.class);
	
	/**
	 * save Feedback 
	 * @param - Feedback
	 * @return - Feedback
	 * @authorized - Patient
	 */
	@PostMapping(path="/patient/save_feedback", consumes = "application/json", produces = "application/json")
	public ResponseEntity<FeedbackResource> save(@RequestBody Feedback feedback) {

		LOG.info("feedback.getDate()=" + feedback.getDate());
		Feedback fdback = feedbackService.save(feedback);
		
		/* create FeedbackResource instance */
		FeedbackResource resource = new FeedbackResource(fdback);
		
		/* FeedbackResource instance has 2 parts - body and link. Initialize the instance body feedback */
		resource.feedback = fdback;
		
		/* create selflink */
		Link selfLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(FeedbackController.class).findById(feedback.getId()))
				.withSelfRel();
		
		/* add selflink to the instance */
		resource.add(selfLink);
	
		return ResponseEntity.status((HttpStatus.OK)).body(resource);
		
	}
	
	
	
	/**
	 * get All Feedbacks 
	 * @param - 
	 * @return - List of Feedback
	 * @authorized - Admin 
	 */
	@GetMapping(path="/admin/get_all_feedbacks", produces = "application/json")
	public ResponseEntity<List<FeedbackResource>> getAll() {
		
		List<Feedback> feedbackList = feedbackService.getAll();
		
		/* create list of FeedbackResource */
		List<FeedbackResource> resourceList = new ArrayList<>();
		
		for(Feedback feedback: feedbackList) {
			
			/* create FeedbackResource instance */
			FeedbackResource resource = new FeedbackResource(feedback);
			
			/* FeedbackResource instance has 2 parts - body and link. Initialize the instance body feedback */
			resource.feedback = feedback;
			
			/* create selflink */
			Link selfLink = ControllerLinkBuilder
					.linkTo(ControllerLinkBuilder.methodOn(FeedbackController.class).findById(feedback.getId()))
					.withSelfRel();
			
			/* add selflink to the instance */
			resource.add(selfLink);
			
			/* add instance to the FeedbackResource list */
			resourceList.add(resource);
		}
		
		return ResponseEntity.status((HttpStatus.OK)).body(resourceList);
	}
	
	
	
	/**
	 * get Feedback By Id 
	 * (used for setting Hateoas only)
	 */
	public ResponseEntity<FeedbackResource> findById(@PathVariable int id) {	
		
		Feedback feedback = feedbackService.getOnly(id);
		
		/* create FeedbackResource instance */
		FeedbackResource resource = new FeedbackResource(feedback);
		
		/* FeedbackResource instance has 2 parts - body and link. Initialize the instance body feedback */
		resource.feedback = feedback;
		
		/* create selflink */
		Link selfLink = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(FeedbackController.class).findById(feedback.getId()))
				.withSelfRel();
		
		/* add selflink to the instance */
		resource.add(selfLink);
	
		return ResponseEntity.status((HttpStatus.OK)).body(resource);
		
	}
	
	
}
