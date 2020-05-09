package com.sbc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sbc.entity.Doctor;
import com.sbc.projection.Doctor1;
import com.sbc.projection.Doctor2;
import com.sbc.projection.Doctor3;
import com.sbc.projection.Doctor4;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

	/* GET ALL DOCTORS */
	@Query(value="SELECT u.id, u.firstname, u.lastname, u.gender, u.birthdate, u.email, u.phone, GROUP_CONCAT(DISTINCT deg.degree) AS degrees, " +
			"d.category, AVG(r.points) AS rating " + 
			"FROM doctor AS d, user AS u, rating AS r, degrees AS deg " + 
			"WHERE d.id = u.id " + 
			"AND r.doctorid_fk = d.id " + 
			"AND deg.doctor_id = d.id " + 
			"GROUP BY u.id"
			, nativeQuery = true)
	List<Doctor2> getAllDoctors();

	/* GET DOCTOR BY DOCTORID (FETCH DOCTOR DATA FOR UPDATING PURPOSE) */
	@Query(value="SELECT u.id, u.firstname, u.lastname, u.email, u.phone, u.gender, u.birthdate, a.street, a.city, a.state, a.zipcode, " +
			"d.category, GROUP_CONCAT(DISTINCT deg.degree) AS degrees, u.username, u.password " + 
			"FROM doctor AS d, user AS u, degrees AS deg, address AS a\r\n" + 
			"WHERE d.id = u.id " + 
			"AND d.id = deg.doctor_id " + 
			"AND u.addressid_fk = a.id " + 
			"AND d.id = :doctorId"
			, nativeQuery = true)
	Doctor4 findDoctorById(@Param("doctorId") int doctorId);
	
	/* GET DOCTORS BY CATEGORY */
	@Query(value="SELECT u.id, u.firstname, u.lastname " + 
			"FROM doctor as d, user as u " + 
			"WHERE u.id = d.id and d.category = :categoryId"
			, nativeQuery = true)
	List<Doctor1> getDoctorsByCategory(@Param("categoryId") int categoryId);
	
	
	/* GET CATEGORY AND DOCTOR NAME USING DOCTORID  */
	@Query(value="SELECT u.id, u.firstname, u.lastname, d.category " + 
			"FROM doctor AS d, user AS u " + 
			"WHERE d.id = u.id " + 
			"AND d.id = :doctorId"
			, nativeQuery = true)
	Doctor3 getCategoryAndNameByDoctorId(@Param("doctorId") int doctorId);
	
	
}