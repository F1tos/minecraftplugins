package com.gmail.fitostpm.spellbook.spells;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.gmail.fitostpm.spellbook.MainClass;
import com.gmail.fitostpm.spellbook.tasks.FireballDirector;

public class FireballSpell extends PointTargetSpell 
{
	public FireballSpell()
	{
		Name = "Fireball";
		Description = new ArrayList<String>(Arrays.asList(new String[] {
			"Throws fireball to specified point.", "",
			ChatColor.BLUE + "Damage: 5"
		}));
	}
	@Override
	public void Behavior(Player caster) 
	{
		FireballDirector fd = new FireballDirector(caster);
		fd.setTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(MainClass.Instance, fd, 0, 1));	
		caster.getLocation().getWorld().playSound(caster.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1, 1);	
	}

}
