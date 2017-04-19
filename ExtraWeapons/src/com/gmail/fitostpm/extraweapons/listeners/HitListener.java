package com.gmail.fitostpm.extraweapons.listeners;

import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.gmail.fitostpm.extraweapons.MainClass;

import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

public class HitListener implements Listener 
{

	@EventHandler
	public void onHit(ProjectileHitEvent event)
	{
		Projectile projectile = event.getEntity();
		if(projectile.getShooter() instanceof Player && MainClass.FlyingExplGrenades.contains(projectile))
		{
			Player player = (Player) projectile.getShooter();
			MainClass.FlyingExplGrenades.remove(projectile);
			projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 3, 0);			
			for(Entity e : projectile.getNearbyEntities(100, 100, 100))
			{
				if(e instanceof Player)
				{
					PacketPlayOutWorldParticles packet = 
							new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_LARGE, true, 
									(float) projectile.getLocation().getX(), 
									(float) projectile.getLocation().getY(), 
									(float) projectile.getLocation().getZ(), 0.1f, 0.1f, 0.1f, 0.0001f, 10, 1);
					((CraftPlayer) ((Player) e)).getHandle().playerConnection.sendPacket(packet);
				}
			}
			for(Entity entity : projectile.getNearbyEntities(5, 5, 5))
			{
				if(entity instanceof Damageable)
				{
					double damage = (6-entity.getLocation().distance(projectile.getLocation()))*2;
					((LivingEntity)entity).damage(damage, player);
					
				}
			}				
		}
	}
}
