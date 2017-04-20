package com.gmail.fitostpm.spellbook.spells.effects;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

public class CommonEffect implements Runnable 
{
	private Entity Target;
	private EnumParticle Particle;
	
	private int CurI;
	private double CurY;
	
	public CommonEffect(Entity target, EnumParticle particle)
	{
		Target = target;		
		Particle = particle;
		
		CurY = target.getLocation().getY();
		CurI = 0;
	}

	@Override
	public void run() 
	{
		PacketPlayOutWorldParticles effect1 = new PacketPlayOutWorldParticles(Particle, true, 
				(float)(Target.getLocation().getX() + .75*Math.sin(CurI)), (float)CurY, 
				(float)(Target.getLocation().getZ() + .75*Math.cos(CurI)), 0.1f, 0.1f, 0.1f, 0.0001f, 1, 1);
		PacketPlayOutWorldParticles effect2 = new PacketPlayOutWorldParticles(Particle, true, 
				(float)(Target.getLocation().getX() + .75*Math.sin((CurI+180)%360)), (float)CurY, 
				(float)(Target.getLocation().getZ() + .75*Math.cos((CurI+180)%360)), 0.1f, 0.1f, 0.1f, 0.0001f, 1, 1);
		for(Entity e : Target.getNearbyEntities(40, 40, 40))
			if(e instanceof Player)
			{
				((CraftPlayer)e).getHandle().playerConnection.sendPacket(effect1);
				((CraftPlayer)e).getHandle().playerConnection.sendPacket(effect2);
			}
		if(Target instanceof Player)
		{
			((CraftPlayer)Target).getHandle().playerConnection.sendPacket(effect1);
			((CraftPlayer)Target).getHandle().playerConnection.sendPacket(effect2);			
		}
		
		CurI = (CurI+1)%360;
		CurY += 0.1;
	}
}
