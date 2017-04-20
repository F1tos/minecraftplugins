package pftest;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import net.minecraft.server.v1_10_R1.Entity;

public class DeathListener implements Listener 
{
	@EventHandler
	public void OnDeath(EntityDeathEvent event)
	{
		Entity entity = ((CraftEntity)event.getEntity()).getHandle();
		if(MainClass.Creeps.contains(entity))
			MainClass.Creeps.remove(entity);
		for(org.bukkit.entity.Entity e : entity.getBukkitEntity().getNearbyEntities(1, 1, 1))
			if(e instanceof Item)
				e.teleport(new Location(e.getLocation().getWorld(),
						e.getLocation().getBlockX(),
						e.getLocation().getBlockY(),
						e.getLocation().getBlockZ()));
	}

}
