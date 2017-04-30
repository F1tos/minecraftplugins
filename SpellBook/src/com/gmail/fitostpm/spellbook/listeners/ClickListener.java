package com.gmail.fitostpm.spellbook.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.gmail.fitostpm.spellbook.MainClass;
import com.gmail.fitostpm.spellbook.SpellBook;

/*
 * clicking mouse duirng casting spell
 */
public class ClickListener implements Listener 
{
	@EventHandler
	public void onClick(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		
		if(MainClass.CastingPlayers.containsKey(player))
		{
			if(event.getAction().equals(Action.LEFT_CLICK_AIR) 
				||event.getAction().equals(Action.LEFT_CLICK_BLOCK))
				MainClass.CastingPlayers.get(player).Cast();
			else if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) 
					||event.getAction().equals(Action.RIGHT_CLICK_AIR))
				MainClass.CastingPlayers.get(player).Stop();	
			
			event.setCancelled(true);
			return;
		}
		
		SpellBook book = SpellBook.asSpellBook(event.getPlayer().getInventory().getItemInMainHand());
		if(book != null && event.getHand().equals(EquipmentSlot.HAND) 
				&& event.getAction().equals(Action.RIGHT_CLICK_AIR))
		{
			player.openInventory(book.createMenu());
			MainClass.SpellChooseDialog.add(player);
		}
	}
}
