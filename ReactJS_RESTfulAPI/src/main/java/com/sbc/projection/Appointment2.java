package com.sbc.projection;

import java.util.Date;

/**
 * Projections are used in Spring JPA to retrieve object from the database which may be different than the entity defined.
 * Because the columns do not match, spring will throw ERROR "No converter found capable of converting from Type [java.lang.Integer] 
 * to [com.sbc.model.Medtech]". If database column has NULL values then use String datatype to repsesent NULL values.
 */

public interface Appointment2 {
	
	public int getId();							// getter method must match column name 'id'
	public Date getStarttime();                 // getter method must match column name 'starttime'
	public Date getEndtime();                   // getter method must match column name 'endttime'
	public String getPatient_id();       		// getter method must match column name 'patient_id'
	public String getFirstname();       		// getter method must match column name 'firstname'
	public String getLastname();       			// getter method must match column name 'lastname'
	public String getBirthdate();       		// getter method must match column name 'birthdate'
	public int getGender();       			// getter method must match column name 'gender'
	

}