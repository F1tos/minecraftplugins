package com.gmail.fitostpm.corpses.listeners;

import org.bukkit.EntityEffect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.gmail.fitostpm.corpses.MainClass;

public class ClickOnCorpseListener implements Listener 
{
	@EventHandler
	public void onClick(PlayerInteractEntityEvent event)
	{
		Entity entity = event.getRightClicked();
		if(entity instanceof LivingEntity
			&& MainClass.CorpsesInventories.containsKey((LivingEntity)entity))
		{
			event.getPlayer().openInventory(MainClass.CorpsesInventories.get((LivingEntity)entity));
		}
		else
		{
			entity.playEffect(EntityEffect.DEATH);
		}
	}
}
