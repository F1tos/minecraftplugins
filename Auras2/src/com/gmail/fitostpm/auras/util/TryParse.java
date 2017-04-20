package com.gmail.fitostpm.auras.util;

import org.bukkit.ChatColor;

import com.gmail.fitostpm.auras.aura.EnumAura;

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
	
	public static boolean toFloat(String value)
	{
		try {
			Float.parseFloat(value);
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
		try{
			EnumParticle.valueOf(value);
			return true;
		}
		catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	public static boolean toColorableEnumParticle(String value)
	{
		try{
			EnumParticle e = EnumParticle.valueOf(value);
			return e.equals(EnumParticle.REDSTONE) || e.equals(EnumParticle.SPELL_MOB) 
					|| e.equals(EnumParticle.SPELL_MOB_AMBIENT);
		}
		catch (IllegalArgumentException e) {
			return false;
		}		
	}
	
	public static boolean toEnumAura(String value)
	{
		try{
			EnumAura.valueOf(value);
			return true;
		}
		catch(IllegalArgumentException e) {
			return false;
		}
	}
	
}
