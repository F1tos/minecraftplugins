package com.gmail.fitostpm.corpses.tasks;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

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
		
		if(!(getNearbyCreatures(CurLoc, 0.5, 0.5, 0.5).size() == 0 && CurLoc.getBlock().getType().equals(Material.AIR)))
		{
			damageNearbyCreatures(CurLoc, 5, 2, 5, 5, Caster);
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
		for(Creature c : getNearbyCreatures(loc, dx, dy, dz))
			c.damage(damage, source);
	}
	
	private List<Creature> getNearbyCreatures(Location loc, double dx, double dy, double dz)
	{
		List<Creature> result = new LinkedList<Creature>();
		for(Entity e : loc.getWorld().getNearbyEntities(loc, dx, dy, dz))
			if(e instanceof Creature && !e.equals(Caster))
				result.add((Creature)e);
		return result;
	} 

}
