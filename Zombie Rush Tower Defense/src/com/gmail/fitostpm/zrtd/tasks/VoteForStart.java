package com.gmail.fitostpm.zrtd.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.fitostpm.zrtd.game.Game;
import com.gmail.fitostpm.zrtd.game.GameState;

import net.md_5.bungee.api.ChatColor;

public class VoteForStart extends Vote
{

	public VoteForStart(Game game, Player player) {
		super(game, player);
	}

	@Override
	public void agree(Player player) 
	{
		if(!AgreedPlayers.contains(player))
		{
			AgreedPlayers.add(player);
			int c = AgreedPlayers.size();
			for(Player p : AgreedPlayers)
				p.sendMessage(ChatColor.BLUE + player.getName() + " has voted for start (" + c + "/" 
						+ game.CountOfPlayers + ")");
			if(c != 1 && (c == game.CountOfPlayers || c > 5 && c == game.CountOfPlayers-1))
				accept();
			secondsLeft = 30;
		}		
	}
	
	public void disagree(Player player)
	{
		for(Player p : AgreedPlayers)
			p.sendMessage(ChatColor.RED + "Player " + player.getName() + " disagree to start the game.");
		game.setVote(null);
		game.setState(GameState.NOTSTARTED);
		Bukkit.getScheduler().cancelTask(ItsId);
	}
	
	@Override
	public void accept() 
	{	
		for(Player player : AgreedPlayers)
			player.sendMessage(ChatColor.BLUE + "Game is starting...");
	}

	@Override
	public void deny() 
	{	
		for(Player player : AgreedPlayers)
			player.sendMessage(ChatColor.RED + "Not enough votes for start.");
		game.setVote(null);
		game.setState(GameState.NOTSTARTED);
		Bukkit.getScheduler().cancelTask(ItsId);
	}

}
