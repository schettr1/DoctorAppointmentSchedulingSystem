package com.sbc.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbc.entity.Feedback;
import com.sbc.exception.FeedbackNotFoundException;
import com.sbc.repository.FeedbackRepository;


@Service	
@Transactional
public class FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;
 

	/* SAVE Operation */
	public Feedback save(Feedback feedback) {
		return feedbackRepository.save(feedback);
	}

	/* GET-ALL Operation */
	public List<Feedback> getAll() {
		return feedbackRepository.findAll();
	}
	
	/* GET-ONLY Operation */
	public Feedback getOnly(int id) {
		return feedbackRepository.findById(id)
				.orElseThrow(
						() -> new FeedbackNotFoundException("id=" + id + " cannot be found."));
	}

}
