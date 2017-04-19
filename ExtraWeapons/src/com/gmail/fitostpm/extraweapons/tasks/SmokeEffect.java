package com.gmail.fitostpm.extraweapons.tasks;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Projectile;

import com.gmail.fitostpm.extraweapons.MainClass;

import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

public class SmokeEffect implements Runnable 
{

	@Override
	public void run() 
	{
		for(Projectile p : MainClass.FlyingExplGrenades)
		{
			float x = (float) p.getLocation().getX();
			float y = (float) p.getLocation().getY();
			float z = (float) p.getLocation().getZ();
			PacketPlayOutWorldParticles packet = 
					new PacketPlayOutWorldParticles(EnumParticle.SMOKE_LARGE, true, x, y, z, 0.1f, 0.1f, 0.1f, 0.0001f, 10, 1);
			((CraftPlayer) p.getShooter()).getHandle().playerConnection.sendPacket(packet);
		}		
	}
}
