package com.gmail.fitostpm.spellbook.tasks;

import com.gmail.fitostpm.spellbook.spells.Spell;

public abstract class Selector
{
	protected Spell CastingSpell;
	
	public abstract void Cast();
	
	public abstract void Stop();
	

	protected int TaskId;

	public int getTaskId() {
		return TaskId;
	}

	public void setTaskId(int taskId) {
		TaskId = taskId;
	}
}
