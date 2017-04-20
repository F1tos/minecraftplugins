package auras;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.inventivegames.particle.ParticleEffect;

public class Hypocycloid extends SecondOrderCurve
{
	public Hypocycloid(Player player, ParticleEffect effect, double height, double radius, char axis) {
		super(player, effect, height, radius, axis);
	}

	public double Radius;
	public double Height;
	public char Axis;

	@SuppressWarnings("deprecation")
	@Override
	public void show() 
	{
		Location location = getEffectHolder().getLocation();
		for(double i=0; i<360; i=i+11.25)
		{
			double x = location.getX();
			double y = location.getY()+Height;
			double z = location.getZ();
			switch (Axis)
			{
			case 'x':
				try {
					getEffect().sendToPlayers(Bukkit.getOnlinePlayers(), 
							new Location(getEffectHolder().getWorld(), x, y+Radius*Math.pow(Math.sin(i), 3), z+Radius*Math.pow(Math.cos(i), 3)), 
							0.1F, 0.1F, 0.1F, 0.0001F, 1);
				} catch (Exception e) {	}
				break;
			case 'y':
				try {
					getEffect().sendToPlayers(Bukkit.getOnlinePlayers(), 
							new Location(getEffectHolder().getWorld(), x+Radius*Math.pow(Math.sin(i), 3), y, z+Radius*Math.pow(Math.cos(i), 3)), 
							0.1F, 0.1F, 0.1F, 0.0001F, 1);
				} catch (Exception e) {	}
				break;
			case 'z':
				try {
					getEffect().sendToPlayers(Bukkit.getOnlinePlayers(), 
							new Location(getEffectHolder().getWorld(), x+Radius*Math.pow(Math.sin(i), 3), y+Radius*Math.pow(Math.cos(i), 3), z), 
							0.1F, 0.1F, 0.1F, 0.0001F, 1);
				} catch (Exception e) {	}
				break;			
			}
		}
		
	}

}
