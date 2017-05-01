package com.gmail.fitostpm.spellbook.tasks;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.gmail.fitostpm.spellbook.util.CollectionsHelper;
import com.gmail.fitostpm.spellbook.util.EntitySelector;

import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

public class FireballDirector implements Runnable 
{
	private Player Caster;
	private Location CurLoc;
	private Vector CurVelocity;
	private int TaskId;
	
	private final Vector Gravity = new Vector(0, -0.035, 0);
	
	public FireballDirector(Player caster)
	{
		Caster = caster;
		CurLoc = caster.getEyeLocation();
		CurVelocity = getVectorOutOfYawAndPitch(Caster);
	}

	@Override
	public void run() 
	{
		CurLoc.add(CurVelocity);
		CurVelocity.add(Gravity);
		drawSphere(CurLoc, 0.3);		
		
		if(EntitySelector.getNearbyEntities(CurLoc, LivingEntity.class, .5, .5, .5, Arrays.asList(Caster)).size() == 0
				&& CurLoc.getBlock().getType().equals(Material.AIR))
		{
			damageNearbyCreatures(CurLoc, 5, 2, 5, 5, Caster);
			CurLoc.getWorld().playSound(CurLoc, Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
			Bukkit.getScheduler().cancelTask(TaskId);			
		}		
	}

	public int getTaskId() {
		return TaskId;
	}

	public void setTaskId(int taskId) {
		TaskId = taskId;
	}	

	private Vector getVectorOutOfYawAndPitch(Player player)
	{
		double yaw = ((player.getLocation().getYaw() + 90) * Math.PI) / 180;
		double pitch = ((player.getLocation().getPitch() + 90) * Math.PI) / 180;
		return new Vector(Math.sin(pitch)*Math.cos(yaw), Math.cos(pitch), Math.sin(pitch)*Math.sin(yaw));
	}
	
	private void drawSphere(Location loc, double radius)
	{
		for(double i = -90; i < 90; i=i+45)
			for(double j = 0; j < 360; j=j+60)
			{
				float x = (float) loc.getX();
				float y = (float) loc.getY();
				float z = (float) loc.getZ();

				PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FLAME, true, 
						(float)(x + radius * Math.sin(i) * Math.sin(j)), (float)(y + radius*Math.cos(j)), 
						(float)(z + radius * Math.cos(i) * Math.sin(j)), 0.1f, 0.1f, 0.1f, 0.0001f, 1, 1);
				for(Player p : Bukkit.getOnlinePlayers())
					if(p.getLocation().distance(loc) < 40)
						((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
			} 
	}
	
	private void damageNearbyCreatures(Location loc, double dx, double dy, double dz, double damage, Entity source)
	{
		for(LivingEntity l : CollectionsHelper
				.ConvertAll(EntitySelector
				.getNearbyEntities(loc, LivingEntity.class, .5, .5, .5, Arrays.asList(Caster))
				, x -> { return (LivingEntity)x; }))
			l.damage(damage, source);
	}
}
