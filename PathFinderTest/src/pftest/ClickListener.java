package pftest;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;


public class ClickListener implements Listener 
{
	private JavaPlugin plugin;
	
	public static ZombieCreep c = null;
	
	public static SnowmanTower s = null;
	
	public ClickListener(JavaPlugin plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR))
		{
			ItemStack item = player.getInventory().getItemInMainHand();
			if(!item.equals(null) && item.getType().equals(Material.DIAMOND))
			{
				WaveStarter wave = new WaveStarter("Zombie", MainClass.Path, 1);
				wave.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, wave, 0, 20));
			}
			if(!item.equals(null) && item.getType().equals(Material.EMERALD))
			{
				for(ChatColor c : ChatColor.values())
				{
					player.sendMessage(c + c.getName());
				}
			}
			
		}
		else if(event.getHand().equals(EquipmentSlot.HAND) && event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			ItemStack item = player.getInventory().getItemInMainHand();
			if(!item.equals(null) && item.getType().equals(Material.CLAY_BRICK))
			{
				Location loc = event.getClickedBlock().getLocation();
				loc.setX(loc.getBlockX()+0.5);
				loc.setY(loc.getY()+1);
				loc.setZ(loc.getBlockZ()+0.5);
				s = new SnowmanTower(loc);
				//s.InitTargetFinder();
			}			
		}
	}

}
