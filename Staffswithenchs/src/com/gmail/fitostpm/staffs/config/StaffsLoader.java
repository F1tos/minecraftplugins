package com.gmail.fitostpm.staffs.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.fitostpm.staffs.MainClass;
import com.gmail.fitostpm.staffs.Staff;
import com.gmail.fitostpm.staffs.util.TryParse;

public class StaffsLoader 
{
	public static List<List<String>> staffsinfo = new ArrayList<List<String>>();
	
	public static void GetConfig(JavaPlugin plugin) throws IOException
	{
		File file = new File(plugin.getDataFolder(), "staffs.yml");	
		
		List<String> lines = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = br.readLine()) != null) 
		{
			if(line.charAt(0) != ' ')
			{
				if(lines.size()!=0 && !staffsinfo.contains(lines))
				{
					staffsinfo.add(lines);
					lines = new ArrayList<String>();
				}
			}
			else
				lines.add(line);
		}
		
		if(!staffsinfo.contains(lines))
			staffsinfo.add(lines);
		br.close();
		
		for(List<String> staff : staffsinfo)
		{
			try	{
				GenerateStaff(staff);				
			}	
			catch (IOException e){
				Bukkit.getLogger().info("[Staffs] Error occured while reading staff " 
										+ (staffsinfo.indexOf(staff) + 1) + ": " + e.getMessage());
			}
		}		
	}
	
	public static void GenerateStaff(List<String> staff) throws IOException
	{
		String type = GetType(staff);
		String name = GetName(staff);
		Material material = GetItem(staff);
		ChatColor color = GetColor(staff);
		double damage = GetDamage(staff);
		int durability = GetDurability(staff);
		double casttime = GetCastTime(staff);
		int distance = GetDistance(staff);
		boolean b = HasRecipe(staff);
		ShapedRecipe recipe;
				
		HashMap<String,Double> stats = new HashMap<String,Double>();
		ItemStack item = new ItemStack(material,1);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(ChatColor.BLUE + "+" + damage + " " + MainClass.Plcholders.get("attackdamage"));
		lore.add(ChatColor.GRAY + MainClass.Plcholders.get("durability") + ": " + durability + "/" + durability);
		lore.add(ChatColor.LIGHT_PURPLE + MainClass.Plcholders.get("chargetime") + ": " + casttime);
		meta.setDisplayName(color + name);
		meta.setLore(lore);
		item.setItemMeta(meta);
		MainClass.commands.put(type, item);
		stats.put("attackdamage", damage);
		stats.put("durability", (double) durability);
		stats.put("chargetime", casttime);
		stats.put("maxdistance", (double) distance);
		Staff.BaseStatsMap.put(color + name, stats);

		if(b)
		{
			recipe = GetRecipe(staff,item);
			Bukkit.getServer().addRecipe(recipe);
		}
	}

	public static String GetType(List<String> staff) throws IOException
	{
		for(String s : staff)
			if(s.indexOf("type:") != -1)
				return s.substring(s.indexOf('\'') + 1, s.lastIndexOf('\''));
		throw new IOException("Unable to read type.");
	}
	
	public static String GetName(List<String> staff) throws IOException
	{
		for(String s : staff)
			if(s.indexOf("name:") != -1)
				return s.substring(s.indexOf('\'') + 1, s.lastIndexOf('\''));
		throw new IOException("Unable to read name.");
	}
	
	@SuppressWarnings("deprecation")
	public static Material GetItem(List<String> staff) throws IOException
	{
		for(String s : staff)
			if(s.indexOf("item:") != -1 && TryParse.toInt(s.substring(s.indexOf(':') + 2)))
				return Material.getMaterial(Integer.parseInt(s.substring(s.indexOf(':') + 2)));
		throw new IOException("Unable to read item.");
	}

	public static ChatColor GetColor(List<String> staff) throws IOException
	{
		for(String s : staff)
			if(s.indexOf("color:") != -1 && TryParse.toChatColor(s.substring(s.indexOf(':') + 2)))
				return ChatColor.valueOf(s.substring(s.indexOf(':') + 2));
		throw new IOException("Unable to read color.");
	}
	
	public static double GetDamage(List<String> staff) throws IOException
	{
		for(String s : staff)
			if(s.indexOf("attackdamage:") != -1 && TryParse.toDouble(s.substring(s.indexOf(':') + 2)))
				return Double.parseDouble(s.substring(s.indexOf(':') + 2));
		throw new IOException("Unable to read attackdamage.");
	}
	
	public static int GetDurability(List<String> staff) throws IOException
	{
		for(String s : staff)
			if(s.indexOf("durability:") != -1 && TryParse.toInt(s.substring(s.indexOf(':') + 2)))
				return Integer.parseInt(s.substring(s.indexOf(':') + 2));
		throw new IOException("Unable to read durability.");
	}
	
	public static double GetCastTime(List<String> staff) throws IOException
	{
		for(String s : staff)
			if(s.indexOf("casttime:") != -1 && TryParse.toDouble(s.substring(s.indexOf(':') + 2)))
				return Double.parseDouble(s.substring(s.indexOf(':') + 2));
		throw new IOException("Unable to read casttime.");
	}
	
	public static int GetDistance(List<String> staff) throws IOException
	{
		for(String s : staff)
			if(s.indexOf("distance:") != -1 && TryParse.toInt(s.substring(s.indexOf(':') + 2)))
				return Integer.parseInt(s.substring(s.indexOf(':') + 2));
		throw new IOException("Unable to read distance.");
	}
	
	public static boolean HasRecipe(List<String> staff)
	{
		boolean b = false;
		for(String s : staff)
			b |= s.indexOf("recipe:") != -1;
		return b;		
	}
	
	@SuppressWarnings("deprecation")
	public static ShapedRecipe GetRecipe(List<String> staff, ItemStack item) throws IOException
	{
		ShapedRecipe recipe = new ShapedRecipe(item);
		HashMap<Character,Material> ingredients = new HashMap<Character,Material>();
		String recipestr = "";
		
		for(String s : staff)
			if(s.indexOf("recipe:") != -1)
				recipestr = s.substring(s.indexOf('\'') + 1, s.lastIndexOf('\''));
			else if(s.indexOf("    ") == 0)
				ingredients.put(s.charAt(4), Material.getMaterial(Integer.parseInt(s.substring(s.indexOf(':') + 2))));
				
		if(recipestr.length() != 9)
			throw new IOException("Unable to read recipe.");
			
		List<Character> neededingredients = new ArrayList<Character>();
		for(Character c : recipestr.toCharArray())
			if(!neededingredients.contains(c) && c != '0')
				neededingredients.add(c);
				
		boolean b = true;
		for(char c : neededingredients)
			b &= ingredients.containsKey(c);
		if(!b)
			throw new IOException("Missing ingredients.");

		recipe.shape(recipestr.substring(0, 3), recipestr.substring(3, 6), recipestr.substring(6));
		for(Character c : ingredients.keySet())
			recipe.setIngredient(c, ingredients.get(c));		
		
		return recipe;		
	}

}
