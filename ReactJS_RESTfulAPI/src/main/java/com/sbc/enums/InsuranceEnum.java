package com.sbc.enums;

public enum InsuranceEnum {

	// Here, value = NONE, UNITED_HEALTH, CVS ...
	NONE(0),
	UNITED_HEALTH(1),
	KAISER_FOUNDATION(2),
	SIGNA_HEALTH(3),
	HUMANA(4),
	CVS(5);
	
	private int value;
	
	private InsuranceEnum(int value) {
		this.value = value;
	}
	
   public int getValue() {
        return value;
    }
	   
	public static InsuranceEnum getEnum(int num){
        for (InsuranceEnum item : InsuranceEnum.values()) {
            if(item.getValue() == num)
            	return item;
        }
        return InsuranceEnum.NONE;
    }
}