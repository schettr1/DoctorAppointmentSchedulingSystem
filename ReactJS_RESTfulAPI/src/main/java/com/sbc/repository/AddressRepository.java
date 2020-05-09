package com.sbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbc.entity.Address;


public interface AddressRepository extends JpaRepository<Address, Integer> {

	
}
	