package com.gmail.fitostpm.spellbook.tasks;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.fitostpm.spellbook.MainClass;
import com.gmail.fitostpm.spellbook.spells.UnitTargetSpell;
import com.gmail.fitostpm.spellbook.targets.Target;
import com.gmail.fitostpm.spellbook.util.ExtraMath;

import net.minecraft.server.v1_11_R1.ChatComponentText;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;

public class TargetSelector extends Selector 
{
	private Player Caster;
	private Target[] Targets;
	private int CurrentTargetIndex;
	
	public TargetSelector(Player caster, Target[] targets, int curTargetIndex, UnitTargetSpell spell)
	{
		Caster = caster;
		Targets = targets;
		CurrentTargetIndex = curTargetIndex;
		CastingSpell = spell;
	}

	@Override
	public void run() {
		net.minecraft.server.v1_11_R1.EntityPlayer nmsPlayer = ((CraftPlayer)Caster).getHandle();
		Caster.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 10));
		
		Location l = Caster.getLocation();
		l.setYaw((float) Targets[CurrentTargetIndex].yaw);
		l.setPitch((float) Targets[CurrentTargetIndex].pitch);
		Caster.teleport(l);			

		for(int i = 0; i < Targets.length; i++)
		{
			Targets[i].yaw = ExtraMath.getYawToTarget(Caster, Targets[i].entity);
			Targets[i].pitch = ExtraMath.getPitchToTarget(Caster, Targets[i].entity);
			if(i == CurrentTargetIndex)
				Targets[i].entity.setGlowing(true);
			else
				Targets[i].entity.setGlowing(false);
		}		

		nmsPlayer.playerConnection.sendPacket(new PacketPlayOutTitle(
				PacketPlayOutTitle.EnumTitleAction.RESET, new ChatComponentText(""), 1, 1, 1));
		nmsPlayer.playerConnection.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES,
				new ChatComponentText("times"), 1, 10, 1));
		nmsPlayer.playerConnection.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
				new ChatComponentText("<Q                                             F>"), 1, 10, 1));
		nmsPlayer.playerConnection.sendPacket(new PacketPlayOutChat(
				new ChatComponentText("Left Click - OK            Right Click - Cancel"), (byte)2));

	}
	
	@Override
	public void Cast()
	{
		((UnitTargetSpell)CastingSpell).Behavior(Caster, Targets[CurrentTargetIndex].entity);
		Stop();
	}

	@Override
	public void Stop() 
	{
		for(Target t : Targets)
			t.entity.setGlowing(false);
		Caster.removePotionEffect(PotionEffectType.SLOW);
		((CraftPlayer)Caster).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(
				PacketPlayOutTitle.EnumTitleAction.CLEAR, new ChatComponentText(""), 1, 1, 1));
		((CraftPlayer)Caster).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(
				new ChatComponentText(""), (byte)2));
		MainClass.CastingPlayers.remove(Caster);
		Cancel();
	}
	
	public void PrevTarget()
	{
		if(CurrentTargetIndex == 0)
			CurrentTargetIndex = Targets.length - 1;
		else
			CurrentTargetIndex--;
	}
	
	public void NextTarget()
	{
		CurrentTargetIndex = (CurrentTargetIndex + 1) % Targets.length;				
	}

}
