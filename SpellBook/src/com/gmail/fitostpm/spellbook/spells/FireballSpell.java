package com.gmail.fitostpm.spellbook.spells;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.gmail.fitostpm.spellbook.MainClass;
import com.gmail.fitostpm.spellbook.tasks.FireballDirector;
import com.gmail.fitostpm.spellbook.tasks.SelfCancelTaskScheduler;

public class FireballSpell extends TrajectoryMissileSpell 
{
	public FireballSpell()
	{
		Name = "Fireball";
		Description = new ArrayList<String>(Arrays.asList(new String[] {
			"Throws fireball to specified point.", "",
			ChatColor.BLUE + "Damage: 5"
		}));
		
		GravityVector = new Vector(0, -0.035, 0);
	}
	
	@Override
	public void Behavior(Player caster) 
	{
		SelfCancelTaskScheduler.ScheduleRepeatingTask(MainClass.Instance, new FireballDirector(caster, GravityVector), 0, 1);
		caster.getLocation().getWorld().playSound(caster.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1, 1);	
	}

}
