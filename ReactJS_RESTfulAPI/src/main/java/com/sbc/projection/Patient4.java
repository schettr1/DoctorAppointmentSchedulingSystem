package com.sbc.projection;

import java.util.Date;

public interface Patient4 {

	// Never return Enum in projection, it does not translate correctly in ReactJs.
	
	public int getId();						// must match column name 'id'
	public String getFirstname();			// must match column name 'firstname'
	public String getLastname();			// must match column name 'lastname'
	public String getEmail();				// must match column name 'email'
	public String getPhone();				// must match column name 'phone'
	public int getGender();					// must match column name 'gender'
	public Date getBirthdate();				// must match column name 'birthdate'
	public String getStreet();				// must match column name 'street'
	public String getCity();				// must match column name 'city'
	public int getState();					// must match column name 'state'
	public int getZipcode();				// must match column name 'zipcode'
	public String getUsername();			// must match column name 'username'
	public String getPassword();			// must match column name 'password'
}