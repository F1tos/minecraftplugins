package com.gmail.fitostpm.spellbook.spells;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

public class InstantHealSpell extends UnitTargetSpell
{
	public InstantHealSpell()
	{
		Name = "Heal";
		Description = new ArrayList<String>(Arrays.asList(new String[] {
			"Increase current health of target.", "Undead targets won't be damaged by that spell.", "",
			ChatColor.BLUE + "Amount: 4", ChatColor.LIGHT_PURPLE + "Cast Range: 15", "",
			ChatColor.YELLOW + "Shift-click to target self"
		}));
		CastRange = 15;
		CanSelfTarget = true;
	}
	
	protected int Amount(int level)
	{
		return 2*level;
	}
	public void playEffect(LivingEntity target)
	{
		for(Entity e : target.getNearbyEntities(20, 20, 20))
			if(e instanceof CraftPlayer)
				((CraftPlayer) e).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles(
						EnumParticle.HEART, true,
						(float) target.getLocation().getX(), 
						(float) target.getLocation().getY() + 2, 
						(float) target.getLocation().getZ(), 
						0.1f, 0.1f, 0.1f, 0.0001f, 10, 1));
	}	
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void Behavior(Player caster, LivingEntity target/*, int level*/) {
		target.setHealth(Math.min(target.getHealth() + 4, target.getMaxHealth()));
		playEffect(target);
	}	
}
