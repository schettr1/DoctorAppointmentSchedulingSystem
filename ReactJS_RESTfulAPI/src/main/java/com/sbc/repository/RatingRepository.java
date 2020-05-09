package com.sbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbc.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

	

}
