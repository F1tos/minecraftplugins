package staffs;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class MissilesHitsListeners implements Listener 
{
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event)
	{
		if(event.getCause().equals(DamageCause.PROJECTILE) && event.getEntity() instanceof LivingEntity)
		{
			LivingEntity damagee = (LivingEntity) event.getEntity();
			Projectile missile = (Projectile) event.getDamager();	
			if(mainClass.LaunchedMissiles.keySet().contains(missile))
			{
				event.setCancelled(true);
				Staff staff = mainClass.LaunchedMissiles.get(missile);
				double dmg = staff.Damage();
				if(staff.isCrit())
					dmg *= 1.5;
				else if (staff.isWeak())
					dmg *= 0.5;
				damagee.damage(dmg,staff.getOwner());
				
			}		
		}
	}

	@EventHandler
	public void onHit(ProjectileHitEvent event)
	{
		if(mainClass.LaunchedMissiles.keySet().contains(event.getEntity()))
			mainClass.LaunchedMissiles.remove(event.getEntity());
	}

	@EventHandler
	public void onExplode(EntityExplodeEvent event)
	{
		if(event.getEntity() instanceof Projectile && 
			mainClass.LaunchedMissiles.keySet().contains((Projectile)event.getEntity()))
			event.setCancelled(true);
	}
}
