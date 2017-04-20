package auras;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListeners implements Listener
{
	public HashMap<Player, Aura> AuraMap;
	
	public QuitListeners(HashMap<Player, Aura> map)
	{
		AuraMap = map;
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent event)
	{
		for(Player p : AuraMap.keySet())
			if (p.equals(event.getPlayer()))
				AuraMap.remove(p);
	}
}
