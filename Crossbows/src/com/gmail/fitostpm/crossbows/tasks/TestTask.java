package com.gmail.fitostpm.crossbows.tasks;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.gmail.fitostpm.crossbows.MainClass;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;

public class TestTask implements Runnable {

	@Override
	public void run() {
		for(Player p : MainClass.players.keySet())
		{
			ResetBar(p);
			double i = MainClass.players.get(p);
			if(i <= 1)
			{
				ShowBar(p, i);
				MainClass.players.put(p, i+0.01);				
			}
			else
			{
				MainClass.players.remove(p);
			}
		}

	}
	public void ShowBar(Player p, double status)
	{		
		PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES,
				new ChatComponentText("times"), 1, 1, 1);
		PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
				new ChatComponentText(ChatColor.GREEN + "Reload: " + CreateStatus(status)));
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(title);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(subtitle);
	}
	
	public String CreateStatus(double status)
	{
		double percent = status*20;
		String result = ChatColor.GREEN + "";
		int i = 0;
		while (i++ < percent)
			result += "|";
		result += ChatColor.RESET;
		while (i++ < 20)
			result += "|";
		return result;
	}
	
	public void ClearBar(Player p)
	{
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(
				PacketPlayOutTitle.EnumTitleAction.CLEAR, new ChatComponentText(""), 1, 1, 1));
	}

	public void ResetBar(Player p)
	{
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(
				PacketPlayOutTitle.EnumTitleAction.RESET, new ChatComponentText(""), 1, 1, 1));
	}

}
