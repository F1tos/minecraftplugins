package com.gmail.fitostpm.staffs.tasks;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Projectile;

import com.gmail.fitostpm.staffs.MainClass;

import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

public class CritEffect implements Runnable {

	@Override
	public void run() 
	{
		for(Projectile p : MainClass._LaunchedMissiles.keySet())
		{
			if(MainClass._LaunchedMissiles.get(p).isCrit())
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
