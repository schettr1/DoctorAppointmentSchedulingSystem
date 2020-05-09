package com.sbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbc.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer>{

}
