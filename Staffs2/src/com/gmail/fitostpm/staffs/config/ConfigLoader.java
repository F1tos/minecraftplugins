package com.gmail.fitostpm.staffs.config;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.fitostpm.staffs.MainClass;

import net.md_5.bungee.api.ChatColor;

public class ConfigLoader 
{
	public static void LoadConfigs(JavaPlugin plugin)
	{
		try {
			MainClass.Msgs = Messages.GetConfig(plugin);
		} catch (IOException e) {
			Bukkit.getLogger().info(ChatColor.RED + "[Staffs]" + ChatColor.RESET + "Unable to read massages.yml");
		}
		try {
			MainClass.Plcholders = Placeholders.GetConfig(plugin);
		} catch (IOException e) {
			Bukkit.getLogger().info(ChatColor.RED + "[Staffs]" + ChatColor.RESET + "Unable to read placeholders.yml");
		}
		
		try {
			StaffsLoader.GetConfig(plugin);
		} catch (IOException e) {
			Bukkit.getLogger().info(ChatColor.RED + "[Staffs]" + ChatColor.RESET + "Unable to read staffs.yml");
		}
		
	}

}
