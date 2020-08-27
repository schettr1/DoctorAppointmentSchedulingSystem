package com.sbc.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sbc.entity.Appointment;
import com.sbc.projection.Appointment1;
import com.sbc.projection.Appointment2;
import com.sbc.projection.Appointment3;
import com.sbc.projection.Appointment4;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{

	/* GET ALL APPOINTMENTS */
	@Query(value="SELECT a.id AS appointment_id, a.starttime AS appointment_starttime, a.endtime AS appointment_endtime, user_doctor.firstname AS doctor_firstname, " + 
			"user_doctor.lastname AS doctor_lastname, user_patient.firstname AS patient_firstname, user_patient.lastname AS patient_lastname, " + 
			"user_patient.email AS patient_email, user_patient.phone AS patient_phone, user_patient.birthdate as patient_birthdate, a.status AS appointment_status " + 
			"FROM appointment AS a, doctor AS d, patient AS p, user AS user_doctor, user AS user_patient " + 
			"WHERE a.doctorid_fk=d.id AND user_doctor.id=d.id " + 
			"AND a.patientid_fk=p.id AND user_patient.id=p.id"
			, nativeQuery = true)
	List<Appointment1> getAllAppointments();
	

	/* GET APPOINTMENTS AFTER THE SELECTED DATE */
	@Query(value="SELECT a.id, a.starttime, a.endtime, p.id AS patient_id, u.firstname AS firstname, u.lastname AS lastname, u.email AS email " + 
			"FROM appointment as a, patient as p, user as u " + 
			"WHERE a.patientid_fk = p.id " + 
			"AND u.id = p.id " + 
			"AND a.starttime > CAST(:selectedDate AS DATE)"
			, nativeQuery = true)
	List<Appointment4> getAppointmentsAfterThisDate(@Param("selectedDate") Date selectedDate);
	
	
	/* GET APPOINTMENTS OF DOCTOR AFTER SELECTED DATE and TIME */
	@Query(value="SELECT a.id, a.starttime, a.endtime " + 
			"FROM appointment as a " + 
			"WHERE a.doctorid_fk = :doctorId " +
			"AND a.starttime > CAST(:selectedDateTime AS DATETIME)"
			, nativeQuery = true)
	List<Appointment2> getAppointmentsByDoctorAfterSelectedDateTime(@Param("doctorId") int doctorId, @Param("selectedDateTime") Date selectedDateTime);
	
	
	/* GET APPOINTMENTS OF DOCTOR ON SELECTED DATE */
	@Query(value="SELECT a.id, a.starttime, a.endtime, u.id AS patient_id, u.firstname, u.lastname, u.birthdate, u.gender " + 
			"FROM appointment AS a, patient AS p, user AS u " + 
			"WHERE a.patientid_fk = p.id " + 
			"AND p.id = u.id " + 
			"AND a.doctorid_fk = :doctorId " + 
			"AND DATE(a.starttime) = :selectedDateTime"
			, nativeQuery = true)
	List<Appointment2> getAppointmentsByDoctorOnSelectedDate(@Param("doctorId") int doctorId, @Param("selectedDateTime") String selectedDateTime);
	
	
	/* GET APPOINTMENTS OF PATIENT */
	@Query(value="SELECT a.id as appid, a.starttime, a.endtime, a.status, a.doctorid_fk as doctorid, u.firstname as doctorfn, u.lastname as doctorln " + 
			"FROM appointment as a, doctor as d, user as u " + 
			"WHERE a.doctorid_fk = d.id " + 
			"AND d.id = u.id " + 
			"AND a.patientid_fk = :patientId "
			, nativeQuery = true)
	List<Appointment3> getAppointmentsByPatientId(@Param("patientId") int patientId);
	
	
	
	/* GET APPOINTMENTS OF PATIENT BY STATUS */
	@Query(value="SELECT a.id as appid, a.starttime, a.endtime, a.doctorid_fk as doctorid, u.firstname as doctorfn, u.lastname as doctorln " + 
			"FROM appointment as a, doctor as d, user as u " + 
			"WHERE a.doctorid_fk = d.id " + 
			"AND d.id = u.id " + 
			"AND status = :status " +
			"AND a.patientid_fk = :patientId "
			, nativeQuery = true)
	List<Appointment3> getAppointmentsByPatientIdAndStatus(@Param("patientId") int patientId, @Param("status") int status);
	
	
}
