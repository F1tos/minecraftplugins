package com.gmail.fitostpm.spellbook.tasks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class SelfCancelTaskScheduler 
{
	public static int ScheduleRepeatingTask(Plugin plugin, SelfCancelRunnable task, long delay, long interval)
	{
		int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, task, delay, interval);
		task.setTaskId(taskId);
		return taskId;
	}

}
