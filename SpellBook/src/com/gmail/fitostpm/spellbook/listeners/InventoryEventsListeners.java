package com.gmail.fitostpm.spellbook.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.fitostpm.spellbook.MainClass;
import com.gmail.fitostpm.spellbook.spells.EnumSpell;
import com.gmail.fitostpm.spellbook.spells.Spell;
import com.gmail.fitostpm.spellbook.targets.Target;
import com.gmail.fitostpm.spellbook.tasks.TargetSelector;


public class InventoryEventsListeners implements Listener
{

	@EventHandler
	public void onItemClick(InventoryClickEvent event)
	{
		if(MainClass.SpellChooseDialog.contains(event.getWhoClicked()))
		{
			event.setCancelled(true);
			ItemStack item = event.getCurrentItem();
			if(item.hasItemMeta() && item.getItemMeta().hasDisplayName() &&
					EnumSpell.getByName(item.getItemMeta().getDisplayName()) != null)
			{
				
				Player player = (Player) event.getWhoClicked();
				Spell spell = EnumSpell.getByName(item.getItemMeta().getDisplayName()).getSpell();

				player.closeInventory();
				if(spell.CanSelfTarget && event.getClick().equals(ClickType.SHIFT_LEFT))
				{
					spell.Behavior(player, player);
					player.getInventory().remove(event.getCurrentItem());
					MainClass.SpellChooseDialog.remove(player);
				}
				else
				{
					Target[] targets = spell.selectTargets(player);
					if(targets.length > 0)
					{
						double playerYaw = player.getLocation().getYaw();
						if(playerYaw < 0 )
							playerYaw += 360;
						TargetSelector ts = new TargetSelector(player, targets,
								getLowestYawDifference(targets, playerYaw), spell);
						ts.setTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(MainClass.Instance, ts, 0, 1));
						MainClass.CastingPlayers.put(player, ts);
					}						
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event)
	{
		if(MainClass.SpellChooseDialog.contains(event.getPlayer()))
		{
			MainClass.SpellChooseDialog.remove(event.getPlayer());
		}
	}	

	public int getLowestYawDifference(Target[] targets, double playerYaw)
	{
		int lowest = 0;
		
		if(targets.length != 1)
			while(lowest + 1 < targets.length && 
					Math.abs(targets[lowest].yaw - playerYaw) > Math.abs(targets[lowest + 1].yaw - playerYaw))
				lowest++;				
						
		return lowest;
	}
}
