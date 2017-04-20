package pftest;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;

import net.minecraft.server.v1_10_R1.Entity;

public enum Creeps 
{
	ZOMBIE_CREEP("Zombie", 54, CustomZombie.class);
	
	private Creeps(String name, int id, Class<? extends Entity> custom)	{ }
	
	public static void spawnEntity(Entity entity, Location loc)
	{
		entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		((CraftWorld)loc.getWorld()).getHandle().addEntity(entity);
	}
	
	public static boolean isCreep(Entity entity)
	{
		if(entity.getClass().equals(CustomZombie.class))
			return true;
		return false;		
	}
}
