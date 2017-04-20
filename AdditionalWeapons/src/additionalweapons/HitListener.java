package additionalweapons;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import de.inventivegames.particle.ParticleEffect;

public class HitListener implements Listener 
{
	public mainClass Plugin;
	
	public HitListener(mainClass plugin)
	{
		Plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onHit(ProjectileHitEvent event)
	{
		Projectile projectile = event.getEntity();
		if (Plugin.FlyingExplGrenades.contains(projectile))
		{
			Player player = (Player) projectile.getShooter();
			Plugin.FlyingExplGrenades.remove(projectile);
			try {
				ParticleEffect.HUGE_EXPLOSION.sendToPlayers(Plugin.getServer().getOnlinePlayers(), projectile.getLocation(), 
						0.1F, 0.1F, 0.1F, 0.0001F, 1);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			projectile.getWorld().playSound(projectile.getLocation(), Sound.EXPLODE, 3, 0);
			for(Entity entity : projectile.getNearbyEntities(5, 5, 5))
			{
				if(entity instanceof LivingEntity)
				{
					double damage = (6-entity.getLocation().distance(projectile.getLocation()))*2;
					((LivingEntity)entity).damage(damage, player);
					
				}
			}				
		}
	}

}
