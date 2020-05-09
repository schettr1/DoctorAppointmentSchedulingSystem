package com.sbc.projection;

import java.util.Date;

import com.sbc.enums.GenderEnum;

public interface Patient1 {
	// Never return Enum in projection, it does not translate correctly.
	public int getId();						// must match column name 'id'
	public String getFirstname();			// must match column name 'firstname'
	public String getLastname();			// must match column name 'lastname'
	public int getGender();					// must match column name 'gender'
	public Date getBirthdate();				// must match column name 'birthdate'
	public String getEmail();				// must match column name 'email'
	public String getPhone();				// must match column name 'phone'

}
