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
import com.gmail.fitostpm.spellbook.spells.effects.CommonEffect;

import net.minecraft.server.v1_11_R1.EnumParticle;

public class FireResistantSpell extends Spell
{
	public FireResistantSpell()
	{
		Name = "Fire Resistance";
		Description = new ArrayList<String>(Arrays.asList(new String[] {
			"Makes target invincible to damage from fire sources.", "",
			ChatColor.BLUE + "Duration: 90", ChatColor.LIGHT_PURPLE + "Cast Range: 15", "",
			ChatColor.YELLOW + "Shift-click to target self"
		}));
		CastRange = 15;
		CanSelfTarget = true;
	}

	@Override
	public void Behavior(Player caster, LivingEntity target) 
	{
		target.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1800, 1));
		playEffect(target);
	}
	
	public void playEffect(LivingEntity target)
	{
		int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(MainClass.Instance, 
				new CommonEffect(target, EnumParticle.FLAME), 0, 2);
		Bukkit.getScheduler().scheduleSyncDelayedTask(MainClass.Instance, new Runnable(){
			@Override
			public void run() {
				Bukkit.getScheduler().cancelTask(taskId);
			}			
		}, 40);
	}
}
