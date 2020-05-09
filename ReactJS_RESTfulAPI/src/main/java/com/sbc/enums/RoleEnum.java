package com.sbc.enums;

public enum RoleEnum {
	
	// Here Enum = NONE(0) where name = NONE and value = 0
	NONE(0),
	ROLE_DOCTOR(1),
	ROLE_PATIENT(2),
	ROLE_ADMIN(3);
	
	private int value;
	
	private RoleEnum(int value) {
		this.value = value;
	}
	
   public int getValue() {
        return value;
    }
	   
	public static RoleEnum getEnum(int num){
        for (RoleEnum item : RoleEnum.values()) {
            if(item.getValue() == num)
                return item;
        }
        return RoleEnum.NONE;
    }
	
}
