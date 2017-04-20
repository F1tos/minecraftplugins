package com.gmail.fitostpm.zrtd.tasks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.gmail.fitostpm.zrtd.game.Game;

public abstract class Vote implements Runnable
{
	protected List<Player> AgreedPlayers;
	protected Game game;
	protected int ItsId;
	protected int secondsLeft;

	public Vote(Game game, Player player)
	{
		this.game = game;
		AgreedPlayers = new ArrayList<Player>();
		agree(player);
	}
	
	@Override
	public void run() 
	{
		if(secondsLeft < 0)
			deny();
		else
			secondsLeft--;
	}

	public void setID(int id){
		ItsId = id;
	}
	
	public abstract void agree(Player player);
	public abstract void disagree(Player player);
	public abstract void accept();
	public abstract void deny();
}
