package pftest;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Snowball;
import org.bukkit.projectiles.ProjectileSource;

import net.minecraft.server.v1_10_R1.EntitySnowman;
import net.minecraft.server.v1_10_R1.GenericAttributes;
import net.minecraft.server.v1_10_R1.PathfinderGoalSelector;

public class SnowmanTower extends Tower
{	
	public SnowmanTower(Location loc)
	{
		Loc = loc;
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
	protected void attack() 
	{		
		((ProjectileSource)entity.getBukkitEntity()).launchProjectile(Snowball.class);
	}
}
