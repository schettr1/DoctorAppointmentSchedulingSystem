package com.sbc.projection;

import java.util.Date;

import com.sbc.enums.StatusEnum;

/**
 * Projections are used in Spring JPA to retrieve object from the database which may be different than the entity defined.
 * Because the columns do not match, spring will throw ERROR "No converter found capable of converting from Type [java.lang.Integer] to [com.sbc.model.Medtech]" 
 * If database column has NULL values then use String datatype to repsesent NULL values.
 */

public interface Appointment3 {
	
	public int getAppid();						// getter method must match column name 'appid'
	public Date getStarttime();                 // getter method must match column name 'starttime'
	public Date getEndtime();                   // getter method must match column name 'endttime'
	public StatusEnum getStatus();                  // getter method must match column name 'status'
	public int getDoctorid();                   // getter method must match column name 'doctorid'
	public String getDoctorfn();                   // getter method must match column name 'doctorfn'
	public String getDoctorln();                   // getter method must match column name 'doctorln'

}