package com.gmail.fitostpm.corpses.listeners;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class LastHitListener implements Listener 
{
	@EventHandler
	public void onLastHit(EntityDamageEvent event)
	{
		Entity target = event.getEntity();
		if(target instanceof Creature && ((Creature) target).getHealth() < event.getDamage())
		{
		}
	}
}
