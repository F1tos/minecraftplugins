package com.gmail.fitostpm.staffs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class Staff 
{
	private double Power;
	private Player Owner;
	private ItemStack Item;
	private double Damage;
	private boolean Crit = false;
	
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
	
	public Staff(Player owner, ItemStack item)
	{
		if(MainClass.CastingPlayers.containsKey(owner))
		{
			Power = MainClass.CastingPlayers.get(owner).Stop() + 0.5;
			MainClass.CastingPlayers.remove(owner);
		}
		else
			Power = 0.5;
		Owner = owner;
		Item = item;
		ItemMeta meta = item.getItemMeta();
		Damage = BaseStatsMap.get(meta.getDisplayName()).get("attackdamage") * Power;
		if(Power - 0.5 >= 0.8) Crit = true;
	};
	
	static public HashMap<String,HashMap<String,Double>> BaseStatsMap = 
			new HashMap<String,HashMap<String,Double>>();

	static public void LaunchMissile(Staff staff)
	{
		staff.getOwner().playSound(staff.getOwner().getLocation(), Sound.GHAST_FIREBALL, 1, 1);
		Projectile pr = staff.getOwner().launchProjectile(Snowball.class);
		pr.setVelocity(pr.getVelocity().
				multiply((BaseStatsMap.get(staff.getItem().getItemMeta().getDisplayName()).get("maxdistance")/46)
						*staff.Power));
		MainClass.LaunchedMissiles.put(pr, staff);
	}
	
	static public void LaunchMissilewoee(Location l, Player p, ItemStack i, Vector v)
	{
		Projectile missile = (Projectile) p.getWorld().spawnEntity(l, EntityType.SNOWBALL);
		missile.setVelocity(v);
		missile.setShooter(p);
		MainClass.LaunchedMissiles.put(missile, new Staff(p, i));
	}
	
	static public boolean isStaff(ItemStack item)
	{
		if (item.hasItemMeta() && item.getItemMeta().hasLore())
		{
			String[] lore = new String[item.getItemMeta().getLore().size()];
			(item.getItemMeta().getLore()).toArray(lore);
			int i=0;
			while(i < lore.length && (lore[i].indexOf(ChatColor.BLUE + "+") == -1 
					|| lore[i].indexOf(MainClass.Plcholders.get("attackdamage")) == -1))
				i++;
			return i<lore.length && lore[i+1].indexOf(ChatColor.GRAY + MainClass.Plcholders.get("durability") + ":") != -1 && 
					lore[i+2].indexOf(ChatColor.LIGHT_PURPLE + MainClass.Plcholders.get("chargetime") + ":") != -1; 

		}
		return false;
	}
	
	public List<StaffEnchantment> getStaffEnchantments()
	{
		String[] lore = new String[getItem().getItemMeta().getLore().size()];
		getItem().getItemMeta().getLore().toArray(lore);
		int i = 0;
		while(i < lore.length && (lore[i].indexOf(ChatColor.BLUE + "+") == -1 
				|| lore[i].indexOf(MainClass.Plcholders.get("attackdamage")) == -1))
			i++;
		i--;
		List<StaffEnchantment> enchs = new LinkedList<StaffEnchantment>();
		for(int j=0; j < i; j++)
			enchs.add(new StaffEnchantment(lore[j]));
		return enchs;
	}
	
	public boolean hasStaffEnchantment(StaffEnchantment ench)
	{
		boolean b = false;		
		List<StaffEnchantment> enchs = this.getStaffEnchantments();
		for(StaffEnchantment e : enchs)
			b = !e.isSame(ench);
		return b;		
	}
	
	public boolean addStaffEnchantment(StaffEnchantment ench)
	{
		if(!hasStaffEnchantment(ench))
		{
			ItemMeta meta = getItem().getItemMeta();
			if(!getItem().getItemMeta().hasEnchants())
			{
				getItem().addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				getItem().setItemMeta(meta);
			}

			String[] lore = new String[getItem().getItemMeta().getLore().size()];
			getItem().getItemMeta().getLore().toArray(lore);
			int i = 0;
			while(i < lore.length && (lore[i].indexOf(ChatColor.BLUE + "+") == -1 
					|| lore[i].indexOf(MainClass.Plcholders.get("attackdamage")) == -1))
				i++;
			i-=2;
			List<String> newlore = new LinkedList<String>();
			for(int j=0; j<lore.length; j++)
			{
				newlore.add(lore[j]);
				if(j==i)
					newlore.add(ChatColor.GRAY + ench.toString());
			}
			if(ench.getType().equals(StaffEnchantmentType.WITCHCRAFT))
				ChangeDamage(ench);
			if(ench.getType().equals(StaffEnchantmentType.SPEED))
				ChangeTime(ench);
			meta.setLore(newlore);
			getItem().setItemMeta(meta);
			return true;
		}	
		else 
			getOwner().sendMessage(ChatColor.RED + MainClass.Msgs.get("staffhasenchantment"));
		return false;
	}
	
	public void ChangeDamage(StaffEnchantment ench)
	{
		double damage = BaseStatsMap.get(getItem().getItemMeta().getDisplayName()).get("attackdamage");
		damage += ench.getLevel()*0.25 + 0.5;
		ItemMeta meta = getItem().getItemMeta();
		String[] lore = new String[meta.getLore().size()];
		meta.getLore().toArray(lore);
		List<String> newlore = new LinkedList<String>();
		for(int i = 0; i < lore.length; i++)
		{
			if(lore[i].indexOf(ChatColor.BLUE + "+") != -1 
					&& lore[i].indexOf(MainClass.Plcholders.get("attackdamage")) != -1)
				newlore.add(ChatColor.BLUE + "+" + damage + MainClass.Plcholders.get("attackdamage"));
			else
				newlore.add(lore[i]);
		}		
	}

	public void ChangeTime(StaffEnchantment ench)
	{
		double time = BaseStatsMap.get(getItem().getItemMeta().getDisplayName()).get("casttime");
		time -= ench.getLevel()*0.05 + 0.1;
		ItemMeta meta = getItem().getItemMeta();
		String[] lore = new String[meta.getLore().size()];
		meta.getLore().toArray(lore);
		List<String> newlore = new LinkedList<String>();
		for(int i = 0; i < lore.length; i++)
		{
			if(lore[i].indexOf(ChatColor.BLUE + "+") != -1 
					&& lore[i].indexOf(MainClass.Plcholders.get("casttime")) != -1)
				newlore.add(ChatColor.LIGHT_PURPLE + MainClass.Plcholders.get("chargetime") + ": " + time);
			else
				newlore.add(lore[i]);
		}		
	}
	
	public double Damage() {
		return Damage;
	}
	
	public boolean isCrit() {
		return Crit;
	}
	
	/*public boolean isWeak() {
		return Weak;
	}*/
}