package com.sbc.enums;

public enum StateEnum {

	// Here value = AK, AL ...
	NONE(0),
	AK(1),		
	AL(2),		
	AR(3),		
	AZ(4),
	CA(5),
	CO(6),
	CT(7),
	DE(8),
	FL(9),
	GA(10),
	HI(11),
	IA(12),
	ID(13),
	IL(14),
	IN(15),
	KS(16),
	KY(17),
	LA(18),
	MA(19),
	MD(20),
	ME(21),
	MI(22),
	MN(23),
	MO(24),
	MS(25),
	MT(26),
	NC(27),
	ND(28),
	NE(29),
	NH(30),
	NJ(31),
	NM(32),
	NV(33),
	NY(34),
	OH(35),
	OK(36),
	OR(37),
	PA(38),
	RI(39),
	SC(40),
	SD(41),
	TN(42),
	TX(43),
	UT(44),
	VA(45),
	VT(46),
	WA(47),
	WI(48),
	WV(49),
	WY(50);
	
	
	private int value;
	
	private StateEnum(int value) {
		this.value = value;
	}
	
   public int getValue() {
        return value;
    }
	   
	public static StateEnum getEnum(int num){
        for (StateEnum item : StateEnum.values()) {
            if(item.getValue() == num)
                return item;
        }
        return StateEnum.NONE;
    }
}




