package auras;

import org.bukkit.entity.Player;

import de.inventivegames.particle.ParticleEffect;

abstract public class Aura 
{
	protected Player EffectHolder;
	protected ParticleEffect Effect;
	
	public abstract void show();

	public Player getEffectHolder() {
		return EffectHolder;
	}

	public void setEffectHolder(Player effectHolder) {
		EffectHolder = effectHolder;
	}

	public ParticleEffect getEffect() {
		return Effect;
	}

	public void setEffect(ParticleEffect effect) {
		Effect = effect;
	}
	
	
	
}
