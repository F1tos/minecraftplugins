package com.gmail.fitostpm.staffs.listeners;

import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import net.minecraft.server.v1_11_R1.NBTBase;
import net.minecraft.server.v1_11_R1.NBTTagCompound;

public class TestListener implements Listener 
{
	private int counter = 0;
	
	//@EventHandler
	public void onClick(PlayerToggleSneakEvent event)
	{				
		Player player = event.getPlayer();
		player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, counter);
		counter--;
		org.bukkit.inventory.ItemStack bukkitItemStack = player.getInventory().getItemInMainHand();
		net.minecraft.server.v1_11_R1.ItemStack spigotItemStack = CraftItemStack.asNMSCopy(bukkitItemStack);
		if(spigotItemStack == null)
			player.sendMessage("spigotItemStack is null");
		else
		{
			NBTTagCompound tag = spigotItemStack.getTag();
			if(tag == null)
				player.sendMessage("tag is null");
			else
				for(String s : tag.c())
				{
					NBTBase nbtbase = tag.get(s);
					player.sendMessage(s + "; class: " + nbtbase.getClass().getName());
				}				
		}
	}
}
