package socketing;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class GravityWeapons implements Listener 
{
	@EventHandler
	public void onSwordAttack(EntityDamageByEntityEvent event)
	{
		if(event.getDamager().getType().equals(EntityType.PLAYER) && event.getCause().equals(DamageCause.ENTITY_ATTACK)
				&& event.getEntity() instanceof LivingEntity)
		{
			LivingEntity attacked = (LivingEntity) event.getEntity();
			Player attacker = (Player) event.getDamager();
			ItemMeta meta = attacker.getItemInHand().getItemMeta();
			if(meta.hasLore() && meta.getLore().contains(ChatColor.DARK_GREEN + "Самоцвет гравитации"))
			{
				Vector velocity = attacked.getVelocity();
				velocity.setY(1.5);
				attacked.setVelocity(velocity);
			}
		}		
	}

}
