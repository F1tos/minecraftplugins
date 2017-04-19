package com.gmail.fitostpm.zrtd.tasks;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;

import com.gmail.fitostpm.zrtd.tower.Tower;

public class TargetFinder implements Runnable 
{
	private Tower tower;
	private int itsID;

	public TargetFinder(Tower t)
	{
		tower = t;
	}
	
	@Override
	public void run() 
	{
		org.bukkit.entity.Entity e = tower.getEntity().getBukkitEntity();
		if(!tower.hasTarget() || tower.getTarget().getBukkitEntity().getLocation().distance(e.getLocation()) 
				> tower.getRadius())
			FindNewTarget();
		else
		{
			tower.lookAtTarget();
			if(!tower.OnCooldown)
				tower.attack();
		}
	}

	public int getItsID() {
		return itsID;
	}

	public void setItsID(int itsID) {
		this.itsID = itsID;
	}

	public Tower getTower() {
		return tower;
	}

	public void setTower(Tower tower) {
		this.tower = tower;
	}
	
	public void FindNewTarget()
	{
		Entity closest = null;
		Location loc = tower.getEntity().getBukkitEntity().getLocation();
		for(Entity e : 
			tower.getEntity().getBukkitEntity().getNearbyEntities(tower.getRadius(), tower.getRadius(), tower.getRadius()))
		{
			if(e instanceof Zombie)
			{
				if(closest == null)
					closest = e;
				else if(e.getLocation().distance(loc) < closest.getLocation().distance(loc))
					closest = e;					
			}		
		}
		
		if (closest == null)
			tower.removeTarget();
		else
			tower.setTarget(((CraftEntity)closest).getHandle());
	}

}
