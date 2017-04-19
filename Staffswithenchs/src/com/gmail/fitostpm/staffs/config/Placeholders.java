package com.gmail.fitostpm.staffs.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

public class Placeholders 
{
	public static String[] plch = {
			"attackdamage",
			"durability",
			"casttime"
	};

	public static HashMap<String,String> DefaultPlaceholders()
	{
		HashMap<String,String> placeholders = new HashMap<String,String>();
		placeholders.put("attackdamage", "Attack Damage");
		placeholders.put("durability", "Durability");
		placeholders.put("chargetime", "Charging Time");
		return placeholders;
	}
	
	public static HashMap<String,String> GetConfig(JavaPlugin plugin) throws IOException
	{
		File file = new File(plugin.getDataFolder(), "placeholders.yml");
		plugin.getDataFolder().mkdir();
		if(!file.exists())
		{
			file.createNewFile();
			PrintWriter pw = new PrintWriter(new FileOutputStream(file));
			HashMap<String,String> defplch = DefaultPlaceholders();
			for(String s : defplch.keySet())
				pw.println(s + ": '" + defplch.get(s) + "'");
			pw.close();
			return defplch;
		}
		else
		{			
			List<String> lines = new ArrayList<String>();
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) 
				lines.add(line);
			HashMap<String,String> placeholders = new HashMap<String,String>();
			for(String s : lines)
				placeholders.put(s.substring(0, s.indexOf(':')),s.substring(s.indexOf('\'')+1,s.lastIndexOf('\'')));
			return Check(placeholders);
		}
	}
	
	public static HashMap<String,String> Check(HashMap<String,String> map)
	{
		HashMap<String,String> defplch = DefaultPlaceholders();		
		for(String s : plch)
			if(!map.containsKey(s))
				map.put(s, defplch.get(s));
		return map;
	}
}
