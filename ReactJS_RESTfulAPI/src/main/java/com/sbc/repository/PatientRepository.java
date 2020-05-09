package com.sbc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sbc.entity.Patient;
import com.sbc.projection.Doctor4;
import com.sbc.projection.Patient1;
import com.sbc.projection.Patient4;


public interface PatientRepository extends JpaRepository<Patient, Integer> {

	/* GET ALL PATIENTS */
	@Query(value="SELECT u.id, u.firstname, u.lastname, u.gender, u.birthdate, u.email, u.phone " + 
			"FROM patient AS p, user AS u " + 
			"WHERE p.id = u.id "
			, nativeQuery = true)
	List<Patient1> getAllPatients();
	
	
	/* GET PATIENT BY PATIENTID (FETCH PATIENT DATA FOR UPDATING PURPOSE) */
	@Query(value="SELECT u.id, u.firstname, u.lastname, u.email, u.phone, u.gender, u.birthdate, a.street, a.city, a.state, a.zipcode, " +
			"u.username, u.password " + 
			"FROM patient AS p, user AS u, address AS a " + 
			"WHERE p.id = u.id " + 
			"AND u.addressid_fk = a.id " + 
			"AND p.id = :patientId"
			, nativeQuery = true)
	Patient4 findPatientById(@Param("patientId") int patientId);
	
	
}
