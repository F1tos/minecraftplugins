package com.gmail.fitostpm.zrtd.missile;

import java.util.Random;

import org.bukkit.entity.Projectile;

import com.gmail.fitostpm.zrtd.tower.Tower;

public class Missile 
{
	private Projectile projectile;
	private Tower Shooter;
	private boolean isCritical;
	
	public Missile(Projectile p, Tower t)
	{
		projectile = p;
		Shooter = t;
		Random r = new Random();
		if(r.nextDouble() < t.getCritChance())
			isCritical = true;
	}
	
	public Projectile getProjectile() {
		return projectile;
	}
	public void setProjectile(Projectile projectile) {
		this.projectile = projectile;
	}
	public Tower getShooter() {
		return Shooter;
	}
	public void setShooter(Tower shooter) {
		Shooter = shooter;
	}
	public boolean isCritical() {
		return isCritical;
	}
	public void setCritical(boolean isCritical) {
		this.isCritical = isCritical;
	}

}
