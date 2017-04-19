package socketing;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class EmeraldString implements Listener 
{
	@EventHandler
	public void onArrowShoot(EntityShootBowEvent event)
	{
		if(event.getEntity().getType().equals(EntityType.PLAYER))
		{
			ItemStack bow = event.getBow();
			ItemMeta meta = bow.getItemMeta();
			if(meta.hasLore() && meta.getLore().contains(ChatColor.AQUA + "Изумрудная струна"))
			{
				Arrow arrow = (Arrow) event.getProjectile();
				Vector velocity = arrow.getVelocity();
				arrow.setVelocity(velocity.multiply(2));
				arrow.setCritical(true);
			}
		}
	}	
}
