package com.sbc.projection;

public interface Doctor3 {
	// Never return Enum in projection, it does not translate correctly.
	public int getId();						// must match column name id
	public int getCategory();				// must match column name 'category'
	public String getFirstname();			// must match column name 'firstname'
	public String getLastname();			// must match column name 'lastname'
	
}
