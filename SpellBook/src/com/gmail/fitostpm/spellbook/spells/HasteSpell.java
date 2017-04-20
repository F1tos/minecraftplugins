package com.gmail.fitostpm.spellbook.spells;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.fitostpm.spellbook.MainClass;
import com.gmail.fitostpm.spellbook.spells.effects.CommonColoredEffect;


public class HasteSpell extends Spell
{
	public HasteSpell()
	{
		Name = "Haste";
		Description = new ArrayList<String>(Arrays.asList(new String[] {
			"Increases speed of target.", "",
			ChatColor.BLUE + "Duration: 60", ChatColor.LIGHT_PURPLE + "Cast Range: 15", "",
			ChatColor.YELLOW + "Shift-click to target self"
		}));
		CastRange = 15;
		CanSelfTarget = true;
	}

	@Override
	public void Behavior(Player caster, LivingEntity target) 
	{
		target.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1200, 2));
		playEffect(target);
	}
	
	public void playEffect(LivingEntity target)
	{
		int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(MainClass.Instance, 
				new CommonColoredEffect(target, -1, 1, 1), 0, 2);
		Bukkit.getScheduler().scheduleSyncDelayedTask(MainClass.Instance, new Runnable(){
			@Override
			public void run() {
				Bukkit.getScheduler().cancelTask(taskId);
			}			
		}, 40);
	}

	@Override
	public ItemStack getButton() 
	{
		ItemStack button = new ItemStack(Material.EMPTY_MAP);
		ItemMeta meta = button.getItemMeta();
		meta.setDisplayName(Name);
		meta.setLore(Description);
		button.setItemMeta(meta);
		return button;
	}
}
