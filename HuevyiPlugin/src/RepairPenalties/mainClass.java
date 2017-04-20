package RepairPenalties;

import java.util.HashMap;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class mainClass extends JavaPlugin
{
	public static HashMap<HumanEntity, InventoryOpenEvent> Viewers = new HashMap<HumanEntity, InventoryOpenEvent>();
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(new ClickOnAnvilListener(), this);	
		getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
	}
	
	public void onDisable()
	{
		
	}
}
