package com.gmail.fitostpm.diamondshooter.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import net.minecraft.server.v1_8_R3.EnumParticle;

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
	
	public static boolean toEnumParticle(String value)
	{
		try {
			EnumParticle.valueOf(value);
			return true;
		}
		catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	public static boolean toWorld(String value)
	{
		try{
			Bukkit.getWorld(value);
			return true;
		}
		catch (NullPointerException e)
		{
			return false;
		}
	}
	
}
