package com.gmail.fitostpm.spellbook.spells;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class LightningStrikeSpell extends Spell
{
	public LightningStrikeSpell()
	{
		Name = "Lightning Strike";
		Description = new ArrayList<String>(Arrays.asList(new String[] {
				"Summons lightning strike at target.", "",
				ChatColor.LIGHT_PURPLE + "Cast Range: 15"
			}));
		CastRange = 15;
	}

	@Override
	public void Behavior(Player caster, LivingEntity target) 
	{
		target.getWorld().strikeLightning(target.getLocation());	
	}
}
