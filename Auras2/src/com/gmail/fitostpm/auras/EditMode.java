package com.gmail.fitostpm.auras;

import org.bukkit.entity.Player;

import com.gmail.fitostpm.auras.aura.Aura;
import com.gmail.fitostpm.auras.aura.AuraCircle;
import com.gmail.fitostpm.auras.aura.AuraSpirit;


public abstract class EditMode 
{
	public Player player;
	public Aura aura;
	public Aura backup;
	public int auraid;
	
	public EditMode(Player p, Aura a, int id)
	{
		player = p;
		aura = a;
		switch(aura.getClass().getName())
		{
		case "AuraCircle":
			backup = new AuraCircle((AuraCircle)a);
			break;
		case "AuraSpirit":
			backup = new AuraSpirit((AuraSpirit)a);
			break;
		}
		auraid = id;
	}
	
	public void save()
	{
		MainClass.Editing.remove(player);
	}
	
	public void exit()
	{
		aura = backup;
		MainClass.Editing.remove(player);		
	}
	
	public abstract void message();
}
