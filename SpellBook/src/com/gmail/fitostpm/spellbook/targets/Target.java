package com.gmail.fitostpm.spellbook.targets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.LivingEntity;

import com.gmail.fitostpm.spellbook.util.ExtraMath;


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
				result.add(new Target((LivingEntity)item, ExtraMath.getYawToTarget(entity, item), ExtraMath.getPitchToTarget(entity, item)));
		});
		Target[] targets = new Target[result.size()];
		result.toArray(targets);
		return targets;
	}
}