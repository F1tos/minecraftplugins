package com.gmail.fitostpm.diamondshooter.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.gmail.fitostpm.diamondshooter.GameState;
import com.gmail.fitostpm.diamondshooter.MainClass;

public class DamagedByArrowListener implements Listener 
{
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event)
	{
		if(event.getDamager() instanceof Player && event.getEntity() instanceof Player
				&& event.getCause().equals(DamageCause.PROJECTILE)
				&& MainClass.getGame((Player)event.getDamager()).equals(MainClass.getGame((Player)event.getEntity()))
				&& (MainClass.getGame((Player)event.getDamager()).CurrentState == GameState.PLAYING
				|| MainClass.getGame((Player)event.getDamager()).CurrentState == GameState.WAITING))
			event.setCancelled(true);
	}
}
