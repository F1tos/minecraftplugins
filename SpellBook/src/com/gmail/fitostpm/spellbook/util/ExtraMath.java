package com.gmail.fitostpm.spellbook.util;

import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class ExtraMath 
{
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
	
	public static Vector getVectorOutOfYawAndPitch(Entity entity)
	{
		double yaw = ((entity.getLocation().getYaw() + 90) * Math.PI) / 180;
		double pitch = ((entity.getLocation().getPitch() + 90) * Math.PI) / 180;
		return new Vector(Math.sin(pitch)*Math.cos(yaw), Math.cos(pitch), Math.sin(pitch)*Math.sin(yaw));
	}

}
