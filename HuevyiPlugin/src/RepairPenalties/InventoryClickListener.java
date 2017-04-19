package RepairPenalties;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryClickListener implements Listener 
{
	private JavaPlugin Plugin;
	
	public InventoryClickListener (JavaPlugin plugin)
	{
		Plugin = plugin;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event)
	{
		ClickType click = event.getClick();
		if((click.equals(ClickType.LEFT) || click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_LEFT) ||
			click.equals(ClickType.SHIFT_RIGHT) || click.equals(ClickType.MIDDLE) ||click.equals(ClickType.LEFT))
			&& mainClass.Viewers.containsKey(event.getWhoClicked()) && 
			event.getCurrentItem().getType().equals(Material.DIAMOND_SWORD))
		{
			Bukkit.getScheduler().runTask(Plugin, new Runnable(){

				@Override
				public void run() {
					event.setCancelled(true);
					mainClass.Viewers.get(event.getWhoClicked()).setCancelled(true);
					mainClass.Viewers.remove(event.getWhoClicked());
				}
				
			});
		}
		else
		{
			Bukkit.broadcastMessage("click - " + click.toString());
			Bukkit.broadcastMessage("item - " + event.getCurrentItem().toString());
		}
		/*if(event.getCursor().getType().equals(Material.DIAMOND_SWORD) && 
				mainClass.Viewers.keySet().contains(event.getWhoClicked()))
		{
			event.setCancelled(true);
			mainClass.Viewers.get(event.getWhoClicked()).setCancelled(true);
			mainClass.Viewers.remove(event.getWhoClicked());
		}	*/		
	}
}
