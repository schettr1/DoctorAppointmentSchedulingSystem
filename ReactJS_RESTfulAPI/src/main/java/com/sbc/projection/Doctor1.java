package com.sbc.projection;

/**
 * Projections are used in Spring JPA to retrieve object from the database which may be different than the entity defined.
 * Because the columns do not match, spring will throw ERROR "No converter found capable of converting from Type [java.lang.Integer] to [com.sbc.model.Medtech]" 
 * If database column has NULL values then use String datatype to repsesent NULL values.
 * 
 * Get Doctors By Category
 *
 */
public interface Doctor1 {

	public int getId();							// getter method must match column name 'id'
	public String getFirstname();                 // getter method must match column name 'firstname'
	public String getLastname();                   // getter method must match column name 'lastname'
	
}
