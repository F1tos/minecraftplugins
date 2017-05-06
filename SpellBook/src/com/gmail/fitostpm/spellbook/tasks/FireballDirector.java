package com.gmail.fitostpm.spellbook.tasks;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.gmail.fitostpm.spellbook.spells.effects.ComplexEffects;
import com.gmail.fitostpm.spellbook.util.CollectionsHelper;
import com.gmail.fitostpm.spellbook.util.EntitySelector;
import com.gmail.fitostpm.spellbook.util.ExtraMath;

import net.minecraft.server.v1_11_R1.EnumParticle;

public class FireballDirector extends SelfCancelRunnable
{
	private Player Caster;
	private Location CurLoc;
	private Vector CurVelocity;
	
	private Vector Gravity;
	
	public FireballDirector(Player caster, Vector gravity)
	{
		Caster = caster;
		CurLoc = caster.getEyeLocation();
		CurVelocity = ExtraMath.getVectorOutOfYawAndPitch(Caster);
		Gravity = gravity;
	}

	@Override
	public void run() 
	{
		CurLoc.add(CurVelocity);
		CurVelocity.add(Gravity);
		ComplexEffects.drawSphere(CurLoc, EnumParticle.FLAME, 0.3, null);
		
		if(EntitySelector.getNearbyEntities(CurLoc, LivingEntity.class, .5, .5, .5, Arrays.asList(Caster)).size() == 0
				&& CurLoc.getBlock().getType().equals(Material.AIR))
		{
			damageNearbyCreatures(CurLoc, 5, 2, 5, 5, Caster);
			CurLoc.getWorld().playSound(CurLoc, Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
			Cancel();
		}		
	}
	
	private void damageNearbyCreatures(Location loc, double dx, double dy, double dz, double damage, Entity source)
	{
		for(LivingEntity l : CollectionsHelper
				.ConvertAll(EntitySelector
				.getNearbyEntities(loc, LivingEntity.class, .5, .5, .5, Arrays.asList(Caster))
				, x -> { return (LivingEntity)x; }))
			l.damage(damage, source);
	}
}
