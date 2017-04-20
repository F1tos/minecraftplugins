package com.gmail.fitostpm.extraweapons.garbage;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.fitostpm.extraweapons.MainClass;

import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

public class GarbageListener implements Listener 
{

	//@EventHandler
	public void onMove(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		for(Entity e : player.getNearbyEntities(2, 2, 2))
		{
			if(isTargetInSight(player, e, 10))
				e.setGlowing(true);
			else 
				e.setGlowing(false);
		}
	}
	
	@EventHandler
	public void SummonSpoider(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		ItemStack item = p.getInventory().getItemInMainHand();
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) && !item.equals(null) && 
				item.getType().equals(Material.DIAMOND))
		{
			
			Bukkit.getScheduler().scheduleSyncRepeatingTask(MainClass.Instance, new Runnable()
			{
				private double i = 0;
				private double j = 45;
				
				@Override
				public void run()
				{					
					if(i < 90 || (i > 180 && i < 270))
						j+=3;
					else
						j-=3;						

					show(p, p.getLocation().getX(), p.getLocation().getY()+1.5, p.getLocation().getZ(), i, j);	
					
					i = (i+5) % 360;
					
				}
			}, 0, 5);
			
		}
	}
	
	public void show(Player p, double x, double y, double z, double i, double j)
	{
		double r = 2;
		((CraftPlayer)p).getHandle().playerConnection
		.sendPacket(new PacketPlayOutWorldParticles(EnumParticle.FLAME, true, 
				(float) (x+r*Math.sin(j)*Math.cos(i)), 
				(float) (y+r*Math.cos(j)), 
				(float) (z+r*Math.sin(j)*Math.sin(i)), .1f, .1f, .1f, .0001f, 1));
	}
	
	@EventHandler
	public void onDamageTake(EntityDamageEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			Player player = (Player) event.getEntity();
			player.sendMessage("" + player.getHealth());
			if(player != null && player.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND))
			{
				event.setCancelled(true);
			}
			else if(player != null && player.getInventory().getItemInMainHand().getType().equals(Material.EMERALD)
					&& player.getHealth() < event.getDamage())
			{
				event.setCancelled(true);
				player.damage(player.getHealth() - 1);
			}
		}
	}
	
	/*@EventHandler
	public void onTarget(EntityTargetEvent e)
	{
		Entity entity = e.getEntity();
		Entity target = e.getTarget();
		Bukkit.getLogger().info(e.getReason().toString());
		if(entity != null && entity instanceof CraftSpider && target != null)
		{
			ThrowWeb((LivingEntity)entity);
		}
	}*/
	
	
	//@SuppressWarnings({ "rawtypes", "unchecked" })
	@EventHandler
	public void SummonSpoider(PlayerInteractEntityEvent e)
	{
		Player player = e.getPlayer();
		Entity entity = e.getRightClicked();
		double actualPitch = player.getLocation().getPitch();
		double y = entity.getLocation().getY() - player.getLocation().getY();
		double distance = player.getLocation().distance(entity.getLocation());
		double calcuatedPitch = Math.toDegrees(Math.acos(y/distance)) - 90;
		player.sendMessage("actualPitch: " + actualPitch);
		player.sendMessage("calcuatedPitch: " + calcuatedPitch);

		/*e.getPlayer().sendMessage("in sight? " + isTargetInSight(e.getPlayer(), e.getRightClicked(), 10));
		e.getPlayer().sendMessage("player's yaw: " + e.getPlayer().getLocation().getYaw());
		e.getPlayer().sendMessage("yawToTarget: " + yawToTarget(e.getPlayer(), e.getRightClicked()));
		if(e.getHand().equals(EquipmentSlot.HAND) && e.getRightClicked() instanceof CraftSpider)
		{
			EntitySpider spider = ((CraftSpider)e.getRightClicked()).getHandle();
			spider.goalSelector.a(3, new PathfinderGoalSpiderMeleeAttackExtended(spider));
			//spider.targetSelector.a(2,  new BetterPathfinderGoalSpiderNearestAttackableTarget(spider, EntityHuman.class));
		}*/
	}

    private boolean isTargetInSight(Entity entity, Entity target, double offset)
    {
    	double yaw = entity.getLocation().getYaw();
    	if(yaw < 0) yaw += 360;
    	return Math.abs(yaw - yawToTarget(entity, target)) < offset;
    }
    
    private double yawToTarget(Entity entity, Entity target)
    {
		double x = target.getLocation().getX() - entity.getLocation().getX();
		double z = target.getLocation().getZ() - entity.getLocation().getZ();
		double distance = Math.pow(Math.pow(x, 2) + Math.pow(z, 2), 0.5);
		double angle = Math.toDegrees(Math.acos(z/distance));
		if(x > 0)
			angle = 360 - angle;
		return angle;
    	//return Math.atan((target.getLocation().getZ() - entity.getLocation().getZ())/(target.getLocation().getX() - entity.getLocation().getX()));
    }       
    
	
	
}
