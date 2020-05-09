package com.sbc.enums;

public enum DegreeEnum {

	// Here, value = MD, DO, ...
	NONE(0),
	MD(1),				
	DO(2),
	MBBS(3),
	MPH(4),
	MHS(5),
	MEd(6),
	MS(7),
	MBA(8),
	PhD(9);
	
	private int value;
	
	private DegreeEnum(int value) {
		this.value = value;
	}
	
   public int getValue() {
        return value;
    }
	   
	public static DegreeEnum getEnum(int num){
        for (DegreeEnum item : DegreeEnum.values()) {
            if(item.getValue() == num)
            	return item;
        }
        return DegreeEnum.NONE;
    }
	
}
