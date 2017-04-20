package com.gmail.fitostpm.staffs.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.gmail.fitostpm.staffs.MainClass;
import com.gmail.fitostpm.staffs.Staff;
import com.gmail.fitostpm.staffs.tasks.Cast;

public class RightClickListener implements Listener 
{	
	@EventHandler
	public void onRightClick(PlayerInteractEvent event)
	{
		if((event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
				//&& Staff.isStaff(event.getPlayer().getItemInHand()))
				&& Staff.isStaff(event.getPlayer().getInventory().getItemInMainHand())
				&& event.getHand().equals(EquipmentSlot.HAND))
		{
			Player player = event.getPlayer();
			ItemStack item = player.getInventory().getItemInMainHand();
			//ItemStack item = player.getItemInHand();
			if(MainClass.CastingPlayers.containsKey(player))
				MainClass.CastingPlayers.get(player).Stop();
			else
			{
				MainClass.CastingPlayers.put(player, new Cast(player, item));							
			}
		}
	}
}