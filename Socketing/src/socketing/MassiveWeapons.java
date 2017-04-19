package socketing;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class MassiveWeapons implements Listener 
{
	@EventHandler	
	public void onAxeAttack(EntityDamageByEntityEvent event)
	{		
		if(event.getDamager() instanceof Player && event.getCause().equals(DamageCause.ENTITY_ATTACK)
				&& event.getEntity() instanceof LivingEntity)
		{
			LivingEntity attacked = (LivingEntity) event.getEntity();
			double damage = event.getDamage();
			Vector vel = attacked.getVelocity();
			Player attacker = (Player) event.getDamager();
			ItemStack item = attacker.getItemInHand();
			if(!item.equals(null) && item.hasItemMeta() && item.getItemMeta().hasLore())
			{
				ItemMeta meta = attacker.getItemInHand().getItemMeta();
				if(meta.hasLore() && meta.getLore().contains(ChatColor.BLUE + "Массивный самоцвет"))
					for(Entity e : attacked.getNearbyEntities(2, 2, 2))
						if(attacker.getNearbyEntities(3, 3, 3).contains(e) && e instanceof LivingEntity)
						{
							((LivingEntity)e).damage(damage*0.40);
							e.setVelocity(vel);
						}
				
			}
		}
		
	}

}
