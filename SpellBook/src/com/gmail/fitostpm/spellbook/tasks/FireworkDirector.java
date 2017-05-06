package com.gmail.fitostpm.spellbook.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.gmail.fitostpm.spellbook.util.ExtraMath;

import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;


public class FireworkDirector extends SelfCancelRunnable
{
	private Player Caster;
	private Location CurLoc;
	private Vector CurVelocity;
	private int TicksLapsed;
	private final int MaxTicks;
	
	private Vector Gravity;
	
	public FireworkDirector(Player caster, Vector gravity) 
	{
		Caster = caster;
		CurLoc = caster.getEyeLocation();
		CurVelocity = (ExtraMath.getVectorOutOfYawAndPitch(Caster));//.multiply(3);
		TicksLapsed = 0;
		MaxTicks = 4 * 20;
		Gravity = gravity;
	}
	
	@Override
	public void run() 
	{
		if(TicksLapsed++ < MaxTicks)
		{
			Block prevBlock = CurLoc.getBlock();
			CurLoc.add(CurVelocity);
			CurVelocity.add(Gravity);
			draw(CurLoc);
			if(!prevBlock.equals(CurLoc.getBlock()) && !CurLoc.getBlock().getType().equals(Material.AIR))
			{
				Caster.sendMessage("BOUNCE!!!");
				CurLoc.getWorld().playSound(CurLoc, Sound.ENTITY_FIREWORK_LAUNCH, 3, 1);
				if(CurLoc.getBlockX() != prevBlock.getX())
				{
					CurVelocity.setX(CurVelocity.getX() *-.9);	
					Caster.sendMessage("X changed!!!");
				}
				else if(CurLoc.getBlockY() != prevBlock.getY())
				{
					CurVelocity.setY(CurVelocity.getY() *-.9);
					Caster.sendMessage("Y changed!!!");					
				}
				else
				{
					CurVelocity.setZ(CurVelocity.getZ() *-.9);
					Caster.sendMessage("Z changed!!!");
				}
			}
		}
		else
		{
			CurLoc.getWorld().playSound(CurLoc, Sound.ENTITY_FIREWORK_LARGE_BLAST, 10, 1);
			Bukkit.getScheduler().cancelTask(TaskId);			
		}		
	}
	
	private void draw(Location location)
	{

		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FIREWORKS_SPARK, true, (float)location.getX(), 
				(float)location.getY(), (float)location.getZ(), 0.1f, 0.1f, 0.1f, 0.0001f, 10, 1);

		for(Player p : Bukkit.getOnlinePlayers())
			if(p.getLocation().distance(location) < 40)
				((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
		
	}
}
