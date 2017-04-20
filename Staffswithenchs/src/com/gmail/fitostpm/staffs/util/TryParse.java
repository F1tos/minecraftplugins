package com.gmail.fitostpm.staffs.util;

import org.bukkit.ChatColor;

public class TryParse 
{
	public static boolean toInt(String value)
	{
		try {
			Integer.parseInt(value);
			return true;
		}
		catch (NumberFormatException e)	{
			return false;
		}
	}
	
	public static boolean toDouble(String value)
	{
		try {
			Double.parseDouble(value);
			return true;
		}
		catch (NumberFormatException e)	{
			return false;
		}
	}
	
	public static boolean toChatColor(String value)
	{
		try {
			ChatColor.valueOf(value);
			return true;
		}
		catch (IllegalArgumentException e) {
			return false;
		}
	}
	
}
