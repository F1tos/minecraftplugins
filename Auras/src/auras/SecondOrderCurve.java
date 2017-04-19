package auras;

import org.bukkit.entity.Player;

import de.inventivegames.particle.ParticleEffect;

public abstract class SecondOrderCurve extends Aura
{
	protected double Radius;
	protected double Height;
	protected char Axis;
	
	public SecondOrderCurve(Player player, ParticleEffect effect, double height, double radius, char axis)
	{
		this.setEffectHolder(player);
		this.setEffect(effect);
		setHeight(height);
		setRadius(radius);
		setAxis(axis);		
	}
	
	public void setEffect(ParticleEffect effect) {
		super.setEffect(effect);		
	}

	public void setEffectHolder(Player player) {
		super.setEffectHolder(player);		
	}

	public abstract void show();

	public double getRadius() {
		return Radius;
	}

	public void setRadius(double radius) {
		Radius = radius;
	}

	public double getHeight() {
		return Height;
	}

	public void setHeight(double height) {
		Height = height;
	}

	public double getAxis() {
		return Axis;
	}

	public void setAxis(char axis) {
		Axis = axis;
	}
}
