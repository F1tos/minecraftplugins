package pftest;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ClickOnEntityListener implements Listener
{
	@EventHandler
	public void onClick(PlayerInteractEntityEvent e)
	{
		e.getPlayer().sendMessage("" + e.getRightClicked().getClass().toString());		
	}
}
