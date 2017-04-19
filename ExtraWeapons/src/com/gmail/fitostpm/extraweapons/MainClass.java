package com.gmail.fitostpm.extraweapons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.fitostpm.extraweapons.util.TryParse;
import com.gmail.fitostpm.extraweapons.listeners.HitListener;
import com.gmail.fitostpm.extraweapons.tasks.SmokeEffect;
import com.gmail.fitostpm.extraweapons.garbage.GarbageListener;
import com.gmail.fitostpm.extraweapons.listeners.ClickListener;

import net.md_5.bungee.api.ChatColor;

public class MainClass extends JavaPlugin
{

	public static List<Projectile> FlyingExplGrenades = new ArrayList<Projectile>();
	
	public static HashMap<Player, Integer> Helders = new HashMap<Player, Integer>();
			
	public static MainClass Instance;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("grenade") && sender instanceof Player && ((Player)sender).isOp())
		{
			switch (args.length)
			{
			case 0:
				GiveExplGrenade((Player) sender, 1);
				return true;
			case 1:
				if(TryParse.toInt(args[0]))
				{
					GiveExplGrenade((Player) sender, Integer.parseInt(args[0]));
					return true;
				}
			case 2:
				if(TryParse.toInt(args[1]))
				{
					for(Player p : getServer().getOnlinePlayers())
						if(p.getDisplayName().equals(args[0]))
						{
							GiveExplGrenade(p, Integer.parseInt(args[1]));
							return true;
						}
				}
				
			}
		}	
		else if(cmd.getName().equalsIgnoreCase("web") && sender instanceof Player && ((Player)sender).isOp())
		{
			switch (args.length)
			{
			case 0:
				GiveSnareWeb((Player) sender, 1);
				return true;
			case 1:
				if(TryParse.toInt(args[0]))
				{
					GiveSnareWeb((Player) sender, Integer.parseInt(args[0]));
					return true;
				}
			case 2:
				if(TryParse.toInt(args[1]))
				{
					for(Player p : getServer().getOnlinePlayers())
						if(p.getDisplayName().equals(args[0]))
						{
							GiveSnareWeb(p, Integer.parseInt(args[1]));
							return true;
						}
				}
				
			}
		}		
		return false;				
	}
	
	public void onEnable()
	{
		Instance = this;
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new SmokeEffect(), 0, 2);
		
		getServer().getPluginManager().registerEvents(new ClickListener(), this);
		getServer().getPluginManager().registerEvents(new GarbageListener(), this);
		getServer().getPluginManager().registerEvents(new HitListener(), this);
	}
	
	public void onDisable()
	{
		
	}	
	
	public void GiveExplGrenade(Player player, int count)
	{	
		String grenadename = ChatColor.DARK_RED + "Осколочная граната";
		getServer().dispatchCommand(player, "minecraft:give @p firework_charge " + count + " 0 {display:{Name:" + grenadename + "},ench:[{id:16,lvl:1}],HideFlags:1}");		
	}
	
	public void GiveSnareWeb(Player player, int count)
	{
		String webname = ChatColor.GREEN + "Паучья сеть";
		getServer().dispatchCommand(player, "minecraft:give @p string " + count + " 0 {display:{Name:" + webname + "},ench:[{id:16,lvl:1}],HideFlags:1}");		
		
	}
	
	
	
	
	
	

}
