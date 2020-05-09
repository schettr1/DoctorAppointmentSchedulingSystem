package com.sbc.projection;

import java.util.Date;

/**
 * Projections are used in Spring JPA to retrieve object from the database which may be different than the entity defined.
 * Because the columns do not match, spring will throw ERROR "No converter found capable of converting from Type [java.lang.Integer] to [com.sbc.model.Medtech]" 
 * If database column has NULL values then use String datatype to repsesent NULL values.
 */

public interface Appointment1 {
	
	public int getAppointment_id();							// getter method must match column name 'appointment_id'
	public Date getAppointment_starttime();                 // getter method must match column name 'appointment_starttime'
	public Date getAppointment_endtime();                   // getter method must match column name 'appointment_endttime'
	public String getAppointment_status();					// getter method must match column name 'appointment_status'
	public String getDoctor_firstname();					// getter method must match column name 'doctor_firstname'
	public String getDoctor_lastname();						// getter method must match column name 'doctor_lastname'
	public String getPatient_firstname();					// getter method must match column name 'patient_firstname'
	public String getPatient_lastname();					// getter method must match column name 'patient_lastname'
	public String getPatient_email();						// getter method must match column name 'patient_email'
	public String getPatient_phone();						// getter method must match column name 'patient_phone'
	public String getPatient_birthdate();					// getter method must match column name 'patient_birthdate'

}