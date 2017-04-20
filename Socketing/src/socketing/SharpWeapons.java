package socketing;

import java.util.Random;

import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class SharpWeapons implements Listener 
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
			double damage = event.getDamage();
			Random r = new Random();
			int chance = r.nextInt(99)+1;
			if(meta.hasLore() && meta.getLore().contains(ChatColor.DARK_RED + "Îסענûי סאלמצגוע") && chance < 20)
			{	
				attacker.playSound(attacker.getLocation(), Sound.FALL_SMALL, 1, 0);
				attacked.damage(damage/2, attacker);
				PotionEffect slow = new PotionEffect(PotionEffectType.SLOW,100,1,false);
				attacked.addPotionEffect(slow);
			}
		}		
	}

}
