package com.gmail.fitostpm.zrtd.tower;

import com.gmail.fitostpm.zrtd.tasks.TargetFinder;

import net.minecraft.server.v1_10_R1.Entity;

public abstract class Tower 
{
	protected double Damage;
	protected double Radius;
	protected double Cooldown;
	protected double CritChance;
	protected double CritModifier;
	public boolean OnCooldown;
	protected Entity entity;
	protected Entity Target;
	protected TargetFinder targetFinder;
	
	public boolean hasTarget()
	{
		return Target != null;
	}
	
	public double getDamage()
	{
		return Damage;
	}
	
	public double getRadius()
	{
		return Radius;
	}
	
	public double getCooldown()
	{
		return Cooldown;
	}
	
	public double getCritChance()
	{
		return CritChance;
	}
	
	public double getCritModifier()
	{
		return CritModifier;
	}	
	
	
	public Entity getEntity()
	{
		return entity;
	}
	
	public Entity getTarget()
	{
		return Target;
	}
	
	public void setTarget(Entity target)
	{
		Target = target;
	}
	
	public void removeTarget()
	{
		Target = null;
	}
	
	public abstract void attack();
	public abstract void lookAtTarget();
}
