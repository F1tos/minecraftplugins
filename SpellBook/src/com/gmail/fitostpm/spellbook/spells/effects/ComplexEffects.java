package com.gmail.fitostpm.spellbook.spells.effects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

public class ComplexEffects 
{
	public static void drawSphere(Location loc, EnumParticle particle, double radius, Player[] players)
	{
		if(players == null)
			players = Bukkit.getOnlinePlayers().toArray(players);
		for(double i = -90; i < 90; i=i+45)
			for(double j = 0; j < 360; j=j+60)
			{
				float x = (float) loc.getX();
				float y = (float) loc.getY();
				float z = (float) loc.getZ();

				PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, 
						(float)(x + radius * Math.sin(i) * Math.sin(j)), (float)(y + radius*Math.cos(j)), 
						(float)(z + radius * Math.cos(i) * Math.sin(j)), 0.1f, 0.1f, 0.1f, 0.0001f, 1, 1);
				for(Player p : players)
					if(p.getLocation().distance(loc) < 40)
						((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
			} 
	}

	public static void drawColoredSphere(Location loc, float red, float green, float blue, double radius, Player[] players)
	{
		if(players == null)
			players = Bukkit.getOnlinePlayers().toArray(players);
		
		for(double i = -90; i < 90; i=i+45)
			for(double j = 0; j < 360; j=j+60)
			{
				float x = (float) loc.getX();
				float y = (float) loc.getY();
				float z = (float) loc.getZ();


				PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, 
						(float)(x + radius * Math.sin(i) * Math.sin(j)), (float)(y + radius*Math.cos(j)), 
						(float)(z + radius * Math.cos(i) * Math.sin(j)), red, green, blue, 1, 0, 1);
				
				for(Player p : players)
					if(p.getLocation().distance(loc) < 40)
						((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
			} 		
	}
	
	public static void drawCircle(Location loc, EnumParticle particle, double radius, Player[] players)
	{
		if(players == null)
			players = Bukkit.getOnlinePlayers().toArray(players);

		for(double i = 1; i < 360; i = i + 11.25)
		{
			float x = (float)(loc.getX() + radius * Math.sin(i));
			float y = (float)loc.getBlockY() + 1;
			float z = (float)(loc.getZ() + radius * Math.cos(i));
			PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, x, y, z, 0.1f, 0.1f, 0.1f, 0.0001f, 1, 1);

			for(Player p : players)
				if(p.getLocation().distance(loc) < 40)
					((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
	public static void drawColoredCircle(Location loc, float red, float green, float blue, double radius, Player[] players)
	{
		if(players == null)
			players = Bukkit.getOnlinePlayers().toArray(players);

		for(double i = 1; i < 360; i = i + 11.25)
		{
			float x = (float)(loc.getX() + radius * Math.sin(i));
			float y = (float)loc.getBlockY() + 1;
			float z = (float)(loc.getZ() + radius * Math.cos(i));
			PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, x, y, z, red, green, blue, 1, 0, 1);

			for(Player p : players)
				if(p.getLocation().distance(loc) < 40)
					((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
		}
	}
}
