package com.gmail.fitostpm.diamondshooter.config;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.fitostpm.diamondshooter.DSGame;
import com.gmail.fitostpm.diamondshooter.MainClass;

public class ConfigLoader 
{
	public static void LoadConfigs(JavaPlugin plugin)
	{
		try {
			GameLoader.GetConfig(plugin);
		} catch (IOException e) {
			Bukkit.getLogger().info("Unable to load games!");
		}
	}
	
	public static void SaveGame(JavaPlugin plugin, DSGame game)
	{
		try {
			GameLoader.SaveGame(plugin, game);
			MainClass.SavedGames.put(game.getName(), game);
		} catch (IOException e) {
			Bukkit.getLogger().info("Unable to save game " + game.getName());
		}
	}

}
