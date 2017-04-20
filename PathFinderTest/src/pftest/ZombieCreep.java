package pftest;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;

import net.minecraft.server.v1_10_R1.EntityZombie;
import net.minecraft.server.v1_10_R1.EnumZombieType;
import net.minecraft.server.v1_10_R1.PathfinderGoalSelector;

public class ZombieCreep extends Creep
{
	public ZombieCreep(Location[] path)
	{
		Path = path;
		ActualEntity = ((CraftEntity)Bukkit.getWorld(path[0].getWorld().getName()).spawnEntity(path[0], EntityType.ZOMBIE))
				.getHandle();
		
		((EntityZombie)ActualEntity).setBaby(false);
		((EntityZombie)ActualEntity).setVillagerType(EnumZombieType.NORMAL);
		CurrentPathPoint = 1;		

		((EntityZombie)ActualEntity).goalSelector = new PathfinderGoalSelector(
				(ActualEntity.world != null) && (ActualEntity.world.methodProfiler != null) 
				? ActualEntity.world.methodProfiler : null);
		((EntityZombie)ActualEntity).targetSelector = new PathfinderGoalSelector(
				(ActualEntity.world != null) && (ActualEntity.world.methodProfiler != null) 
				? ActualEntity.world.methodProfiler : null);
	}
}
