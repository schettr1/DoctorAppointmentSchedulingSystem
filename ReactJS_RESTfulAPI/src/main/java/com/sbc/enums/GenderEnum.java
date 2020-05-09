package com.sbc.enums;

public enum GenderEnum {

	// Here, value = MALE, FEMALE ...
	NONE(0),
	MALE(1),		
	FEMALE(2),		
	OTHER(3);	
	
	private int value;
	
	private GenderEnum(int value) {
		this.value = value;
	}
	
   public int getValue() {
        return value;
    }
	   
	public static GenderEnum getEnum(int num){
        for (GenderEnum item : GenderEnum.values()) {
            if(item.getValue() == num)
            	return item;
        }
        return GenderEnum.NONE;
    }
}
