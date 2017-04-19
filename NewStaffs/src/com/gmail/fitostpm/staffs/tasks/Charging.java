package com.gmail.fitostpm.staffs.tasks;

import org.bukkit.entity.Player;

import com.gmail.fitostpm.staffs.MainClass;

public class Charging implements Runnable 
{
	@Override
	public void run() 
	{
		for(Player p : MainClass._CastingPlayers.keySet())
			MainClass._CastingPlayers.get(p).getCast().Run();
	}
}
