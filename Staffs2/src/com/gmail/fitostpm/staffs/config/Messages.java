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

public class Messages 
{
	public static String[] msgs = {
			"repairstaffmessage",
			"repairsuccess",
			"notstaffinhand",
	};
	
	public static HashMap<String,String> DefaultMessages()
	{
		HashMap<String,String> messages = new HashMap<String,String>();
		messages.put("repairstaffmessage", "Your staff needs to be repaired!");
		messages.put("repairsuccess", "Your staff has been repaired!");
		messages.put("notstaffinhand", "Item in your hand is not staff!");
		return messages;
	}
	
	public static HashMap<String,String> GetConfig(JavaPlugin plugin) throws IOException
	{
		File file = new File(plugin.getDataFolder(), "messages.yml");
		plugin.getDataFolder().mkdir();
		if(!file.exists())
		{
			file.createNewFile();
			PrintWriter pw = new PrintWriter(new FileOutputStream(file));
			HashMap<String,String> defmsgs = DefaultMessages();
			for(String s : defmsgs.keySet())
				pw.println(s + ": '" + defmsgs.get(s) + "'");
			pw.close();
			return defmsgs;
		}
		else
		{			
			List<String> lines = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) 
				lines.add(line);
			br.close();

			HashMap<String,String> messages = new HashMap<String,String>();
			for(String s : lines)
				messages.put(s.substring(0, s.indexOf(':')),s.substring(s.indexOf('\'')+1,s.lastIndexOf('\'')));
			return messages;
		}
	}
	
	public static HashMap<String,String> Check(HashMap<String,String> map)
	{
		HashMap<String,String> defplch = DefaultMessages();		
		for(String s : msgs)
			if(!map.containsKey(s))
				map.put(s, defplch.get(s));
		return map;
	}
}
