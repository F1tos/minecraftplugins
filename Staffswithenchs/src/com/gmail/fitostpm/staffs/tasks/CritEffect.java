package com.gmail.fitostpm.staffs.tasks;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Projectile;

import com.gmail.fitostpm.staffs.MainClass;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class CritEffect implements Runnable {

	@Override
	public void run() 
	{
		for(Projectile p : MainClass.LaunchedMissiles.keySet())
		{
			if(MainClass.LaunchedMissiles.get(p).isCrit())
			{
				float x = (float) p.getLocation().getX();
				float y = (float) p.getLocation().getY();
				float z = (float) p.getLocation().getZ();
				PacketPlayOutWorldParticles packet = 
						new PacketPlayOutWorldParticles(EnumParticle.CRIT, true, x, y, z, 0.1f, 0.1f, 0.1f, 0.0001f, 10, 1);
				((CraftPlayer) p.getShooter()).getHandle().playerConnection.sendPacket(packet);
			}
		}
		
	}

}
