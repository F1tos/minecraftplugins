package com.gmail.fitostpm.corpses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.fitostpm.corpses.listeners.ClickOnCorpseListener;
import com.gmail.fitostpm.corpses.listeners.GarbageListener;
import com.gmail.fitostpm.corpses.listeners.LivingEntityDeathListener;

public class MainClass extends JavaPlugin
{
	public static MainClass Instance;
	public static HashMap<LivingEntity, Inventory> CorpsesInventories;
	public static List<Double> Results = new ArrayList<Double>();
	
	
	public void onEnable()
	{
		Instance = this;
		CorpsesInventories = new HashMap<LivingEntity, Inventory>();
		
		RegisterListeners(new Listener[]{
			new ClickOnCorpseListener(),
			new LivingEntityDeathListener(),
			new GarbageListener(),
		});
	}
	
	public void RegisterListeners(Listener[] listeners)
	{
		for(Listener l : listeners)
			getServer().getPluginManager().registerEvents(l, this);
			
	}
	
	public void onDisable()
	{
		
	}
}
