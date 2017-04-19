package com.gmail.fitostpm.staffs.tasks;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.fitostpm.staffs.ItemStaff;
import com.gmail.fitostpm.staffs.MainClass;

import net.minecraft.server.v1_11_R1.ChatComponentText;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;

public class Cast
{
	private double CurCastTime = 0;
	private double MaxCastTime;
	private Player Caster;
	
	public Cast(Player player, ItemStaff staff)
	{
		MaxCastTime = staff.getCastTime();
		Caster = player;
	}

	public void Run() 
	{
		Caster.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20,3,true));
		Caster.setSneaking(true);
		ShowBar(getStatus());
		if(MaxCastTime > CurCastTime)
			CurCastTime += 0.1;
	}

	public double getCurCastTime() {
		return CurCastTime;
	}

	public double getMaxCastTime() {
		return MaxCastTime;
	}
	
	public double getStatus()
	{
		return CurCastTime/MaxCastTime;
	}
	
	public double Stop()
	{
		Caster.removePotionEffect(PotionEffectType.SLOW);
		Caster.setSneaking(false);
		((CraftPlayer)Caster).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(
				PacketPlayOutTitle.EnumTitleAction.RESET, new ChatComponentText(""), 1, 1, 1));
		((CraftPlayer)Caster).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(
				new ChatComponentText(""), (byte) 2));
		return getStatus();
	}
	
	public void ShowBar(double status)
	{		
		((CraftPlayer)Caster).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(
				PacketPlayOutTitle.EnumTitleAction.RESET, new ChatComponentText(""), 1, 1, 1));
		
		PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES,
				new ChatComponentText("times"), 1, 10, 1);
		PacketPlayOutChat subtitle = new PacketPlayOutChat(new ChatComponentText(CreateBar(status)), (byte)2);
		((CraftPlayer)Caster).getHandle().playerConnection.sendPacket(title);
		((CraftPlayer)Caster).getHandle().playerConnection.sendPacket(subtitle);
	}
	
	public String CreateBar(double status)
	{
		ChatColor color = GetColor(status);
		int edge = Math.min(15, (int) (15*status));
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		for(int i=0; i<edge; i++)
			sb1.append('█'); //•█
		for(int i=0; i<15-edge; i++)
			sb2.append('█');
		return ChatColor.AQUA + MainClass.Plcholders.get("power") + ": " + color + sb1.toString() + ChatColor.RESET + sb2.toString();
	}
	
	public ChatColor GetColor(double status)
	{
		if(status < 0.2)
			return ChatColor.GREEN;
		else if(status < 0.4)
			return ChatColor.YELLOW;
		else if(status < 0.6)
			return ChatColor.GOLD;
		else if(status < 0.8)
			return ChatColor.RED;
		else
			return ChatColor.DARK_RED;
		
	}

}
