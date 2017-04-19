package com.gmail.fitostpm.auras.aura;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;


public class AuraSpirit extends Aura 
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

	public AuraSpirit(EnumParticle particle, double minheight, double maxheight, double minrad, 
			double maxrad)
	{

		Particle = particle;
		MinRadius = Math.min(minrad, maxrad);
		MaxRadius = Math.max(minrad, maxrad);
		MinHeight = Math.min(minheight, maxheight);
		MaxHeight = Math.max(minheight, maxheight);
		CurHeight = MinHeight;
		CurRadius = MinRadius;
		CurPos = 0;
		HeightInc = true;
		RadInc = true;
		IsColored = false;
		Red = 0.1f;
		Green = 0.1f;
		Blue = 0.1f;
	}	
	
	public AuraSpirit(EnumParticle particle, double minrad, double maxrad, double minheight, 
			double maxheight,  float red, float green, float blue)
	{
		Particle = particle;
		MinRadius = Math.min(minrad, maxrad);
		MaxRadius = Math.max(minrad, maxrad);
		MinHeight = Math.min(minheight, maxheight);
		MaxHeight = Math.max(minheight, maxheight);
		CurHeight = MinHeight;
		CurRadius = MinRadius;
		CurPos = 0;
		HeightInc = true;
		RadInc = true;
		IsColored = true;
		Red = red;
		Green = green;
		Blue = blue;	
	}
	
	public AuraSpirit(AuraSpirit a)
	{
		Particle = a.Particle;
		MinRadius = a.MinRadius;
		MaxRadius = a.MaxRadius;
		MinHeight = a.MinHeight;
		MaxHeight = a.MaxHeight;
		CurHeight = a.CurHeight;
		CurRadius = a.CurRadius;
		CurPos = a.CurPos;
		HeightInc = a.HeightInc;
		RadInc = a.RadInc;
		IsColored = a.IsColored;
		Red = a.Red;
		Green = a.Green;
		Blue = a.Blue;
	}
	
	@Override
	public void show(Location location) {
		float x = (float)location.getX();
		float y = (float)location.getY();
		float z = (float)location.getZ();
		float speed = 0.0001f;
		int count = 1;
		if(IsColored)
		{
			speed = 1;
			count = 0;
		}
		PacketPlayOutWorldParticles packet= new PacketPlayOutWorldParticles(Particle, true, 
				(float)(x + CurRadius*Math.sin(CurPos)), (float)(y + CurHeight), 
				(float)(z + CurRadius*Math.cos(CurPos)), Red, Green, Blue, speed, count, 1);
		for(Player p : location.getWorld().getPlayers())
			if(location.distance(p.getLocation()) < 50)
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		
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

	@Override
	public String toString() 
	{
		String result = "Type: Circle; Radius1: " + MinRadius + "; Radius2: " + MaxRadius + 
				"; Height1: " + MinHeight +"; Height2: " + MaxHeight +"; Color(RGB): ";
		if(IsColored)
			result += Red + " " + Green + " " + Blue;
		else
			result += "None.";
		return result;
	}

}
