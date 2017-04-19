package com.gmail.fitostpm.staffs;

public enum StaffEnchantmentType {
	DURABILITY,
	SPEED,
	WITCHCRAFT;
	
	public static StaffEnchantmentType getStaffEnchantmentType(String ench)
	{
		if(ench.toUpperCase().equals("DURABILITY"))
			return DURABILITY;
		else if(ench.toUpperCase().equals("SPEED"))
			return SPEED;
		else
			return WITCHCRAFT;		
	}
	
	public static boolean isStaffEnchantmentType(String ench)
	{
		return ench.equalsIgnoreCase("durability") 
				|| ench.equalsIgnoreCase("speed") 
				|| ench.equalsIgnoreCase("witchcraft");
	}
	
	public int getMaxLevel()
	{
		if(this.equals(DURABILITY))
			return 3;
		else if (this.equals(SPEED))
			return 4;
		else 
			return 5;		
	}
	
	@Override
	public String toString()
	{
		if(this.equals(DURABILITY))
			return "Durability";
		else if (this.equals(SPEED))
			return "Speed";
		else 
			return "Witchcraft";
	}
}
