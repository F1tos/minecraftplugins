package com.gmail.fitostpm.diamondshooter.tasks;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.gmail.fitostpm.diamondshooter.DSGame;
import com.gmail.fitostpm.diamondshooter.GameState;
import com.gmail.fitostpm.diamondshooter.MainClass;

import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class PlayThread implements Runnable 
{
	private String Name;
	
	public PlayThread(String name)
	{
		Name = name;
	}

	@Override
	public void run() 
	{
		while(MainClass.SavedGames.get(Name).CurrentState == GameState.PLAYING)
		{
			Bukkit.getLogger().info("PlayThread is working!");
			DSGame game = MainClass.SavedGames.get(Name);
			float x = (float) game.getCurrentTarget().getLocation().getX();
			float y = (float) game.getCurrentTarget().getLocation().getY();
			float z = (float) game.getCurrentTarget().getLocation().getZ();
			PacketPlayOutWorldParticles packet = 
					new PacketPlayOutWorldParticles(game.getParticle(), true, x, y, z, 
							0.1f, 0.1f, 0.1f, 0.0001f, 1, 1);
			for(Player p : game.getPlayers())
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
			game.checkWinner();
			if(game.getCurrentTarget().isOnGround())
			{
				game.CurrentState = GameState.WAITING;
				Random r = new Random();
				game.removeTarget();
				game.setSecondsToNextRound(r.nextInt(6) + 3);
				for(Player player : game.getPlayers())
				{
					player.sendMessage("There is no winner in this round...");
					player.sendMessage("Next round will start in 3 to 8 seconds");
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) { }
		}
	}

}
