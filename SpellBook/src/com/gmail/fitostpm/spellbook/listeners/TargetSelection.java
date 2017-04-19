package com.gmail.fitostpm.spellbook.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import com.gmail.fitostpm.spellbook.MainClass;


public class TargetSelection implements Listener 
{
	@EventHandler
	public void onPressQ(PlayerDropItemEvent event)
	{
		if(MainClass.CastingPlayers.containsKey(event.getPlayer()))
		{
			MainClass.CastingPlayers.get(event.getPlayer()).PrevTarget();
			event.setCancelled(true);
		}			
	}

	@EventHandler
	public void onPressF(PlayerSwapHandItemsEvent event)
	{
		if(MainClass.CastingPlayers.containsKey(event.getPlayer()))
		{
			MainClass.CastingPlayers.get(event.getPlayer()).NextTarget();
			event.setCancelled(true);
		}		
	}
}
