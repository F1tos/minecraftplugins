package pftest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_10_R1.EntityInsentient;
import net.minecraft.server.v1_10_R1.EnumParticle;
import net.minecraft.server.v1_10_R1.PacketPlayOutWorldParticles;

public class PathGoalRepeater implements Runnable
{

	@Override
	public void run() 
	{
		List<Creep> removed = new ArrayList<Creep>();
		for(Creep c : MainClass.Creeps)
		{
			Location l = c.getHeadingPath()[c.getCurrentPathPoint()];
			new PathfinderWalkToLoc((EntityInsentient)c.ActualEntity, l, 1).c();
			if(c.getActualEntity().getBukkitEntity().getLocation().distance(l) < 1)
			{
				if(c.getCurrentPathPoint()+1 == c.getHeadingPath().length)
				{
					removed.add(MainClass.Creeps.get(MainClass.Creeps.indexOf(c)));
					for(Entity e : c.getActualEntity().getBukkitEntity().getNearbyEntities(50, 50, 50))
					{
						if(e instanceof Player)
						{
							PacketPlayOutWorldParticles packet = 
									new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, 
											(float) c.getActualEntity().getBukkitEntity().getLocation().getX(), 
											(float) c.getActualEntity().getBukkitEntity().getLocation().getY(), 
											(float) c.getActualEntity().getBukkitEntity().getLocation().getZ(), 
											0.1f, 0.1f, 0.1f, 0.0001f, 10, 1);
							((CraftPlayer) ((Player) e)).getHandle().playerConnection.sendPacket(packet);
						}
					}	
				}
				else
					MainClass.Creeps.get(MainClass.Creeps.indexOf(c)).setCurrentPathPoint(c.getCurrentPathPoint()+1);
					
			}
		}
		
		for(Creep c : removed)
		{
			MainClass.Creeps.remove(c);
			c.getActualEntity().getBukkitEntity().remove();
		}
	}	
}
