package com.gmail.fitostpm.spellbook.spells;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Spell 
{
	protected String Name;
	protected List<String> Description;
	protected Material Icon = Material.EMPTY_MAP;
	protected double CastRange;

	public ItemStack getButton() 
	{
		ItemStack button = new ItemStack(Icon);
		ItemMeta meta = button.getItemMeta();
		meta.setDisplayName(Name);
		meta.setLore(Description);
		button.setItemMeta(meta);
		return button;		
	}

}
