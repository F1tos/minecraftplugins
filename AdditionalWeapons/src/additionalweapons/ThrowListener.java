package additionalweapons;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class ThrowListener implements Listener 
{
	public mainClass Plugin;
	
	public ThrowListener(mainClass plugin)
	{
		Plugin = plugin;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e)
	{		
		Player p = e.getPlayer();
		ItemStack item = p.getItemInHand();
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) && !item.equals(null) && (item.getItemMeta().hasDisplayName()))
		{
			String name = item.getItemMeta().getDisplayName();
			if(name.equals(ChatColor.DARK_RED + "Осколочная граната"))
			{
				Plugin.FlyingExplGrenades.add(p.launchProjectile(Snowball.class));
				ItemStack item1 = new ItemStack(item);
				item1.setAmount(1);
				RemoveOne(p.getInventory(),item1);				
			} 
		}
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
