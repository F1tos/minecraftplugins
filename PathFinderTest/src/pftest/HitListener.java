package pftest;

import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class HitListener implements Listener
{
	@EventHandler
	public void onHit(ProjectileHitEvent event)
	{
		Snowball s = null;
		Player p = null;
		if(event.getEntity() instanceof Snowball)
		{
			s = (Snowball) event.getEntity();
			if(s.getShooter() instanceof Player)
			{
				p = (Player) s.getShooter();
				double yaw = ((CraftPlayer)p).getHandle().yaw;
				double x = s.getLocation().getX() - p.getLocation().getX();
				double z = s.getLocation().getZ() - p.getLocation().getZ();
				double distance = Math.pow(Math.pow(x, 2) + Math.pow(z, 2), 0.5);
				double angle = Math.toDegrees(Math.acos(z/distance));
				if(x < 0)
					angle = 360 - angle;
				p.sendMessage("actual yaw - " + yaw);
				p.sendMessage("calculated yaw - " + angle*(-1));
			}
			
		}
			
	}
}
