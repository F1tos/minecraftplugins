package auras;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.inventivegames.particle.ParticleEffect;

public class Spirit extends Aura
{
	public double MinRadius;
	public double CurRadius;
	public double MaxRadius;
	public double MinHeight;
	public double MaxHeight;
	public double CurHeight;
	public double CurPos;
	public boolean RadInc;
	public boolean HeightInc;
	
	public Spirit (Player player, ParticleEffect effect, double minheight, double maxheight, double minrad, double maxrad)
	{
		setEffectHolder(player);
		setEffect(effect);
		MinRadius = Math.min(minrad, maxrad);
		MaxRadius = Math.max(minrad, maxrad);
		MinHeight = Math.min(minheight, maxheight);
		MaxHeight = Math.max(minheight, maxheight);
		CurHeight = MinHeight;
		CurRadius = MinRadius;
		CurPos = 0;
		HeightInc = true;
		RadInc = true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void show() 
	{
		double x = getEffectHolder().getLocation().getX();
		double y = getEffectHolder().getLocation().getY();
		double z = getEffectHolder().getLocation().getZ();
		try {
			getEffect().sendToPlayers(Bukkit.getOnlinePlayers(), 
					new Location(getEffectHolder().getWorld(),x+CurRadius*Math.sin(CurPos), y+CurHeight, z+CurRadius*Math.cos(CurPos)), 
					0.1F, 0.1F, 0.1F, 0.0001F, 2);
		} catch (Exception e) {	}
		
		CurPos = (CurPos+0.3)%360;
		if (HeightInc == true) CurHeight += 0.05;
		else CurHeight -= 0.05;
		if (CurHeight >= MaxHeight) HeightInc = false;
		else if (CurHeight <= MinHeight) HeightInc = true;
		if(RadInc == true) CurRadius +=0.05;
		else CurRadius -= 0.05;
		if(CurRadius >= MaxRadius) RadInc = false;
		else if (CurRadius <= MinRadius) RadInc = true;
	}

}
