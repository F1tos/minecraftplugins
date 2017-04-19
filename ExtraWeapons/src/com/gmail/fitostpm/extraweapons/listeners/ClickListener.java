package com.gmail.fitostpm.extraweapons.listeners;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.gmail.fitostpm.extraweapons.MainClass;

import net.md_5.bungee.api.ChatColor;

public class ClickListener implements Listener 
{
	@EventHandler
	public void onClick(PlayerInteractEvent e)
	{		
		Player p = e.getPlayer();
		ItemStack item = p.getInventory().getItemInMainHand();
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) && !item.equals(null) && 
				item.hasItemMeta() && item.getItemMeta().hasDisplayName())
		{
			String name = item.getItemMeta().getDisplayName();
			
			//---------------ÎÑÊÎËÎ×ÍÀß ÃÐÀÍÀÒÀ---------------
			if(name.equals(ChatColor.DARK_RED + "Îñêîëî÷íàÿ ãðàíàòà"))
			{
				MainClass.FlyingExplGrenades.add(p.launchProjectile(Snowball.class));
				ItemStack item1 = new ItemStack(item);
				item1.setAmount(1);
				RemoveOne(p.getInventory(),item1);				
			} 
			
			//---------------ÏÀÓ×Üß ÑÅÒÜ---------------
			else if(name.equals(ChatColor.GREEN + "Ïàó÷üÿ ñåòü") && e.getHand().equals(EquipmentSlot.HAND))
				ThrowWeb(p);			
		}

		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) && item != null 
				&& p.getInventory().getItemInMainHand().getType().equals(Material.EMERALD)
				&& e.getHand().equals(EquipmentSlot.HAND))
		{
			p.sendMessage("bukkit yaw: " + p.getLocation().getYaw());;
			p.sendMessage("nms yaw: " + ((CraftPlayer)p).getHandle().yaw);
			p.sendMessage("bukkit pitch: " + p.getLocation().getPitch());;
			p.sendMessage("nms pitch: " + ((CraftPlayer)p).getHandle().pitch);
		}		
	}

	@SuppressWarnings("deprecation")
	private void ThrowWeb(LivingEntity entity)
	{
		Projectile pr = entity.launchProjectile(Snowball.class);
		Vector velocity = pr.getVelocity();
		pr.remove();
		FallingBlock fb = entity.getWorld().spawnFallingBlock(pr.getLocation(), Material.WEB, (byte) 0);
		fb.setVelocity(velocity);
		fb = entity.getWorld().spawnFallingBlock(pr.getLocation(), Material.WEB, (byte) 0);
		fb.setVelocity(AddRandomOffset(velocity, 0.2, true));
		fb = entity.getWorld().spawnFallingBlock(pr.getLocation(), Material.WEB, (byte) 0);
		fb.setVelocity(AddRandomOffset(velocity, 0.2, true));		
	}
	
	
	public Vector AddRandomOffset(Vector vector, double offsetAmount, boolean sym)
	{
		Random r = new Random();
		if(sym)
			return vector.add(new Vector(r.nextDouble() * offsetAmount - offsetAmount/2,
					r.nextDouble() * offsetAmount - offsetAmount/2,
					r.nextDouble() * offsetAmount - offsetAmount/2));
		else
			return vector.add(new Vector(r.nextDouble() * offsetAmount,
					r.nextDouble() * offsetAmount,
					r.nextDouble() * offsetAmount));
	}
	
	public void RemoveOne(Inventory inv, ItemStack item)
	{
		int slot = getSlot(inv,item);
		int amount = inv.getItem(slot).getAmount();
		if(amount == 1)
			inv.setItem(slot, new ItemStack(Material.AIR));
		else
		{
			item.setAmount(amount-1);
			inv.setItem(slot, item);
		}	
	}
	
	public int getSlot(Inventory inv, ItemStack item)
	{
		int i;
		for(i=1; i<65; i++)
		{
			item.setAmount(i);
			if(inv.contains(item))
				return inv.first(item);
		}
		return 0;
	}
}
