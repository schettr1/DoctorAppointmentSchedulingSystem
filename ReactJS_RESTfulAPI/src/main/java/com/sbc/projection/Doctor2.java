package com.sbc.projection;

import java.util.Date;

public interface Doctor2 {

	// Never return Enum in projection, it does not translate correctly.
	public int getId();						// must match column name 'id'
	public String getFirstname();			// must match column name 'firstname'
	public String getLastname();			// must match column name 'lastname'
	public int getGender();					// must match column name 'gender'
	public Date getBirthdate();				// must match column name 'birthdate'
	public String getEmail();				// must match column name 'email'
	public String getPhone();				// must match column name 'phone'
	public int[] getDegrees();				// must match column name 'degrees'
	public int getCategory();				// must match column name 'category'
	public double getRating();				// must match column name 'rating'
}
