package com.gmail.fitostpm.extraweapons.garbage;


import net.minecraft.server.v1_11_R1.EntityLiving;
import net.minecraft.server.v1_11_R1.EntitySpider;
import net.minecraft.server.v1_11_R1.PathfinderGoalNearestAttackableTarget;

public class BetterPathfinderGoalSpiderNearestAttackableTarget <T extends EntityLiving> 
extends PathfinderGoalNearestAttackableTarget<T> 
{
	public BetterPathfinderGoalSpiderNearestAttackableTarget(EntitySpider entityspider, Class<T> oclass)
    {
		super(entityspider, oclass, true);
    }
    
    public boolean a()
    {
    	
    	float f = this.e.e(1.0F);
      
    	if(f >= 0.5F)
    	{
    		return false;
    	}
    	else
    	{
        	return super.a();    		
    	}
    }
}
