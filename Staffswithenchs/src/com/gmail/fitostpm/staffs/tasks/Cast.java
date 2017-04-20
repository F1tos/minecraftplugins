package com.gmail.fitostpm.staffs.tasks;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.fitostpm.staffs.MainClass;
import com.gmail.fitostpm.staffs.Staff;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;

public class Cast
{
	private double CurCastTime = 0;
	private double MaxCastTime;
	private Player Caster;
	
	public Cast(Player player, ItemStack item)
	{
		MaxCastTime = Staff.BaseStatsMap.get(item.getItemMeta().getDisplayName()).get("chargetime");
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
		MainClass.CastingPlayers.remove(Caster);
		PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
				new ChatComponentText(""),0,0,0);
		PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
				new ChatComponentText(""),0,0,0);
		((CraftPlayer)Caster).getHandle().playerConnection.sendPacket(title);
		((CraftPlayer)Caster).getHandle().playerConnection.sendPacket(subtitle);
		return getStatus();
	}
	
	public void ShowBar(double status)
	{		
		PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
				new ChatComponentText(""),0,3,0);
		PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
				new ChatComponentText(CreateBar(status)),0,3,0);
		((CraftPlayer)Caster).getHandle().playerConnection.sendPacket(title);
		((CraftPlayer)Caster).getHandle().playerConnection.sendPacket(subtitle);
	}
	
	public String CreateBar(double status)
	{
		ChatColor color = GetColor(status);
		int edge = Math.min(50, (int) (50*status));
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		for(int i=0; i<edge; i++)
			sb1.append('|');
		for(int i=0; i<50-edge; i++)
			sb2.append('|');
		return color + sb1.toString() + ChatColor.RESET + sb2.toString();
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
