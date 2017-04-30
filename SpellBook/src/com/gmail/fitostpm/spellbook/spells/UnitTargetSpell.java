package com.gmail.fitostpm.spellbook.spells;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.gmail.fitostpm.spellbook.targets.Target;
import com.gmail.fitostpm.spellbook.util.ExtraMath;

public abstract class UnitTargetSpell extends Spell 
{
	public boolean CanSelfTarget = false;
	
	public abstract void Behavior(Player caster, LivingEntity target/*, int level*/);	
	
	public Target[] selectTargets(LivingEntity caster)
	{
		List<Target> targets = new ArrayList<Target>();
		caster.getNearbyEntities(CastRange, CastRange, CastRange).forEach(entity -> {
			if(entity instanceof LivingEntity)
				targets.add(new Target((LivingEntity) entity, 
						ExtraMath.getYawToTarget(caster, entity), 
						ExtraMath.getPitchToTarget(caster, entity)));
		});
		Target[] result = new Target[targets.size()];
		targets.toArray(result);
		Arrays.sort(result, (x,y) -> (int) (x.yaw - y.yaw));
		
		return result;		
	}
}
