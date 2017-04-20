package pftest;

import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R1.EnumParticle;
import net.minecraft.server.v1_9_R1.PacketPlayOutWorldParticles;

public class EffectReapeter implements Runnable 
{
	private ExtendedProjectile p;
	
	@Override
	public void run() 
	{
		float x = (float) p.locX;
		float y = (float) p.locY;
		float z = (float) p.locZ;
		for(Entity e : p.getBukkitEntity().getNearbyEntities(50, 50, 50))
		{
			if(e instanceof Player)
			{
				Player player = (Player) e;
				PacketPlayOutWorldParticles packet = 
						new PacketPlayOutWorldParticles(EnumParticle.CRIT, true, x, y, z, 0.1f, 0.1f, 0.1f, 0.0001f, 10, 1);
				((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);				
			}			
		}
	}
}
