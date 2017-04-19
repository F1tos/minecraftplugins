package com.gmail.fitostpm.zrtd.tower;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Snowball;
import org.bukkit.projectiles.ProjectileSource;

import com.gmail.fitostpm.zrtd.MainClass;
import com.gmail.fitostpm.zrtd.missile.Missile;
import com.gmail.fitostpm.zrtd.tasks.TargetFinder;
import com.gmail.fitostpm.zrtd.tasks.Cooldown;

import net.minecraft.server.v1_10_R1.EntitySnowman;
import net.minecraft.server.v1_10_R1.GenericAttributes;
import net.minecraft.server.v1_10_R1.PathfinderGoalSelector;

public class SnowmanTower extends Tower
{	
	public SnowmanTower(Location loc)
	{
		Damage = 2;
		Cooldown = 1.7;
		Radius = 7;
		entity = ((CraftEntity)loc.getWorld().spawnEntity(loc, EntityType.SNOWMAN)).getHandle();
		
		EntitySnowman snowman = (EntitySnowman) entity;
		snowman.goalSelector = new PathfinderGoalSelector(
				(snowman.world != null) && (snowman.world.methodProfiler != null) 
				? snowman.world.methodProfiler : null);
		snowman.targetSelector = new PathfinderGoalSelector(
				(snowman.world != null) && (snowman.world.methodProfiler != null) 
				? snowman.world.methodProfiler : null);
		
		snowman.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0);
		snowman.setInvulnerable(true);	
		
		CritChance = 0.1;
		CritModifier = 1.25;
		Target = null;
	}
	
	public void InitTargetFinder()
	{
		targetFinder = new TargetFinder(this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(MainClass.Instance, targetFinder, 0, 2);
	}

	@Override
	public void attack() 
	{
		MainClass.LaunchedMissiles.add(
				new Missile(((ProjectileSource)entity.getBukkitEntity()).launchProjectile(Snowball.class), this));
		OnCooldown = true;
		Bukkit.getScheduler().runTaskLater(MainClass.Instance, new Cooldown(this), (long) (Cooldown*20));
	}

	@Override
	public void lookAtTarget() 
	{
		double x = Target.locX - entity.locX;
		double z = Target.locZ - entity.locZ;
		double distance = Math.pow(Math.pow(x, 2) + Math.pow(z, 2), 0.5);
		double angle = Math.toDegrees(Math.acos(z/distance));
		if(x < 0)
			angle = 360 - angle;
		entity.yaw = (float) angle;
	}
}
