package com.gmail.fitostpm.spellbook.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class EntitySelector 
{
	public static List<Entity> getNearbyEntities(Location location, Class<? extends Entity> type, double dx, double dy, double dz, Collection<Entity> except)
	{
		if(except == null)
			except = new LinkedList<Entity>();
		List<Entity> result = new LinkedList<Entity>();
		for(Entity e : location.getWorld().getNearbyEntities(location, dx, dy, dz))
			if(e.getClass().equals(type) && !except.contains(e))
				result.add(e);
		return result;
	}

}
