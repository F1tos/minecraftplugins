package com.gmail.fitostpm.corpses.tasks;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

public class TrajectorySelector implements Runnable 
{
	private Player Caster;
	private List<Creature> Targets;
	private float Red = 1;
	private float Green = 1;
	private float Blue = 1;

	public TrajectorySelector(Player caster)
	{
		Caster = caster;
		Targets = new LinkedList<Creature>();
	}
	
	@Override
	public void run() 
	{		
		Vector direction = getVectorOutOfYawAndPitch(Caster);
		Vector gravity = new Vector(0, -0.035, 0);
		Location startLoc = Caster.getEyeLocation();
		Location loc = Caster.getEyeLocation();
		Location endLoc = Caster.getEyeLocation();
		while(loc.getBlock().getType().equals(Material.AIR) && startLoc.distance(loc) < 50)
		{
			endLoc = updateLocation(startLoc, loc, direction, gravity);
			float x = (float) loc.getX();
			float y = (float) loc.getY();
			float z = (float) loc.getZ();
			PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, x, y, z, Red, Green, Blue, 1, 0, 1);
			((CraftPlayer)Caster).getHandle().playerConnection.sendPacket(packet);
		}
		
		if(endLoc.distance(startLoc) < 50)
		{
			drawCircle(endLoc, 5);
			List<Creature> newTargets = getNearbyCreatures(endLoc);
			if(newTargets.size() > 0)
				updateTargets(newTargets);		
			else
				clearTargets();
		}
		else
			clearTargets();
	}
	
	private Location updateLocation(Location sLoc, Location cLoc, Vector dir, Vector grav)
	{
		for(int i = 0; i < 3; i++)
		{
			cLoc.add(dir);
			dir.add(grav);
			
			if(!(cLoc.getBlock().getType().equals(Material.AIR) && sLoc.distance(cLoc) < 50))
				return cLoc;			
		}
		return cLoc;	
	}
	
	private Vector getVectorOutOfYawAndPitch(Player player)
	{
		double yaw = ((player.getLocation().getYaw() + 90) * Math.PI) / 180;
		double pitch = ((player.getLocation().getPitch() + 90) * Math.PI) / 180;
		return new Vector(Math.sin(pitch)*Math.cos(yaw), Math.cos(pitch), Math.sin(pitch)*Math.sin(yaw));
	}
	
	private void drawCircle(Location loc, double radius)
	{
		for(double i = 1; i < 360; i = i + 11.25)
		{
			float x = (float)(loc.getX() + radius * Math.sin(i));
			float y = (float)loc.getBlockY() + 1;
			float z = (float)(loc.getZ() + radius * Math.cos(i));
			PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, x, y, z, Red, Green, Blue, 1, 0, 1);
			((CraftPlayer)Caster).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
	private List<Creature> getNearbyCreatures(Location loc)
	{
		List<Creature> result = new LinkedList<Creature>();
		for(Entity e : loc.getWorld().getNearbyEntities(loc, 5, 2, 5))
			if(e instanceof Creature)
				result.add((Creature)e);
		return result;
	} 
	
	private void updateTargets(List<Creature> newTargets)
	{
		for(Creature c : newTargets)
			c.setGlowing(true);
		
		for(Creature c : Targets)
			if(!newTargets.contains(c))
				c.setGlowing(false);
		
		Targets = newTargets;
		Green = 0;
		Blue = 0;		
	}
	
	private void clearTargets()
	{
		if(Targets.size() == 0)
			return;
		
		for(Creature c : Targets)
			c.setGlowing(false);
		
		Targets = new LinkedList<Creature>();
		Green = 1;
		Blue = 1;
	}
}
