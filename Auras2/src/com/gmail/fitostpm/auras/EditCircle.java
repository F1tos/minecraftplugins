package com.gmail.fitostpm.auras;

import org.bukkit.entity.Player;

import com.gmail.fitostpm.auras.aura.Aura;
import com.gmail.fitostpm.auras.aura.AuraCircle;

import net.md_5.bungee.api.ChatColor;

public class EditCircle extends EditMode
{

	public EditCircle(Player p, Aura a, int id) {
		super(p, a, id);
	}
	
	public void EditRadius(String param, double value)
	{
		switch(param)
		{
		case "-a":
			((AuraCircle)aura).Radius += value;
			break;
		case "-d":
			if(((AuraCircle)aura).Radius < value)
				player.sendMessage(ChatColor.RED + "Error: You cannot deduct value more than radius.");
			else
				((AuraCircle)aura).Radius -= value;
			break;
		case "-s":
		default:
			((AuraCircle)aura).Radius = value;				
		}
	}
	
	public void EditHeight(String param, double value)
	{
		switch(param)
		{
		case "-a":
			((AuraCircle)aura).Height += value;
			break;
		case "-d":
			((AuraCircle)aura).Height -= value;
			break;
		case "-s":
		default:
			((AuraCircle)aura).Height = value;				
		}
	}

	public void message()
	{
		player.sendMessage(ChatColor.GOLD + "========Edit Mode========");
		player.sendMessage(ChatColor.GOLD + "Aura id: " + ChatColor.AQUA + auraid);
		switch(aura.getClass().getName())
		{
		case "AuraCircle":
			AuraCircle ac = (AuraCircle) aura;
			player.sendMessage(ChatColor.GOLD + "  Radius: " + ChatColor.AQUA + ac.Radius 
					+ ChatColor.GRAY + ". To change radius type /eradius [-a|-d|-s] <new_value>.");
			player.sendMessage(ChatColor.GOLD + "  Height: " + ChatColor.AQUA + ac.Height 
					+ ChatColor.GRAY + ". To change radius type /eheight [-a|-d|-s] <new_value>.");
			player.sendMessage(ChatColor.GOLD + "  Particle: " + ChatColor.AQUA + ac.getParticle().toString().toLowerCase() 
					+ ChatColor.GRAY + ". To change radius type /eparticle <new_value>.");
			player.sendMessage(ChatColor.GOLD + "  Axis: " + ChatColor.AQUA + ac.Axis 
					+ ChatColor.GRAY + ". To change radius type /eaxis <new_value>.");	
			if(ac.isColored())			
			{
				player.sendMessage(ChatColor.GOLD + "  Color: " + ChatColor.RED + ac.getRed() 
						+  ChatColor.GREEN + ac.getGreen() + ChatColor.BLUE +  ac.getBlue() + ChatColor.GRAY 
						+ ". To change radius type /ecolor <new_red_value> <new_green_value> <new_blue_value>.");	
			}				
			break;
		}
		player.sendMessage(ChatColor.GOLD + "To save changes type " + ChatColor.GREEN + "/emsave");
		player.sendMessage(ChatColor.GOLD + "To exit edit mode without saving changes types " 
				+ ChatColor.GREEN + "/emexit");
	}
}
