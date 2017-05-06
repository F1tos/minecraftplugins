package com.gmail.fitostpm.spellbook.spells;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public abstract class TrajectoryMissileSpell extends Spell 
{
	public Vector GravityVector;
	
	public abstract void Behavior(Player caster);
}
