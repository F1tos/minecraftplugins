package com.gmail.fitostpm.extraweapons.garbage;

import net.minecraft.server.v1_11_R1.EntityHuman;
import net.minecraft.server.v1_11_R1.EntitySpider;
import net.minecraft.server.v1_11_R1.World;

public class StrongSpider extends EntitySpider {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public StrongSpider(World world) {
		super(world);

		this.targetSelector.a(2, new BetterPathfinderGoalSpiderNearestAttackableTarget(this, EntityHuman.class));
	}

}
