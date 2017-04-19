package socketing;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class LightningWeapons implements Listener 
{
	@EventHandler
	public void onAttack(EntityDamageByEntityEvent event)
	{
		if(event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity
				&& event.getCause().equals(DamageCause.ENTITY_ATTACK))
		{
			Player attacker = (Player) event.getDamager();
			LivingEntity attacked = (LivingEntity) event.getEntity();
			ItemStack item = attacker.getItemInHand();
			if(!item.equals(null) && item.hasItemMeta() && item.getItemMeta().hasLore()
				&& item.getItemMeta().getLore().contains(ChatColor.WHITE + "Самоцвет молний"))
				attacked.getWorld().strikeLightning(attacked.getLocation());
		}
	}
}
