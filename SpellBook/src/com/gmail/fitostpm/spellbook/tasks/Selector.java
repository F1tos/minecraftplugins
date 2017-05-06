package com.gmail.fitostpm.spellbook.tasks;

import com.gmail.fitostpm.spellbook.spells.Spell;

public abstract class Selector extends SelfCancelRunnable
{
	protected Spell CastingSpell;
	
	public abstract void Cast();
	
	public abstract void Stop();
}
