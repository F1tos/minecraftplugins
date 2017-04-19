package com.gmail.fitostpm.auras.tasks;

import org.bukkit.entity.Player;

import com.gmail.fitostpm.auras.aura.Aura;

public class AuraShower implements Runnable 
{
	private Player EffectHolder;
	private Aura effect;

	public AuraShower(Player effectholder, Aura effect)
	{
		EffectHolder = effectholder;
		this.effect = effect;
	}
	
	@Override
	public void run() 
	{
		effect.show(EffectHolder.getLocation());
	}

}
