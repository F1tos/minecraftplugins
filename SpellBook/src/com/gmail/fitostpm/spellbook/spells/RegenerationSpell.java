package com.gmail.fitostpm.spellbook.spells;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.fitostpm.spellbook.MainClass;
import com.gmail.fitostpm.spellbook.spells.effects.CommonColoredEffect;

public class RegenerationSpell extends Spell
{
	public RegenerationSpell()
	{
		Name = "Regeneration";
		Description = new ArrayList<String>(Arrays.asList(new String[] {
			"Increases regeneration of target.", "",
			ChatColor.BLUE + "Amount: 0.8 per second",
			ChatColor.BLUE + "Duration: 10", ChatColor.LIGHT_PURPLE + "Cast Range: 15", "",
			ChatColor.YELLOW + "Shift-click to target self"
		}));
		CastRange = 15;
		CanSelfTarget = true;
	}

	@Override
	public void Behavior(Player caster, LivingEntity target) 
	{
		target.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 3));
		playEffect(target);
	}
	
	public void playEffect(LivingEntity target)
	{
		int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(MainClass.Instance, 
				new CommonColoredEffect(target, 0, -1, 1), 0, 2);
		Bukkit.getScheduler().scheduleSyncDelayedTask(MainClass.Instance, new Runnable(){
			@Override
			public void run() {
				Bukkit.getScheduler().cancelTask(taskId);
			}			
		}, 40);
	}
}
