package com.sbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sbc.entity.AppointmentDetail;
import com.sbc.projection.AppointmentDetail1;

public interface AppointmentDetailRepository extends JpaRepository<AppointmentDetail, Integer> {

	/* GET APPOINTMENT DETAIL USING APPOINTMENT ID */
	@Query(value="SELECT a.id AS appointment_id, ad.id AS appointmentdetail_id, u.firstname AS patient_firstname, u.lastname AS patient_lastname, " +
			"u.id AS patient_id, ad.note, ad.prescription, ad.reason, ad.treatment, a.status AS appointment_status " + 
			"FROM appointment_detail AS ad, appointment as a, user AS u, patient AS p " + 
			"WHERE a.patientid_fk = p.id " + 
			"AND p.id = u.id " + 
			"AND a.appointmentdetailid_fk = ad.id " + 
			"AND a.id = :appointmentId"
			, nativeQuery = true)
	AppointmentDetail1 getAppointmentDetailByAppointmentId(@Param("appointmentId") int appointmentId);
	
	
}