package pftest;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Zombie;

import net.minecraft.server.v1_10_R1.EntityInsentient;

public class TargetFinder implements Runnable 
{
	private Tower tower;
	private int itsID;

	public TargetFinder(Tower t)
	{
		setTower(t);
	}
	
	@Override
	public void run() 
	{
		org.bukkit.entity.Entity e = tower.entity.getBukkitEntity();
		if(!tower.hasTarget() || tower.Target.getBukkitEntity().getLocation().distance(e.getLocation()) > tower.Radius)
			FindNewTarget();
		else
			tower.attack();
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
		Location loc = tower.entity.getBukkitEntity().getLocation();
		for(Entity e : 
			tower.entity.getBukkitEntity().getNearbyEntities(tower.Radius, tower.Radius, tower.Radius))
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
			tower.Target = null;
		else
			tower.Target = ((CraftEntity)closest).getHandle();
	}

}
