package com.gmail.fitostpm.zrtd.creep;

import org.bukkit.Location;

import net.minecraft.server.v1_10_R1.Entity;

public abstract class Creep 
{
	protected Entity ActualEntity;
	protected Location[] Path;
	protected int CurrentPathPoint;
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Creep)
		{
			Creep c = (Creep) obj;
			return ActualEntity.equals(c.ActualEntity);
		}
		return false;		
	}
}
