package com.gmail.fitostpm.staffs.listeners;

import com.gmail.fitostpm.staffs.MainClass;
import com.gmail.fitostpm.staffs.Staff;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class AttackListeners implements Listener 
{	
	@EventHandler
	public void onIteract(PlayerInteractEvent event)
	{
		if((event.getAction().equals(Action.LEFT_CLICK_AIR) || 
			event.getAction().equals(Action.LEFT_CLICK_BLOCK) && MainClass.CastingPlayers.containsKey(event.getPlayer())) 
			&& !event.getPlayer().getInventory().getItemInMainHand().equals(null))
			//&& !event.getPlayer().getItemInHand().equals(null))
		{
			Player player = event.getPlayer();
			ItemStack item = player.getInventory().getItemInMainHand();
			//ItemStack item = player.getItemInHand();
			if(item.hasItemMeta() && item.getItemMeta().hasDisplayName() && Staff.isStaff(item))
			{
				if(Staff.isStaff(item))
				{
					int dur = GetDurability(item);
					if(!player.getGameMode().equals(GameMode.CREATIVE))
						dur = DecreaseDurability(item);
					if(dur > -1)
					{
						Staff.LaunchMissile(new Staff(player, item));
						if((double)(GetMaxDurability(item)/10) == (double) dur)
						{
							//player.playSound(player.getLocation(), Sound.GLASS, 1, 0);
							player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, 0);
							player.sendMessage(ChatColor.RED + MainClass.Msgs.get("repairstaffmessage"));
						}
					}
					else
					{
						//player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 0);
						player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 0);
						RemoveOne(player.getInventory(), item);
						if(MainClass.CastingPlayers.containsKey(player))
							MainClass.CastingPlayers.get(player).Stop();
					}
				}
			}			
		}
	}	
	
	public int GetDurability(ItemStack item)
	{
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		String[] alore = new String[lore.size()];
		lore.toArray(alore);
		int i = 0;
		while(i < alore.length && alore[i].indexOf(ChatColor.GRAY + MainClass.Plcholders.get("durability") + ":") == -1)
			i++;
		int beginIndex = alore[i].indexOf(':') + 2;
		int endIndex = alore[i].indexOf('/');
		return Integer.parseInt(alore[i].substring(beginIndex, endIndex));		
	}
	
	public int GetMaxDurability(ItemStack item)
	{
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		String[] alore = new String[lore.size()];
		lore.toArray(alore);
		int i = 0;
		while(i < alore.length && alore[i].indexOf(ChatColor.GRAY + MainClass.Plcholders.get("durability") + ":") == -1)
			i++;
		return Integer.parseInt(alore[i].substring(alore[i].indexOf('/') + 1));		
	}
	
	public int DecreaseDurability(ItemStack item)
	{
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		String[] alore = new String[lore.size()];
		lore.toArray(alore);
		int i = 0;
		while(i < alore.length && alore[i].indexOf(ChatColor.GRAY + MainClass.Plcholders.get("durability") + ":") == -1)
			i++;
		int beginIndex = alore[i].indexOf(':') + 2;
		int endIndex = alore[i].indexOf('/');
		int currentdurability = Integer.parseInt(alore[i].substring(beginIndex, endIndex)) - 1;
		if(currentdurability > -1)
		{
			int maxdurability = Integer.parseInt(alore[i].substring(endIndex + 1));
			alore[i] = ChatColor.GRAY + MainClass.Plcholders.get("durability") + ": " 
						+ currentdurability + "/" + maxdurability;
			lore = new ArrayList<String>();
			for(String s : alore)
				lore.add(s);
			meta.setLore(lore);
			item.setItemMeta(meta);
		}
		return currentdurability;
	}
	
	public void RemoveOne(Inventory inv, ItemStack item)
	{
		int slot = getSlot(inv,item);
		int amount = inv.getItem(slot).getAmount();
		if(amount == 1)
			inv.setItem(slot, new ItemStack(Material.AIR));
		else
		{
			item.setAmount(amount-1);
			inv.setItem(slot, item);
		}	
	}
	
	public int getSlot(Inventory inv, ItemStack item)
	{
		int i;
		for(i=1; i<65; i++)
		{
			item.setAmount(i);
			if(inv.contains(item))
				return inv.first(item);
		}
		return 0;
	}

}
