package com.gmail.fitostpm.corpses.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Projectile;

import com.gmail.fitostpm.corpses.MainClass;

public class GarbageRunner implements Runnable 
{
	private Projectile Target;
	private double HorVelocityStart;
	private double TicksLapsed;
	private int Id;
	
	public GarbageRunner(Projectile target)
	{
		Target = target;
		TicksLapsed = 0;
		HorVelocityStart = target.getVelocity().getY();
	}

	@Override
	public void run() 
	{
		if(Target.getVelocity().getY() > 0.0001)
		{
			TicksLapsed++;			
		}
		else
		{
			Bukkit.getServer().getPlayer("Fitos").sendMessage("g = " 
					+ HorVelocityStart/TicksLapsed + " units/tick or " 
					+ HorVelocityStart/(TicksLapsed/20) + " units/sec");
			MainClass.Results.add(HorVelocityStart/TicksLapsed);
			Bukkit.getScheduler().cancelTask(Id);
		}
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}
}