package socketing;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class PurgeWeapons implements Listener
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
			if(meta.hasLore() && meta.getLore().contains(ChatColor.WHITE + "Жемчуг очищения"))
			{
				if(attacked.hasPotionEffect(PotionEffectType.ABSORPTION))
					attacked.removePotionEffect(PotionEffectType.ABSORPTION);
				if(attacked.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE))
					attacked.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
				if(attacked.hasPotionEffect(PotionEffectType.FAST_DIGGING))
					attacked.removePotionEffect(PotionEffectType.FAST_DIGGING);
				if(attacked.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE))
					attacked.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
				if(attacked.hasPotionEffect(PotionEffectType.HEALTH_BOOST))
					attacked.removePotionEffect(PotionEffectType.HEALTH_BOOST);
				if(attacked.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
					attacked.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
				if(attacked.hasPotionEffect(PotionEffectType.INVISIBILITY))
					attacked.removePotionEffect(PotionEffectType.INVISIBILITY);
				if(attacked.hasPotionEffect(PotionEffectType.JUMP))
					attacked.removePotionEffect(PotionEffectType.JUMP);
				if(attacked.hasPotionEffect(PotionEffectType.NIGHT_VISION))
					attacked.removePotionEffect(PotionEffectType.NIGHT_VISION);
				if(attacked.hasPotionEffect(PotionEffectType.REGENERATION))
					attacked.removePotionEffect(PotionEffectType.REGENERATION);
				if(attacked.hasPotionEffect(PotionEffectType.SATURATION))
					attacked.removePotionEffect(PotionEffectType.SATURATION);
				if(attacked.hasPotionEffect(PotionEffectType.SPEED))
					attacked.removePotionEffect(PotionEffectType.SPEED);
				if(attacked.hasPotionEffect(PotionEffectType.WATER_BREATHING))
					attacked.removePotionEffect(PotionEffectType.WATER_BREATHING);
			}
		}		
	}

}
