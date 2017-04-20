package com.gmail.fitostpm.spellbook.spells.effects;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

public class CommonColoredEffect implements Runnable 
{
	private Entity Target;
	
	private float Red;
	private float Green;
	private float Blue;
	
	private int CurI;
	private double CurY;
	
	public CommonColoredEffect(Entity target, float red, float green, float blue)
	{
		Target = target;
		
		Red = red;
		Green = green;
		Blue = blue;
		
		CurY = target.getLocation().getY();
		CurI = 0;
	}

	@Override
	public void run() 
	{
		PacketPlayOutWorldParticles effect1 = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, 
				(float)(Target.getLocation().getX() + .75*Math.sin(CurI)), (float)CurY, 
				(float)(Target.getLocation().getZ() + .75*Math.cos(CurI)), Red, Green, Blue, 1, 0, 1);
		PacketPlayOutWorldParticles effect2 = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, 
				(float)(Target.getLocation().getX() + .75*Math.sin((CurI+180)%360)), (float)CurY, 
				(float)(Target.getLocation().getZ() + .75*Math.cos((CurI+180)%360)), Red, Green, Blue, 1, 0, 1);
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
