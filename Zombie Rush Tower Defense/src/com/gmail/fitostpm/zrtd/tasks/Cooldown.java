package com.gmail.fitostpm.zrtd.tasks;

import com.gmail.fitostpm.zrtd.tower.Tower;

public class Cooldown implements Runnable 
{
	private Tower tower;
	
	public Cooldown(Tower t)
	{
		tower = t;
	}

	@Override
	public void run() 
	{
		tower.OnCooldown = false;
	}

}
