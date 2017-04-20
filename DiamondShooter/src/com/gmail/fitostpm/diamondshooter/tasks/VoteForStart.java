package com.gmail.fitostpm.diamondshooter.tasks;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.Player;

import com.gmail.fitostpm.diamondshooter.GameState;
import com.gmail.fitostpm.diamondshooter.MainClass;

import net.md_5.bungee.api.ChatColor;

public class VoteForStart
{
	private String Name;
	private int SecondsLeft = 30;
	private int NeededPlayers;
	private List<Player> AgreedPlayers = new LinkedList<Player>();
	
	public VoteForStart(String name)
	{
		Name = name;
		NeededPlayers = MainClass.SavedGames.get(Name).getPlayers().size();
	}
	
	public void run()
	{
		SecondsLeft--;
		if(SecondsLeft == 0)
			cancel();
	}
	
	public void accept(Player player)
	{
		if(!AgreedPlayers.contains(player) && MainClass.SavedGames.get(Name).getPlayers().contains(player))
		{
			AgreedPlayers.add(player);
			for(Player p : MainClass.SavedGames.get(Name).getPlayers())
				p.sendMessage(ChatColor.GREEN + "Player " + ChatColor.BLUE + player.getDisplayName() 
					+ ChatColor.GREEN + " has voted for start! (" + AgreedPlayers.size() + "/" 
					+ NeededPlayers + ")");			
			SecondsLeft = 30;
			if(NeededPlayers < 5 && AgreedPlayers.size() == NeededPlayers)
				ready();
			else if(NeededPlayers >= 5 && AgreedPlayers.size() + 1 == NeededPlayers)
				ready();			
		}
	}
	
	public void ready()
	{
		MainClass.SavedGames.get(Name).startGame();
	}
	
	public void cancel()
	{
		for(Player player : MainClass.SavedGames.get(Name).getPlayers())
			player.sendMessage(ChatColor.RED + "Not enough votes to start a game!");
		MainClass.SavedGames.get(Name).CurrentState = GameState.NOTSTARTED;
	}
	
}
