package com.gmail.fitostpm.spellbook.tasks;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.gmail.fitostpm.spellbook.MainClass;
import com.gmail.fitostpm.spellbook.spells.PointTargetSpell;
import com.gmail.fitostpm.spellbook.util.CollectionsHelper;
import com.gmail.fitostpm.spellbook.util.EntitySelector;

import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

public class TrajectorySelector extends Selector implements Runnable 
{
	private Player Caster;
	private List<LivingEntity> Targets;
	private float Red = 1;
	private float Green = 1;
	private float Blue = 1;

	public TrajectorySelector(Player caster, PointTargetSpell spell)
	{
		Caster = caster;
		Targets = new LinkedList<LivingEntity>();
		CastingSpell = spell;
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
			List<LivingEntity> newTargets = CollectionsHelper
					.ConvertAll(EntitySelector
					.getNearbyEntities(endLoc, LivingEntity.class, 5, 2, 5, Arrays.asList(Caster)), 
					x -> { return (LivingEntity)x; });
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
		
	private void updateTargets(List<LivingEntity> newTargets)
	{
		for(LivingEntity c : newTargets)
			c.setGlowing(true);
		
		for(LivingEntity c : Targets)
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
		
		for(LivingEntity c : Targets)
			c.setGlowing(false);
		
		Targets = new LinkedList<LivingEntity>();
		Green = 1;
		Blue = 1;
	}

	@Override
	public void Cast() 
	{
		((PointTargetSpell)CastingSpell).Behavior(Caster);
		Stop();
	}

	@Override
	public void Stop() 
	{
		clearTargets();
		MainClass.CastingPlayers.remove(Caster);
		Bukkit.getScheduler().cancelTask(TaskId);		
	}
}
