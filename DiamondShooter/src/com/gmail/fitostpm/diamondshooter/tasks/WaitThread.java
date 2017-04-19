package com.gmail.fitostpm.diamondshooter.tasks;

import org.bukkit.Bukkit;

import com.gmail.fitostpm.diamondshooter.GameState;
import com.gmail.fitostpm.diamondshooter.MainClass;

public class WaitThread implements Runnable 
{
	private String Name;
	
	public WaitThread(String name)
	{
		Name = name;
	}
	
	@Override
	public void run() 
	{
		while(MainClass.SavedGames.get(Name).CurrentState == GameState.WAITING)
		{
			Bukkit.getLogger().info("WaitThread is working!");
			if(MainClass.SavedGames.get(Name).tick() == 0)
				MainClass.SavedGames.get(Name).CurrentState = GameState.PLAYING;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) { }
		}
		
		new RoundStarter(MainClass.SavedGames.get(Name));

	}

}
