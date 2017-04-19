package com.gmail.fitostpm.staffs.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import com.gmail.fitostpm.staffs.MainClass;

public class PlayerItemHeldListeners implements Listener 
{
	@EventHandler
	public void onSlotChange(PlayerItemHeldEvent event)
	{
		if(MainClass._CastingPlayers.containsKey(event.getPlayer()))
			MainClass._CastingPlayers.get(event.getPlayer()).StopCasting();;
	}

}
