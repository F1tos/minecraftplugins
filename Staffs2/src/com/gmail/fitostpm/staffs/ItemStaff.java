package com.gmail.fitostpm.staffs;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_11_R1.NBTTagCompound;

import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;

public class ItemStaff 
{	
	private double Power;
	private Player Owner;
	private ItemStack Item;
	private double Damage;
	private int Durability;
	private int MaxDurability;
	private boolean Crit = false;
	
	public ItemStaff(Player owner, ItemStack item)
	{
		if(MainClass.CastingPlayers.containsKey(owner))
		{
			Power = MainClass.CastingPlayers.get(owner).Stop() + 0.5;
			MainClass.CastingPlayers.remove(owner);
		}
		else
			Power = 0.1;
		Owner = owner;
		Item = item;
		Damage = getDamage(item);
		Durability = getCurrentDurability(item);
		MaxDurability = getMaxDurability(item);
		if(Power - 0.5 >= 0.8) Crit = true;		
	}
	
	public static boolean isStaff(ItemStack item)
	{		
		return CraftItemStack.asNMSCopy(item).getTag().hasKey("staff");
	}
	
	private double getDamage(ItemStack item)
	{
		NBTTagCompound tag = CraftItemStack.asNMSCopy(item).getTag();
		if(tag.hasKey("staffDamage"))
			return tag.getFloat("staffDamage");
		else
			return 0;
	}
	
	private int getCurrentDurability(ItemStack item)
	{
		NBTTagCompound tag = CraftItemStack.asNMSCopy(item).getTag();
		if(tag.hasKey("staffCurrentDurability"))
			return tag.getInt("staffCurrentDurability");
		else
			return 0;
	}
	
	private int getMaxDurability(ItemStack item)
	{
		NBTTagCompound tag = CraftItemStack.asNMSCopy(item).getTag();
		if(tag.hasKey("staffMaxDurability"))
			return tag.getInt("staffMaxDurability");
		else
			return 0;
	}
	
	public double getPower() {
		return Power;
	}
	public void setPower(double power) {
		Power = power;
	}
	public Player getOwner() {
		return Owner;
	}
	public void setOwner(Player owner) {
		Owner = owner;
	}
	public ItemStack getItem() {
		return Item;
	}
	public void setItem(ItemStack item) {
		Item = item;
	}
	public double getDamage() {
		return Damage;
	}
	public void setDamage(double damage) {
		Damage = damage;
	}
	public boolean isCrit() {
		return Crit;
	}
	public void setCrit(boolean crit) {
		Crit = crit;
	}

	public int getDurability() {
		return Durability;
	}

	public void setDurability(int durability) {
		Durability = durability;
	}

	public int getMaxDurability() {
		return MaxDurability;
	}
}
