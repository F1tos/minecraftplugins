package auras;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.inventivegames.particle.ParticleEffect;

public class Sphere extends Aura
{
	public double Radius;
	public double Height;
	
	public Sphere (Player player, ParticleEffect effect, double radius, double height)
	{
		setEffectHolder(player);
		setEffect(effect);
		Radius = radius;
		Height = height;		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void show() 
	{
		Location location = getEffectHolder().getLocation();
		for(double i=0; i<360; i=i+11.25)
		{
			for(double j=0; j<180; j=j+11.25)
			{
				double x = location.getX();
				double y = location.getY()+Height;
				double z = location.getZ();
				try {
					getEffect().sendToPlayers(Bukkit.getOnlinePlayers(), 
							new Location(getEffectHolder().getWorld(),x+Radius*Math.sin(i)*Math.sin(j), y+Radius*Math.cos(j), z+Radius*Math.cos(i)*Math.sin(j)), 
							0.1F, 0.1F, 0.1F, 0.0001F, 1);
				} catch (Exception e) {	}
				
			}
		}
		
	}
	

}
