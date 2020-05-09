package com.sbc.resource.hateoas;

import org.springframework.hateoas.ResourceSupport;
import com.sbc.entity.Feedback;

public class FeedbackResource extends ResourceSupport {

	public Feedback feedback;
	
	public FeedbackResource(Feedback feedback) {
		this.feedback = feedback;
	}
	
}
