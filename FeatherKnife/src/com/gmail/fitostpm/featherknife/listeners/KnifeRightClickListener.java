package com.gmail.fitostpm.featherknife.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;


public class KnifeRightClickListener implements Listener 
{
	@EventHandler
	public void onRightClick(PlayerInteractEntityEvent event)
	{
		if(event.getPlayer().getItemInHand().getType().equals(Material.FEATHER)
			&& event.getPlayer().getItemInHand().hasItemMeta()
			&& event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()
			&& event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Feather Knife")
			&& event.getRightClicked() instanceof CraftLivingEntity)
		{
			CraftPlayer player = (CraftPlayer)event.getPlayer();
			CraftLivingEntity target = (CraftLivingEntity)event.getRightClicked();

			//double playerYaw = Math.abs(player.getHandle().yaw);
			//double targetYaw = Math.abs(target.getHandle().yaw);
			double playerYaw = Math.abs(event.getPlayer().getLocation().getYaw());
			double targetYaw = Math.abs(event.getRightClicked().getLocation().getYaw());
			double diff = Math.abs((double)(playerYaw - targetYaw));
			player.sendMessage("playerYaw: " + playerYaw);
			player.sendMessage("targetYaw: " + targetYaw);
			player.sendMessage("diff: " + diff);

			if(playerYaw > 20 && playerYaw < 340 && targetYaw > 20 && targetYaw < 340)
			{				 
				if(Math.abs((double)(playerYaw - targetYaw)) < 40)
				{
					player.playSound(player.getLocation(), Sound.BAT_HURT, 1, 1);
					target.damage(1000, player);
				}
			}
			else if((playerYaw < 20 && (targetYaw < playerYaw + 20 || targetYaw > 340 + playerYaw)) 
				|| (playerYaw > 340 && (targetYaw > 680 - playerYaw) || targetYaw < 360 - playerYaw))
			{
				player.playSound(player.getLocation(), Sound.BAT_HURT, 1, 1);
				target.damage(1000, player);					
			}	
		}
	}
}
