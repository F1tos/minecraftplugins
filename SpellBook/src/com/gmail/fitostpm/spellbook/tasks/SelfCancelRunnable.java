package com.gmail.fitostpm.spellbook.tasks;

import org.bukkit.Bukkit;

public abstract class SelfCancelRunnable implements Runnable 
{
	protected int TaskId;

	public int getTaskId() { return TaskId;	}

	public void setTaskId(int taskId) { TaskId = taskId; }
	
	public void Cancel() { Bukkit.getScheduler().cancelTask(TaskId); }
}
