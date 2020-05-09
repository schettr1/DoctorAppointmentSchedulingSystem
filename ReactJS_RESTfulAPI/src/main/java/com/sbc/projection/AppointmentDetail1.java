package com.sbc.projection;

public interface AppointmentDetail1 {

	public int getAppointment_id();					// getter method must match column name 'appointment_id'
	public int getAppointment_status();      	    // getter method must match column name 'appointment_status'
	public String getPatient_id();           		// getter method must match column name 'patient_id'
	public String getPatient_firstname();          	// getter method must match column name 'patient_firstname'
	public String getPatient_lastname();           	// getter method must match column name 'patient_lastname'
	public int getAppointmentdetail_id();			// getter method must match column name 'getAppointmentdetail_id'
	public String getReason();                   	// getter method must match column name 'reason'
	public String getTreatment();                	// getter method must match column name 'treatment'
	public String getPrescription();             	// getter method must match column name 'prescription'
	public String getNote();                   		// getter method must match column name 'note'
	
}
