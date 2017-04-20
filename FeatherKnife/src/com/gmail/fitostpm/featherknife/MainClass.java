package com.gmail.fitostpm.featherknife;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.fitostpm.featherknife.listeners.KnifeRightClickListener;

public class MainClass extends JavaPlugin 
{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("giveknife"))
		{
			if(args.length == 0 && sender instanceof Player)
			{
				GiveKnife((Player)sender);
				return true;
			}
			else if (args.length == 1)
			{
				for(Player p : getServer().getOnlinePlayers())
					if(p.getName().equals(args[0]))
					{
						GiveKnife(p);
						return true;
					}
			}
				
		}
		return false;
	}
	
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(new KnifeRightClickListener(), this);
	}

	@Override
	public void onDisable()
	{
		
	}
	
	public void GiveKnife(Player player)
	{
		ItemStack item = new ItemStack(Material.FEATHER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Feather Knife");	
		item.setItemMeta(meta);
		player.getWorld().dropItem(player.getLocation(), item);
	}
}
