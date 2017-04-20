package com.gmail.fitostpm.auras.aura;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class AuraCircle extends Aura
{
	public double Radius;
	public double Height;
	public char Axis;
	
	public AuraCircle()
	{
		Radius = 0.7;
		Height = 2.5;
		Axis = 'y';
		Particle = EnumParticle.FLAME;
		IsColored = false;
	}
	
	public AuraCircle(EnumParticle particle, double radius,
			double height, char axis)
	{
		Particle = particle;
		Radius = radius;
		Height = height;
		IsColored = false;
		switch(axis)
		{
		case 'y':
		case 'z':
			Axis = axis;
			break;
		case 'x':
		default:
			Axis = 'x';			
		}
		Red = 0.1f;
		Green = 0.1f;
		Blue = 0.1f;
	}
	public AuraCircle(EnumParticle particle, double radius,
			double height, char axis, float red, float green, float blue)
	{
		Particle = particle;
		Radius = radius;
		Height = height;
		IsColored = true;
		Red = red;
		Green = green;
		Blue = blue;		
		switch(axis)
		{
		case 'y':
		case 'z':
			Axis = axis;
			break;
		case 'x':
		default:
			Axis = 'x';			
		}
	}
	
	public AuraCircle(AuraCircle a)
	{
		Particle = a.Particle;
		Radius = a.Radius;
		Height = a.Height;
		Axis = a.Axis;
		IsColored = a.IsColored;
		Red = a.Red;
		Green = a.Green;
		Blue = a.Blue;			
	}

	@Override
	public void show(Location location) 
	{
		float x = (float) location.getX();
		float y = (float) (location.getY()+Height);
		float z = (float) location.getZ();
		float speed = 0.0001f;
		int count = 1;
		if(IsColored)
		{
			speed = 1;
			count = 0;
		}
		for(double i=0; i<360; i=i+11.25)
		{
			PacketPlayOutWorldParticles packet = null;
			switch(Axis)
			{
			case 'x': 
				packet = new PacketPlayOutWorldParticles(Particle, true, x, (float) (y+Radius*Math.sin(i)), 
						(float) (z+Radius*Math.cos(i)), Red, Green, Blue, speed, count, 1);
				for(Player p : location.getWorld().getPlayers())
					if(location.distance(p.getLocation()) < 50)
						((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
				break;
				
			case 'y':
				packet = new PacketPlayOutWorldParticles(Particle, true, (float) (x+Radius*Math.sin(i)), 
						(float) (y+Height), (float) (z+Radius*Math.cos(i)), Red, Green, Blue, speed, count, 1);
				for(Player p : location.getWorld().getPlayers())
					if(location.distance(p.getLocation()) < 50)
						((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
				break;
				
			case 'z':
				packet = new PacketPlayOutWorldParticles(Particle, true, (float) (x+Radius*Math.sin(i)), 
						(float) (y+Radius*Math.cos(i)), z, Red, Green, Blue, speed, count, 1);
				for(Player p : location.getWorld().getPlayers())
					if(location.distance(p.getLocation()) < 50)
						((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
				break;
			}
		}
				
	}
	@Override
	public String toString() 
	{
		String result = "Type: Circle; Radius: " + Radius + "; Height: " + Height +"; Axis: " 
				+ Axis +"; Color(RGB): ";
		if(IsColored)
			result += Red + " " + Green + " " + Blue;
		else
			result += "None.";
		return result;
	}

}
