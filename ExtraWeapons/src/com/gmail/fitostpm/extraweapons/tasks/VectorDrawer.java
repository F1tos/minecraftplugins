package com.gmail.fitostpm.extraweapons.tasks;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

public class VectorDrawer implements Runnable 
{
	private Player player;
	private double distance;
	
	public VectorDrawer(Player player, double distance)
	{
		this.player = player;
		this.distance = distance;
	}
	
	private Vector getLeftVectorFromYawAndPitch(Player player)
	{
		double yaw = (player.getLocation().getYaw() + 75) * Math.PI / 180;
		return new Vector(Math.cos(yaw), 0, Math.sin(yaw));
	}
	
	private Vector getRightVectorFromYawAndPitch(Player player)
	{
		double yaw = (player.getLocation().getYaw() + 105) * Math.PI / 180;
		return new Vector(Math.cos(yaw), 0, Math.sin(yaw));
	}
	
	@Override
	public void run() 
	{
		Vector leftvector = getLeftVectorFromYawAndPitch(player);
		Location l = player.getLocation();
		//player.sendMessage("-----------vector-----------");
		//player.sendMessage(String.format("vector: %.2f; %.2f; %.2f", vector.getX(), vector.getY(), vector.getZ()));
		for(int d = 0; d < distance; d++)
		{			
			l.add(leftvector);
			float x = (float) l.getX();
			float y = (float) player.getLocation().getY() + 1;
			float z = (float) l.getZ();
			//player.sendMessage(String.format("loc: %.2f; %.2f; %.2f", l.getX(), l.getY(), l.getZ()));
			PacketPlayOutWorldParticles packet = 
					new PacketPlayOutWorldParticles(EnumParticle.FLAME, true, x, y, z, 0.1f, 0.1f, 0.1f, 0.0001f, 1, 1);
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
		
		Vector rightvector = getRightVectorFromYawAndPitch(player);
		l = player.getLocation();
		//player.sendMessage("-----------vector-----------");
		//player.sendMessage(String.format("vector: %.2f; %.2f; %.2f", vector.getX(), vector.getY(), vector.getZ()));
		for(int d = 0; d < distance; d++)
		{			
			l.add(rightvector);
			float x = (float) l.getX();
			float y = (float) player.getLocation().getY() + 1;
			float z = (float) l.getZ();
			//player.sendMessage(String.format("loc: %.2f; %.2f; %.2f", l.getX(), l.getY(), l.getZ()));
			PacketPlayOutWorldParticles packet = 
					new PacketPlayOutWorldParticles(EnumParticle.FLAME, true, x, y, z, 0.1f, 0.1f, 0.1f, 0.0001f, 1, 1);
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
		
		player.sendMessage("angle l-r: " + leftvector.angle(rightvector));
		player.sendMessage("angle r-l: " + rightvector.angle(leftvector));
	}

}
