package com.gmail.fitostpm.staffs;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.fitostpm.staffs.tasks.Cast;

import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NBTTagDouble;
import net.minecraft.server.v1_11_R1.NBTTagInt;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;

public class ItemStaff 
{	
	private double Power;
	private Player Owner;
	private ItemStack Item;
	private double Damage;
	private int Durability;
	private int MaxDurability;
	private int MaxDistance;
	private double CastTime;
	private boolean Crit = false;
	private Cast Casting;
	
	public ItemStaff(Player owner, ItemStack item)
	{
		Power = 0.1;
		Owner = owner;
		Item = item;
		Damage = getDamage(item);
		Durability = getCurrentDurability(item);
		MaxDurability = getMaxDurability(item);
		MaxDistance = getMaxDistance(item);
		CastTime = getCastTime(item);
		if(Power - 0.5 >= 0.8) Crit = true;		
	}
	
	public void StartCasting()
	{
		Power = 0.1;
		Casting = new Cast(Owner, this);
	}
	
	public void StopCasting()
	{
		MainClass._CastingPlayers.remove(Owner);
		if(Casting != null)
			Power = Casting.Stop() + 0.5;
		if(Power - 0.5 >= 0.8) Crit = true;	
	}
	
	public void LaunchMissile()
	{
		StopCasting();	
		Owner.playSound(Owner.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1, 1);
		Projectile pr = Owner.launchProjectile(Snowball.class);
		pr.setVelocity(pr.getVelocity().multiply(((double)MaxDistance/46) * Power));
		MainClass._LaunchedMissiles.put(pr, this);
		if(!Owner.getGameMode().equals(GameMode.CREATIVE))
		{
			DecreaseDurability();
			UpdateLore();
			UpdateTag("staffCurrentDurability", Durability);
			if(Durability < 0)
				DestroyStaff();
			else
				UpdateItemInHand();
		}
	}
	
	public void DecreaseDurability()
	{
		Durability--;
		if((double)MaxDurability/10 - (double)Durability >= 0 && (double)MaxDurability/10 - (double)Durability < 1)
			Owner.playSound(Owner.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, 0);
	}
	
	public void UpdateLore()
	{
		List<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(ChatColor.BLUE + "+" + Damage + " Attack Damage");
		lore.add(ChatColor.GRAY + "Durability: " + Durability + "/" + MaxDurability);
		lore.add(String.format(ChatColor.LIGHT_PURPLE + "Casting Time: %.2f", CastTime));
		ItemMeta meta = Item.getItemMeta();
		meta.setLore(lore);
		Item.setItemMeta(meta);
	}
	
	public static boolean isStaff(ItemStack item)
	{		
		net.minecraft.server.v1_11_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(item);
		return nmsItemStack.hasTag() && nmsItemStack.getTag().hasKey("staffType");
	}
	
	public void UpdateTag(String name, Object value)
	{
		net.minecraft.server.v1_11_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(Item);
		NBTTagCompound tag = nmsItem.getTag();
		
		if(value instanceof Double)
			tag.set(name, new NBTTagDouble((double) value));
		else if(value instanceof Integer)
			tag.set(name, new NBTTagInt((int) value));
		
		nmsItem.setTag(tag);
		Item = CraftItemStack.asBukkitCopy(nmsItem);
	}
	
	private void DestroyStaff()
	{
		RemoveOne(Owner.getInventory(), Item);
	}
	
	private void RemoveOne(Inventory inv, ItemStack item)
	{
		int slot = getSlot(inv,item);
		int amount = inv.getItem(slot).getAmount();
		if(amount == 1)
			inv.setItem(slot, new ItemStack(Material.AIR));
		else
		{
			item.setAmount(amount-1);
			inv.setItem(slot, item);
		}	
	}
	
	private int getSlot(Inventory inv, ItemStack item)
	{
		int i;
		for(i=1; i<65; i++)
		{
			item.setAmount(i);
			if(inv.contains(item))
				return inv.first(item);
		}
		return 0;
	}

	
	private void UpdateItemInHand()
	{
		Owner.getInventory().setItemInMainHand(Item);
	}
	
	//NBTTag data getters
	private double getDamage(ItemStack item){
		NBTTagCompound tag = CraftItemStack.asNMSCopy(item).getTag();
		if(tag.hasKey("staffDamage"))
			return tag.getFloat("staffDamage");
		else
			return 0;
	}
	private double getCastTime(ItemStack item)
	{
		NBTTagCompound tag = CraftItemStack.asNMSCopy(item).getTag();
		if(tag.hasKey("staffCastTime"))
			return tag.getFloat("staffCastTime");
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
	private int getMaxDistance(ItemStack item)
	{
		NBTTagCompound tag = CraftItemStack.asNMSCopy(item).getTag();
		if(tag.hasKey("staffMaxDistance"))
			return tag.getInt("staffMaxDistance");
		else
			return 0;
	}
	
	//setters and getters
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
	public double getCastTime() {
		return CastTime;
	}
	public void setCastTime(double castTime) {
		CastTime = castTime;
	}
	public int getMaxDistance() {
		return MaxDistance;
	}
	public void setMaxDistance(int maxDistance) {
		MaxDistance = maxDistance;
	}
	public Cast getCast(){
		return Casting;
	}
}
