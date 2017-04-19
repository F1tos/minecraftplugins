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
		
		if(!file.exists())
			GenerateDefault(file);
		
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
	
	public static void GenerateDefault(File file) throws IOException
	{
		file.createNewFile();
		PrintWriter pw  = new PrintWriter(new FileOutputStream(file));
		
		pw.println("1:");
		pw.println("  type: 'wand'");
		pw.println("  name: 'Magic Wand'");
		pw.println("  item: 280");
		pw.println("  color: DARK_AQUA");
		pw.println("  attackdamage: 3.5");
		pw.println("  durability: 160");
		pw.println("  casttime: 1.75");
		pw.println("  distance: 39");
		
		pw.println("2:");
		pw.println("  type: 'woodst'");
		pw.println("  name: 'Wooden Staff'");
		pw.println("  item: 280");
		pw.println("  color: DARK_AQUA");
		pw.println("  attackdamage: 5");
		pw.println("  durability: 250");
		pw.println("  casttime: 1.7");
		pw.println("  distance: 42");
		pw.println("  recipe: 'abaaca0c0'");
		pw.println("    a: 265");
		pw.println("    b: 388");
		pw.println("    c: 280");
		
		pw.println("3:");
		pw.println("  type: 'woodsc'");
		pw.println("  name: 'Wooden Scepter'");
		pw.println("  item: 280");
		pw.println("  color: DARK_AQUA");
		pw.println("  attackdamage: 4");
		pw.println("  durability: 250");
		pw.println("  casttime: 1.5");
		pw.println("  distance: 42");
		pw.println("  recipe: 'abaaca0c0'");
		pw.println("    a: 265");
		pw.println("    b: 264");
		pw.println("    c: 280");
		
		pw.println("4:");
		pw.println("  type: 'bonest'");
		pw.println("  name: 'Bone Staff'");
		pw.println("  item: 352");
		pw.println("  color: BLUE");
		pw.println("  attackdamage: 6");
		pw.println("  durability: 400");
		pw.println("  casttime: 1.5");
		pw.println("  distance: 46");
		pw.println("  recipe: 'abaaca0c0'");
		pw.println("    a: 265");
		pw.println("    b: 388");
		pw.println("    c: 352");
		
		pw.println("5:");
		pw.println("  type: 'bonesc'");
		pw.println("  name: 'Bone Scepter'");
		pw.println("  item: 352");
		pw.println("  color: BLUE");
		pw.println("  attackdamage: 5");
		pw.println("  durability: 400");
		pw.println("  casttime: 1.3");
		pw.println("  distance: 46");
		pw.println("  recipe: 'abaaca0c0'");
		pw.println("    a: 265");
		pw.println("    b: 264");
		pw.println("    c: 352");
		
		pw.println("6:");
		pw.println("  type: 'amberst'");
		pw.println("  name: 'Amber Staff'");
		pw.println("  item: 369");
		pw.println("  color: LIGHT_PURPLE");
		pw.println("  attackdamage: 8");
		pw.println("  durability: 700");
		pw.println("  casttime: 1.3");
		pw.println("  distance: 55");
		pw.println("  recipe: 'abaaca0c0'");
		pw.println("    a: 266");
		pw.println("    b: 388");
		pw.println("    c: 369");
		
		pw.println("7:");
		pw.println("  type: 'ambersc'");
		pw.println("  name: 'Amber Scepter’'");
		pw.println("  item: 369");
		pw.println("  color: LIGHT_PURPLE");
		pw.println("  attackdamage: 7");
		pw.println("  durability: 700");
		pw.println("  casttime: 1.1");
		pw.println("  distance: 55");
		pw.println("  recipe: 'abaaca0c0'");
		pw.println("    a: 266");
		pw.println("    b: 264");
		pw.println("    c: 369");

		pw.println("8:");
		pw.println("  type: 'star'");
		pw.println("  name: 'Ender Star'");
		pw.println("  item: 399");
		pw.println("  color: DARK_PURPLE");
		pw.println("  attackdamage: 10");
		pw.println("  durability: 2000");
		pw.println("  casttime: 0.4");
		pw.println("  distance: 60");
		
		pw.close();
	}

}
