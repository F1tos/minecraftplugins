package com.gmail.fitostpm.staffs.listeners;

import com.gmail.fitostpm.staffs.ItemStaff;
import com.gmail.fitostpm.staffs.MainClass;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class IteractListeners implements Listener 
{	

	@EventHandler
	public void onLeftClick(PlayerInteractEvent event)
	{
		Action action = event.getAction();
		Player player = event.getPlayer();
		
		if((action.equals(Action.LEFT_CLICK_AIR)
			|| action.equals(Action.LEFT_CLICK_BLOCK) && MainClass._CastingPlayers.containsKey(player))
			&& ItemStaff.isStaff(player.getInventory().getItemInMainHand())
			&& event.getHand().equals(EquipmentSlot.HAND))
		{
			if(MainClass._CastingPlayers.containsKey(player))
				MainClass._CastingPlayers.get(player).LaunchMissile();
			else
				new ItemStaff(player, player.getInventory().getItemInMainHand()).LaunchMissile();
		}
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent event)
	{
		if((event.getAction().equals(Action.RIGHT_CLICK_AIR))
				//&& Staff.isStaff(event.getPlayer().getItemInHand()))
				&& ItemStaff.isStaff(event.getPlayer().getInventory().getItemInMainHand())
				&& event.getHand().equals(EquipmentSlot.HAND))
		{
			Player player = event.getPlayer();
			ItemStack item = player.getInventory().getItemInMainHand();
			//ItemStack item = player.getItemInHand();
			if(MainClass._CastingPlayers.containsKey(player))
				MainClass._CastingPlayers.get(player).StopCasting();
			else
			{
				MainClass._CastingPlayers.put(player, new ItemStaff(player, item));		
				MainClass._CastingPlayers.get(player).StartCasting();					
			}
		}
	}
}