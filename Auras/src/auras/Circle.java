package auras;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.inventivegames.particle.ParticleEffect;

public class Circle extends SecondOrderCurve
{
	public Circle(Player player, ParticleEffect effect, double height, double radius, char axis) {
		super(player, effect, height, radius, axis);
	}
	
	@SuppressWarnings("deprecation")
	public void show()
	{
		Location location = getEffectHolder().getLocation();
		double x = location.getX();
		double y = location.getY()+Height;
		double z = location.getZ();
		for(double i=0; i<360; i=i+11.25)
		{
			switch (Axis)
			{
			case 'x':
				try {
					getEffect().sendToPlayers(Bukkit.getOnlinePlayers(), 
							new Location(getEffectHolder().getWorld(), x, y+Radius*Math.sin(i), z+Radius*Math.cos(i)), 
							0.1F, 0.1F, 0.1F, 0.0001F, 1);
				} catch (Exception e) {	}
				break;
			case 'y':
				try {
					getEffect().sendToPlayers(Bukkit.getOnlinePlayers(), 
							new Location(getEffectHolder().getWorld(), x+Radius*Math.sin(i)*Math.sin(Math.toRadians(45)), y+Radius*Math.cos(Math.toRadians(45)), z+Radius*Math.cos(i)*Math.sin(Math.toRadians(45))), 
							0.1F, 0.1F, 0.1F, 0.0001F, 1);
				} catch (Exception e) {	}
				break;
			case 'z':
				try {
					getEffect().sendToPlayers(Bukkit.getOnlinePlayers(), 
							new Location(getEffectHolder().getWorld(), x+Radius*Math.sin(i), y+Radius*Math.cos(i), z), 
							0.1F, 0.1F, 0.1F, 0.0001F, 1);
				} catch (Exception e) {	}
				break;			
			}
		}
	}
}
