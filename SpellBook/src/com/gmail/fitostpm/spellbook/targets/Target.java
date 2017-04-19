package com.gmail.fitostpm.spellbook.targets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;


public class Target
{
	public LivingEntity entity;
	public double yaw;
	public double pitch;
	
	public Target(LivingEntity entity, double yaw, double pitch)
	{
		this.entity = entity;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public static Target[] getNearbyTargets(LivingEntity entity, double x, double y, double z)
	{
		List<Target> result = new ArrayList<Target>();
		entity.getNearbyEntities(x, y, z).forEach(item -> {
			if(item instanceof LivingEntity)
				result.add(new Target((LivingEntity)item, getYawToTarget(entity, item), getPitchToTarget(entity, item)));
		});
		Target[] targets = new Target[result.size()];
		result.toArray(targets);
		return targets;
	}
	
	public static double getYawToTarget(Entity entity, Entity target)
	{
		double x = target.getLocation().getX() - entity.getLocation().getX();
		double z = target.getLocation().getZ() - entity.getLocation().getZ();
		double distance = Math.pow(Math.pow(x, 2) + Math.pow(z, 2), 0.5);
		double angle = Math.toDegrees(Math.acos(z/distance));
		if(x > 0)
			angle = 360 - angle;
		return angle;
	}
	
	public static double getPitchToTarget(Entity entity, Entity target)
	{
		double y = target.getLocation().getY() - entity.getLocation().getY();
		double distance = entity.getLocation().distance(target.getLocation());
		return Math.toDegrees(Math.acos(y/distance)) - 90;
	}
}