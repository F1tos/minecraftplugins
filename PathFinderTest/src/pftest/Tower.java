package pftest;

import org.bukkit.Location;

import net.minecraft.server.v1_10_R1.Entity;

public abstract class Tower 
{
	protected double Damage;
	protected double Radius;
	protected double Cooldown;
	protected double CritChance;
	protected double CritModifier;
	protected Location Loc;
	protected Entity Target;
	protected TargetFinder targetFinder;
	protected Entity entity;
	
	protected boolean hasTarget()
	{
		return Target != null;
	}
	
	protected abstract void attack();
}
