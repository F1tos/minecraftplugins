package com.gmail.fitostpm.diamondshooter.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.gmail.fitostpm.diamondshooter.DSGame;
import com.gmail.fitostpm.diamondshooter.GameState;
import com.gmail.fitostpm.diamondshooter.MainClass;

public class ArrowHitListener implements Listener 
{
	@EventHandler
	public void onHit(ProjectileHitEvent event)
	{
		if(event.getEntity() instanceof Arrow && event.getEntity().getShooter() instanceof Player)
		{
			Player player = (Player)(event.getEntity().getShooter());
			for(DSGame game : MainClass.SavedGames.values())
				if((game.CurrentState == GameState.WAITING || game.CurrentState == GameState.PLAYING) 
						&& game.getPlayers().contains(player))
					event.getEntity().remove();
		}
	}

}
