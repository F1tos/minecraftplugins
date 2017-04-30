package com.gmail.fitostpm.spellbook.listeners;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import com.gmail.fitostpm.spellbook.MainClass;
import com.gmail.fitostpm.spellbook.tasks.Selector;
import com.gmail.fitostpm.spellbook.tasks.TargetSelector;


/*
 * switching targets
 */
public class SwitchTargetListener implements Listener 
{
	private HashMap<Player, Selector> cp = MainClass.CastingPlayers;
	
	@EventHandler
	public void onPressQ(PlayerDropItemEvent event)
	{
		Player player = event.getPlayer();
		if(cp.containsKey(player) && cp.get(player) instanceof TargetSelector)
		{
			((TargetSelector)cp.get(player)).PrevTarget();
			event.setCancelled(true);
		}			
	}

	@EventHandler
	public void onPressF(PlayerSwapHandItemsEvent event)
	{
		Player player = event.getPlayer();
		if(cp.containsKey(player) && cp.get(player) instanceof TargetSelector)
		{
			((TargetSelector)cp.get(player)).NextTarget();
			event.setCancelled(true);
		}		
	}
}
