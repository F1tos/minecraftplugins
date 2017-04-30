package com.gmail.fitostpm.corpses.listeners;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.gmail.fitostpm.corpses.MainClass;

public class LivingEntityDeathListener implements Listener
{
	@EventHandler
	public void onDeath(EntityDeathEvent event)
	{
		LivingEntity entity = event.getEntity();
		if(!(entity instanceof Player) && entity instanceof Creature)
		{
			Bukkit.getScheduler().scheduleSyncDelayedTask(MainClass.Instance, new Runnable()
			{
				@Override
				public void run() 
				{
					entity.setHealth(1);
					entity.setSilent(true);
					entity.setAI(false);
					entity.setInvulnerable(true);
					entity.setCollidable(false);
				}
				
			}, (long) (0.8 * 20));

			List<ItemStack> droppedItems = event.getDrops();
			for(Entity e : entity.getNearbyEntities(1, 1, 1))
				if((e instanceof Item) && droppedItems.contains(((Item)e).getItemStack()))
					e.remove();
			
			String entityName = entity.getCustomName() == null || entity.getCustomName().equals("") 
					? entity.getName() : entity.getCustomName();

			Inventory inventory = Bukkit.createInventory((InventoryHolder)Bukkit.getPlayer("Fitos"), ((droppedItems.size() % 9)+1)*9, entityName);
			inventory.addItem(droppedItems.toArray(new ItemStack[droppedItems.size()]));
			MainClass.CorpsesInventories.put(entity, inventory);
		}
	}

}
