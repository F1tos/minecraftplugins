package com.gmail.fitostpm.staffs.tasks;

import org.bukkit.entity.Player;

import com.gmail.fitostpm.staffs.MainClass;

public class Charging implements Runnable 
{
	@Override
	public void run() 
	{
		for(Player p : MainClass.CastingPlayers.keySet())
			MainClass.CastingPlayers.get(p).Run();
	}
}
