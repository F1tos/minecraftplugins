package com.gmail.fitostpm.crossbows.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import com.gmail.fitostpm.crossbows.MainClass;

import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagString;

public class TestListener implements Listener 
{
	@EventHandler
	public void OnItemClick(PlayerInteractEvent event)
	{
		/*if(event.getPlayer().getItemInHand().getType().equals(Material.DIAMOND))
			if(!MainClass.players.containsKey(event.getPlayer()))
				MainClass.players.put(event.getPlayer(),0d);
			else
				MainClass.players.remove(event.getPlayer());}
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR))
		{			
			Player player = event.getPlayer();
			org.bukkit.inventory.ItemStack bukkitItemStack = player.getItemInHand();
			net.minecraft.server.v1_8_R3.ItemStack spigotItemStack = CraftItemStack.asNMSCopy(bukkitItemStack);
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
		
		if(event.getAction().equals(Action.LEFT_CLICK_AIR))
		{
			Player player = event.getPlayer();
			org.bukkit.inventory.ItemStack bukkitItemStack = player.getItemInHand();
			net.minecraft.server.v1_8_R3.ItemStack spigotItemStack = CraftItemStack.asNMSCopy(bukkitItemStack);
			NBTTagCompound tag = new NBTTagCompound();
			tag.set("hello", new NBTTagString("world"));
			spigotItemStack.setTag(tag);		
			player.setItemInHand(CraftItemStack.asBukkitCopy(spigotItemStack));
		}*/		
	}	
	
	@EventHandler
	public void OnClick(PlayerInteractEntityEvent event)
	{
		event.getPlayer().sendMessage("1");
		event.getPlayer().sendMessage(event.getRightClicked().getClass().getName());
		event.getPlayer().sendMessage("is craftplayer - " + (event.getRightClicked() instanceof CraftPlayer));
		event.getPlayer().sendMessage(event.getPlayer().getItemInHand().getType().toString());
		
		if(event.getRightClicked() instanceof CraftPlayer && event.getPlayer().getItemInHand().getType().equals(Material.FEATHER))
		{
			event.getPlayer().sendMessage("2");
			Player player = event.getPlayer();
			Player target = ((CraftPlayer)event.getRightClicked()).getPlayer();

			event.getPlayer().sendMessage("3");
			float playerYaw = ((CraftPlayer)player).getHandle().yaw;
			float targetYaw = ((CraftPlayer)target).getHandle().yaw;
			
			if(playerYaw > 20 && playerYaw < 340 && targetYaw > 20 && targetYaw < 340 
				&& Math.abs((double)(playerYaw - targetYaw)) > 40)
			{
				event.getPlayer().sendMessage("4");
				target.damage(1000, player);
			}
			else if((playerYaw < 20 && (targetYaw < playerYaw + 20 || targetYaw > 340 + playerYaw)) 
				|| (playerYaw > 340 && (targetYaw > 680 - playerYaw) || targetYaw < 360 - playerYaw))
			{
				event.getPlayer().sendMessage("5");
				target.damage(1000, player);					
			}		
		}
		
	}
} 
