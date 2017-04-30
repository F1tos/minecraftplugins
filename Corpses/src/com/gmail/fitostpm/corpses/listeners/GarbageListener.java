package com.gmail.fitostpm.corpses.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.gmail.fitostpm.corpses.MainClass;
import com.gmail.fitostpm.corpses.tasks.FireballDirector;
import com.gmail.fitostpm.corpses.tasks.GarbageRunner;
import com.gmail.fitostpm.corpses.tasks.TrajectorySelector;

public class GarbageListener implements Listener 
{
	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if(player.getInventory().getItemInMainHand().getType().equals(Material.STRING)
				&& event.getAction().equals(Action.RIGHT_CLICK_AIR))
		{
			GarbageRunner runner = new GarbageRunner(player.launchProjectile(Snowball.class));
			runner.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(MainClass.Instance, runner, 0, 1));
		}
		else if(player.getInventory().getItemInMainHand().getType().equals(Material.STRING)
				&& event.getAction().equals(Action.LEFT_CLICK_AIR))
		{
			double mid = 0;
			for(double d : MainClass.Results)
				mid += d;
			player.sendMessage("g = " + mid/(double)MainClass.Results.size() + " units/ticks (" + MainClass.Results.size() + " results total)");
		}
		else if(player.getInventory().getItemInMainHand().getType().equals(Material.IRON_INGOT)
				&& event.getAction().equals(Action.RIGHT_CLICK_AIR))
		{
			TrajectorySelector ts = new TrajectorySelector(player);
			Bukkit.getScheduler().scheduleSyncRepeatingTask(MainClass.Instance, ts, 0, 1);
		}
		else if(player.getInventory().getItemInMainHand().getType().equals(Material.IRON_INGOT)
				&& event.getAction().equals(Action.LEFT_CLICK_AIR))
		{
			FireballDirector fd = new FireballDirector(player);
			fd.setTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(MainClass.Instance, fd, 0, 1));
		}
	}
}
