package com.sbc.enums;

public enum CategoryEnum {

	// Here, value = NONE, GENERAL_PHYSICIAN, ...
	NONE(0),
	GENERAl_PHYSICIAN(1),
	DERMATOLOGIST(2),
	ORTHOPEDIC(3),
	PEDIATRIC(4),
	NEUROLOGIST(5);
	
	private int value;
	
	private CategoryEnum(int value) {
		this.value = value;
	}
	
   public int getValue() {
        return value;
    }
	   
	public static CategoryEnum getEnum(int num){
        for (CategoryEnum item : CategoryEnum.values()) {
            if(item.getValue() == num)
            	return item;
        }
        return CategoryEnum.NONE;
    }
	
}
