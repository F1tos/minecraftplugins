package RepairPenalties;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class ClickOnAnvilListener implements Listener 
{
	@EventHandler
	public void onOpen(InventoryOpenEvent event)
	{
		if(event.getInventory().getType().equals(InventoryType.ANVIL))
			mainClass.Viewers.put(event.getPlayer(), event);
	}
}
