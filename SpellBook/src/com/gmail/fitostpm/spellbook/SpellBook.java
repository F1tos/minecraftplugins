package com.gmail.fitostpm.spellbook;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.Inventory;

import com.gmail.fitostpm.spellbook.spells.EnumSpell;
import com.gmail.fitostpm.spellbook.spells.Spell;

import net.minecraft.server.v1_11_R1.ItemStack;
import net.minecraft.server.v1_11_R1.NBTTagCompound;

public class SpellBook 
{
	private String OwnerName;
	private List<Spell> Spells;
	
	private SpellBook(String ownerName, List<Spell> spells)
	{
		OwnerName = ownerName;
		Spells = spells;
	}	
	
	public Inventory createMenu()
	{
		List<org.bukkit.inventory.ItemStack> buttons = new ArrayList<org.bukkit.inventory.ItemStack>();
		Spells.forEach(item -> buttons.add(item.getButton()));
		Inventory menu = Bukkit.createInventory(Bukkit.getPlayer(OwnerName), 
				((buttons.size() / 9) + 1) * 9, 
				OwnerName + "'s spell book.");
		menu.addItem(buttons.toArray(new org.bukkit.inventory.ItemStack[buttons.size()]));
		return menu;
	}
	
	public static SpellBook asSpellBook(org.bukkit.inventory.ItemStack item)
	{
		return asSpellBook(CraftItemStack.asNMSCopy(item));
	}
	
	public static SpellBook asSpellBook(ItemStack item)
	{
		if(isSpellBook(item))
		{
			NBTTagCompound tag = item.getTag();
			String owner = tag.getString("SpellBookOwner");
			if(tag.hasKey("SpellBookSpells"))
			{
				NBTTagCompound scrollsTag = tag.getCompound("SpellBookSpells");
				List<Spell> scrolls = new ArrayList<Spell>();
				for(String s: scrollsTag.c())
					if(EnumSpell.getByTag(s) != null)
						scrolls.add(EnumSpell.getByTag(s).getSpell());
				return new SpellBook(owner, scrolls);				
			}
			return new SpellBook(owner, new ArrayList<Spell>());
		}
		return null;
	}
	
	public static boolean isSpellBook(org.bukkit.inventory.ItemStack item)
	{
		return isSpellBook(CraftItemStack.asNMSCopy(item));
	}
	
	public static boolean isSpellBook(ItemStack item)
	{		
		return item.hasTag() && item.getTag().hasKey("SpellBookOwner");
	}
}
