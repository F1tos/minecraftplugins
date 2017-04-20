package cosyserver;

import org.bukkit.plugin.java.JavaPlugin;

import cosyserver.TorchPlacement;

public class mainClass extends JavaPlugin{

	public void onEnable()
	{
		//registering events
		getServer().getPluginManager().registerEvents(new TorchPlacement(), this);		
	}
	
	public void onDisable()
	{
		
	}
}
