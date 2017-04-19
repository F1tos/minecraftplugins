package com.gmail.fitostpm.diamondshooter.tasks;

import org.bukkit.Bukkit;

import com.gmail.fitostpm.diamondshooter.GameState;
import com.gmail.fitostpm.diamondshooter.MainClass;

public class VoteThread implements Runnable 
{
	private String Name;
	
	public VoteThread(String name)
	{
		Name = name;
	}
	
	@Override
	public void run() 
	{		
		if(MainClass.SavedGames.get(Name).CurrentState == GameState.VOTING)
		{
			Bukkit.getLogger().info("VoteThread is working!");
			MainClass.SavedGames.get(Name).voteforstart.run();
		}
		else
			Bukkit.getScheduler().getPendingTasks();
	}

}
