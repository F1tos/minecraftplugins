package com.gmail.fitostpm.staffs.listeners;

import com.gmail.fitostpm.staffs.ItemStaff;
import com.gmail.fitostpm.staffs.MainClass;

import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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
			if(MainClass._LaunchedMissiles.keySet().contains(missile))
			{
				event.setCancelled(true);
				ItemStaff staff = MainClass._LaunchedMissiles.get(missile);
				double dmg = staff.getDamage();
				/*if(staff.isCrit())
					dmg *= 1.5;
				else if (staff.isWeak())
					dmg *= 0.5;*/
				damagee.damage(dmg,staff.getOwner());	
				if(damagee instanceof Player)
					//staff.getOwner().playSound(staff.getOwner().getLocation(), Sound.SUCCESSFUL_HIT, 1, 0);
					staff.getOwner().playSound(staff.getOwner().getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 0);		
			}		
		}
	}

	@EventHandler
	public void onHit(ProjectileHitEvent event)
	{
		if(MainClass._LaunchedMissiles.keySet().contains(event.getEntity()))
			MainClass._LaunchedMissiles.remove(event.getEntity());
	}

	@EventHandler
	public void onExplode(EntityExplodeEvent event)
	{
		if(event.getEntity() instanceof Projectile && 
			MainClass._LaunchedMissiles.keySet().contains((Projectile)event.getEntity()))
			event.setCancelled(true);
	}
}
