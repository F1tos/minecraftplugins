package com.gmail.fitostpm.extraweapons.garbage;

import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

import com.gmail.fitostpm.extraweapons.MainClass;

import net.minecraft.server.v1_11_R1.EntityLiving;
import net.minecraft.server.v1_11_R1.EntitySpider;
import net.minecraft.server.v1_11_R1.PathfinderGoalMeleeAttack;

public class PathfinderGoalSpiderMeleeAttackExtended extends PathfinderGoalMeleeAttack
{
	private double WebCooldown = 3;
	private boolean IsWebOnCooldown = true;

    public PathfinderGoalSpiderMeleeAttackExtended(EntitySpider entityspider)
    {
    	super(entityspider, 1.0D, true);
    }
    
    public boolean b()
    {    	
    	float f = this.b.e(1.0F);
    	if ((f >= 0.5F) && (this.b.getRandom().nextInt(100) == 0))
    	{
    		this.b.setGoalTarget(null);
    		return false;
    	}
    	
    	if(IsWebOnCooldown && isTargetInSight(10))
    	{
    		ShootWeb();
    		IsWebOnCooldown = false;
    		Bukkit.getScheduler().scheduleSyncDelayedTask(MainClass.Instance, 
    				new Runnable() {
						@Override
						public void run() {
							IsWebOnCooldown = true;							
						}    			
    				}, (long) WebCooldown * 20);
    	}
    	
    	return super.b();
    }
    
    protected double a(EntityLiving entityliving)
    {
    	return 4.0F + entityliving.width;
    }
    
    private boolean isTargetInSight(double offset)
    {
    	return Math.toDegrees(getVectorFromYaw(this.b.yaw).angle(getVectorFromYaw(yawToTarget()))) < offset;
    }
    
    private Vector getVectorFromYaw(double yaw)
    {
    	return new Vector(Math.cos(yaw), 0, Math.sin(yaw));
    }
    
    private double yawToTarget()
    {
    	return Math.atan((this.b.getGoalTarget().locZ - this.b.locZ)/(this.b.getGoalTarget().locX - this.b.locX));
    }
    
    private void ShootWeb()
    {
    	
    }
}
