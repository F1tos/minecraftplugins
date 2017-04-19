package com.gmail.fitostpm.auras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.fitostpm.auras.aura.Aura;
import com.gmail.fitostpm.auras.aura.AuraCircle;
import com.gmail.fitostpm.auras.aura.AuraSpirit;
import com.gmail.fitostpm.auras.aura.EnumAura;
import com.gmail.fitostpm.auras.tasks.AuraShower;
import com.gmail.fitostpm.auras.util.TryParse;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class MainClass extends JavaPlugin
{
	public static HashMap<Player,List<Aura>> AuraMap;
	public static HashMap<Player, Aura> Editing;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		/*if(cmd.getName().equalsIgnoreCase("addaura") && sender.hasPermission("auras.add"))
		{
			Player player = null;
			EnumParticle particle = null;
			Aura effect = null;
			switch(args.length)
			{
			case 6:
				player = Bukkit.getPlayer(args[0]);
				if(player != null && args[1].equalsIgnoreCase("circle") 
						&& TryParse.toEnumParticle(args[2].toUpperCase()) 
						&& TryParse.toDouble(args[3]) && TryParse.toDouble(args[4]))
				{ 
					particle = EnumParticle.valueOf(args[2].toUpperCase());
					double radius = Double.parseDouble(args[3]);
					double height = Double.parseDouble(args[4]);
					char axis = args[5].charAt(0);
					effect = new AuraCircle(particle, radius, height, axis);
					effect.setTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, 
							new AuraShower(player, effect), 0, 2));
					if(!AuraMap.containsKey(player))
						AuraMap.put(player, new ArrayList<Aura>());
					AuraMap.get(player).add(effect);
					return true;
				}		
				break;
			case 7:
				player = Bukkit.getPlayer(args[0]);
				if(player != null && args[1].equalsIgnoreCase("spirit") 
						&& TryParse.toEnumParticle(args[2].toUpperCase()) 
						&& TryParse.toDouble(args[3]) && TryParse.toDouble(args[4])
						&& TryParse.toDouble(args[5]) && TryParse.toDouble(args[6]))
				{
					particle = EnumParticle.valueOf(args[2].toUpperCase());
					double minrad = Double.parseDouble(args[3]);
					double maxrad = Double.parseDouble(args[4]);
					double minheight = Double.parseDouble(args[5]);
					double maxheight = Double.parseDouble(args[6]);
					effect = new AuraSpirit(particle, minrad, maxrad, minheight, maxheight);
					effect.setTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, 
							new AuraShower(player, effect), 0, 2));
					if(!AuraMap.containsKey(player))
						AuraMap.put(player, new ArrayList<Aura>());
					AuraMap.get(player).add(effect);
					return true;					
				}
				break;				
			}
		}*/

		if(cmd.getName().equalsIgnoreCase("addaura") && sender.hasPermission("auras.add") && args.length > 1)
		{
			List<String> execParams = new ArrayList<String>();
			List<String> cmdParams = new ArrayList<String>();
			for(int i = 0; i < args.length - 2; i++)
				execParams.add(args[i]);
			cmdParams.add(args[args.length-2]);
			cmdParams.add(args[args.length-1]);	
			ExecuteAddCommand(sender, execParams, cmdParams);
		}
		
		
		else if(cmd.getName().equalsIgnoreCase("rmaura"))
		{
			Player player = null;
			int id = 0;
			if(args.length == 1 && sender instanceof Player && TryParse.toInt(args[0]))
			{
				player = (Player) sender;
				id = Integer.parseInt(args[0]);
			}
			else if(args.length == 2 && TryParse.toInt(args[1]))
			{
				if(!(sender instanceof Player) 
						|| ( sender instanceof Player && (Bukkit.getPlayer(args[0]).equals((Player)sender) 
								|| sender.hasPermission("auras.rm"))))
				{
					player = Bukkit.getPlayer(args[0]);
					id = Integer.parseInt(args[1]);
				}
				else
					return false;
			}
			else
				return false;

			if(!AuraMap.containsKey(player))
				player.sendMessage(ChatColor.RED + "You have no any effects.");
			else
			{ 
				if(id > AuraMap.get(player).size())
					player.sendMessage(ChatColor.RED + "There is no effect with such id.");
				else
				{
					Bukkit.getScheduler().cancelTask(AuraMap.get(player).get(id).getTaskId());
					AuraMap.get(player).remove(id);
					return true;
				}
			}				
		}
		
		else if(cmd.getName().equalsIgnoreCase("displayauras") && sender instanceof Player)
		{
			if(args.length == 0)
			{
				Player player = (Player) sender;
				if(!AuraMap.containsKey(player) || AuraMap.get(player).size() == 0)
					player.sendMessage(ChatColor.GREEN + "You have no any effects.");
				else
				{
					for(int i = 0; i < AuraMap.get(player).size(); i++)
					{
						player.sendMessage(ChatColor.GREEN + "" + i + " - " 
								+ AuraMap.get(player).get(i).toString());
					}
				}
				return true;
			}				
		}
		
		/*else if(cmd.getName().equalsIgnoreCase("addaurac") 
				&& sender.hasPermission("auras.add.colored"))
		{
			Player player = null;
			EnumParticle particle = null;
			Aura effect = null;
			switch(args.length)
			{
			case 9:
				player = Bukkit.getPlayer(args[0]);
				if(player != null && args[1].equalsIgnoreCase("circle") 
						&& TryParse.toColorableEnumParticle(args[2].toUpperCase()) 
						&& TryParse.toDouble(args[3]) && TryParse.toDouble(args[4])
						&& TryParse.toFloat(args[6]) && TryParse.toFloat(args[7])
						&& TryParse.toFloat(args[8]))
				{ 
					particle = EnumParticle.valueOf(args[2].toUpperCase());
					double radius = Double.parseDouble(args[3]);
					double height = Double.parseDouble(args[4]);
					char axis = args[5].charAt(0);
					float red = Float.parseFloat(args[6]);
					float green = Float.parseFloat(args[7]);
					float blue = Float.parseFloat(args[8]);
					effect = new AuraCircle(particle, radius, height, axis, red, green, blue);
					effect.setTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, 
							new AuraShower(player, effect), 0, 2));
					if(!AuraMap.containsKey(player))
						AuraMap.put(player, new ArrayList<Aura>());
					AuraMap.get(player).add(effect);
					return true;
				}
				break;
			case 10:
				player = Bukkit.getPlayer(args[0]);
				if(player != null && args[1].equalsIgnoreCase("spirit") 
						&& TryParse.toColorableEnumParticle(args[2].toUpperCase()) 
						&& TryParse.toDouble(args[3]) && TryParse.toDouble(args[4])
						&& TryParse.toDouble(args[5]) && TryParse.toDouble(args[6])
						&& TryParse.toFloat(args[7]) && TryParse.toFloat(args[8])
						&& TryParse.toFloat(args[9]))
				{
					particle = EnumParticle.valueOf(args[2].toUpperCase());
					double minrad = Double.parseDouble(args[3]);
					double maxrad = Double.parseDouble(args[4]);
					double minheight = Double.parseDouble(args[5]);
					double maxheight = Double.parseDouble(args[6]);
					float red = Float.parseFloat(args[7]);
					float green = Float.parseFloat(args[8]);
					float blue = Float.parseFloat(args[9]);
					effect = new AuraSpirit(particle, minrad, maxrad, minheight, maxheight, 
							red, green, blue);
					effect.setTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, 
							new AuraShower(player, effect), 0, 2));
					if(!AuraMap.containsKey(player))
						AuraMap.put(player, new ArrayList<Aura>());
					AuraMap.get(player).add(effect);
					return true;					
				}
				break;		
			}
		}*/
		
		else if(cmd.getName().equalsIgnoreCase("particlenames") && sender instanceof Player)
		{
			StringBuilder sb = new StringBuilder();
			for (EnumParticle e : EnumParticle.values())
				sb.append(e.name().toLowerCase() + " ");
			((Player)sender).sendMessage(sb.toString());
		}
		
		return false;		
	}
	@Override
	public void onEnable()
	{
		AuraMap = new HashMap<Player, List<Aura>>();
		Editing = new HashMap<Player, Aura>();
	}
	
	public void onDisable()
	{
		
	}
	
	public void ExecuteAddCommand(CommandSender sender, List<String> execParams, List<String> cmdParams)
	{
		Player player = Bukkit.getPlayer(cmdParams.get(0));
		if(player == null)
		{
			sender.sendMessage(ChatColor.RED + "Error: no such player \"" + cmdParams.get(0)+ "\"");
			return;
		}
		if(TryParse.toEnumAura(cmdParams.get(1).toUpperCase()))
		{
			sender.sendMessage(ChatColor.RED + "Error: no such aura \"" + cmdParams.get(1)+ "\"");
			return;
		}				
		Aura aura;
		try {
			aura = EnumAura.valueOf(cmdParams.get(1).toUpperCase()).getclass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			sender.sendMessage(ChatColor.RED + "Error: UnknownError");
			return;
		}
		
		String msg = CheckExecParams(execParams);
		if(!msg.equals(""))
		{
			sender.sendMessage(msg);
			return;
		}
		
		for(String s : execParams)
		{
			if(s.equals("-n") || s.equals("-new"))
				Clear(player);
			if(s.equals("-c") || s.equals("-colored"))
			{
				aura.setColored(true);
				aura.setRed(1);
				aura.setGreen(0);
				aura.setBlue(0);
				aura.setParticle(EnumParticle.REDSTONE);
			}
		}		
		aura.setTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, 
				new AuraShower(player, aura), 0, 2));
		if(!AuraMap.containsKey(player))
			AuraMap.put(player, new ArrayList<Aura>());
		AuraMap.get(player).add(aura);
		
	}
	
	public void Clear(Player player)
	{
		if(AuraMap.containsKey(player))
		{
			for(Aura a : AuraMap.get(player))	
				Bukkit.getScheduler().cancelTask(a.getTaskId());
			AuraMap.remove(player);
		}
	}
	
	public String CheckExecParams(List<String> params)
	{
		for(String s : params)
		{
			if(!(s.equals("-n") || s.equals("-new") || s.equals("-e") || s.equals("-edit")
					|| s.equals("-c") || s.equals("-colored")))
				return ChatColor.RED + "Error: Unknown parameter: \"" + s + "\"";
		}		
		return "";		
	}
	
	public void StartEditMode(Player player, Aura aura, int id)
	{
		Editing.put(player, aura);
	}

}
