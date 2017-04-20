package com.gmail.fitostpm.auras.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.fitostpm.auras.MainClass;
import com.gmail.fitostpm.auras.aura.Aura;

public class QuitListeners implements Listener
{	
	@EventHandler
	public void onQuit(PlayerQuitEvent event)
	{
		if(MainClass.AuraMap.containsKey(event.getPlayer()))
		{
			for(Aura a : MainClass.AuraMap.get(event.getPlayer()))	
				Bukkit.getScheduler().cancelTask(a.getTaskId());
			MainClass.AuraMap.remove(event.getPlayer());
		}
	}
}
