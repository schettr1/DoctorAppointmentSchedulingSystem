package com.sbc.enums;

public enum StatusEnum {

	// Here value -> NONE, PENDING, RECEIVED ...
	NONE(0),			// if no status is found, return as default status
	BOOKED(1),			// status BOOKED is created after patient books the appointment
	RECEIVED(2),		// admin changes status from BOOKED to RECEIVED after patient arrives at the office
	COMPLETED(3),		// doctor changes status from RECEIVED to COMPLETED after he fills out appointment detail form.
	NOT_ARRIVED(4),		// admin changes status from BOOKED to NOT_ARRIVED at the end of the day
	CANCELED(5);		// admin changes status from BOOKED to CANCELED if patient calls the office to cancel the appointment
	
	private int value;
	
	private StatusEnum(int value) {
		this.value = value;
	}
	
   public int getValue() {
        return value;
    }
	   
	public static StatusEnum getEnum(int num){
        for (StatusEnum item : StatusEnum.values()) {
            if(item.getValue() == num)
            	return item;
        }
        return StatusEnum.NONE;
    }
	
	
}
