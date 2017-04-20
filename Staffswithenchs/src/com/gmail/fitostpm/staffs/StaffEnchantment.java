package com.gmail.fitostpm.staffs;

public class StaffEnchantment 
{
	private StaffEnchantmentType Ench;
	private int Level;
	
	public StaffEnchantment(String ench)
	{
		if(ench.contains(" I") || ench.contains(" V") || ench.contains(" X"))
		{
			if(ench.contains(" I"))
			{
				int index = ench.indexOf(" I");
				Ench = StaffEnchantmentType.getStaffEnchantmentType(ench.substring(0, index));
				String level = ench.substring(index+1);
				switch(level)
				{
				case "I":
					Level = 1;
				case "II":
					Level = 2;
				case "III":
					Level = 3;
				case "IV":
					Level = 4;
				case "IX":
					Level = 9;
				}
			}
			else if(ench.contains(" V"))
			{
				int index = ench.indexOf(" I");
				Ench = StaffEnchantmentType.getStaffEnchantmentType(ench.substring(0, index));
				String level = ench.substring(index+1);
				switch(level)
				{
				case "V":
					Level = 5;
				case "VI":
					Level = 6;
				case "VII":
					Level = 7;
				case "VIII":
					Level = 8;
				}
			}
			else
			{
				Ench = StaffEnchantmentType.getStaffEnchantmentType(ench.substring(0, ench.indexOf(" X")));
				Level = 10;
			}
		}
		else
		{
			Ench = StaffEnchantmentType.getStaffEnchantmentType(ench);
			Level = 1;			
		}
	}
	
	public StaffEnchantment(String ench, int level)
	{
		Ench = StaffEnchantmentType.getStaffEnchantmentType(ench);
		Level = level;
	}
	
	public StaffEnchantment(StaffEnchantmentType ench)
	{
		Ench = ench;
		Level = 1;
	}
	
	public StaffEnchantment(StaffEnchantmentType ench, int level)
	{
		Ench = ench;
		Level = level;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof StaffEnchantment)
		{
			StaffEnchantment se = (StaffEnchantment) obj;
			return (se.Ench.equals(Ench) && se.Level == Level);
		}
		else
			return false;
	}
	
	public boolean isSame(StaffEnchantment se)
	{
		return se.Ench.equals(Ench);
	}
	
	@Override
	public String toString()
	{
		String level;
		switch(Level)
		{
		case 1:
			level = "I";
			break;
		case 2:
			level = "II";
			break;
		case 3:
			level = "III";
			break;
		case 4:
			level = "IV";
			break;
		case 5:
			level = "V";
			break;
		case 6:
			level = "VI";
			break;
		case 7:
			level = "VI";
			break;
		case 8:
			level = "VIII";
			break;
		case 9:
			level = "IX";
			break;
		default:
			level = "X";
			break;
		}
		return Ench.toString() + " " + level;
	}
	
	public int getLevel()
	{
		return Level;
	}
	
	public StaffEnchantmentType getType()
	{
		return Ench;
	}

}
