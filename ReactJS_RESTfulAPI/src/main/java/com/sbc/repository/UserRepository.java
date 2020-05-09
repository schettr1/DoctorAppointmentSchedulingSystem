package com.sbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbc.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	/* FIND BY USERNAME */
	User findByUsername(String username);
}
