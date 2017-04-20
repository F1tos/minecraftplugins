package com.gmail.fitostpm.diamondshooter.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.gmail.fitostpm.diamondshooter.DSGame;
import com.gmail.fitostpm.diamondshooter.GameState;
import com.gmail.fitostpm.diamondshooter.MainClass;

public class PlayerMoveListener implements Listener 
{
	@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		for(DSGame game : MainClass.SavedGames.values())
		{
			if((game.CurrentState == GameState.WAITING || game.CurrentState == GameState.PLAYING)
					&& game.getPlayers().contains(event.getPlayer())
					&& game.getPlayersPosition(event.getPlayer()).distance(event.getPlayer().getLocation()) > 0.1)
				event.getPlayer().teleport(game.getPlayersPosition(event.getPlayer()));	
		}		
	}
}
