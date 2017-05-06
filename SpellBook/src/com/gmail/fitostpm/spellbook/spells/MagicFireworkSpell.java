package com.gmail.fitostpm.spellbook.spells;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.gmail.fitostpm.spellbook.MainClass;
import com.gmail.fitostpm.spellbook.tasks.FireworkDirector;
import com.gmail.fitostpm.spellbook.tasks.SelfCancelTaskScheduler;

public class MagicFireworkSpell extends TrajectoryMissileSpell 
{
	public MagicFireworkSpell()
	{
		Name = "Magic Firework";
		Description = new ArrayList<String>(Arrays.asList(new String[] {
			"Throws magic firework, which damages with touch and bounces from block.", 
			"Firework explodes after 4 seconds.", "",
			ChatColor.BLUE + "Touch damage: 1",
			ChatColor.BLUE + "Explode damage: 5",
		}));
		
		GravityVector = new Vector(0, -0.012, 0);
	}
	
	@Override
	public void Behavior(Player caster) 
	{
		SelfCancelTaskScheduler.ScheduleRepeatingTask(MainClass.Instance, new FireworkDirector(caster, GravityVector), 0, 1);
		caster.getLocation().getWorld().playSound(caster.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 1, 5);
	}

}
