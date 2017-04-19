package megaplugin;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginListener implements Listener
{
	public void onLogin(PlayerLoginEvent event)
	{
		public LoginListener(LoginPlugin plugin) {
	        plugin.getServer().getPluginManager().registerEvents(this, plugin);
	    }
	 
	    @EventHandler
	    public void normalLogin(PlayerLoginEvent event) {
	        // Some code here
	    }
	}

}
