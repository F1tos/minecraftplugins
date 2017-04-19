package socketing;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;
import com.gmail.fitostpm.staffs.Staff;

public class DecayWeapons implements Listener 
{
	@EventHandler
	public void onLaunch(ProjectileLaunchEvent event)
	{
		Projectile projectile = event.getEntity();
		if(projectile.getShooter() instanceof Player)
		{
			Player shooter = (Player) projectile.getShooter();
			if(Staff.isStaff(shooter.getItemInHand()))
			{
				ItemStack weapon = shooter.getItemInHand();
				if(weapon.hasItemMeta() && weapon.getItemMeta().hasLore() &&
					weapon.getItemMeta().getLore().contains(ChatColor.LIGHT_PURPLE + "Жемчуг распада"))
				{
					double x = projectile.getVelocity().getX();
					double y = projectile.getVelocity().getY();
					double z = projectile.getVelocity().getZ();
					Vector velocity = new Vector(x*Math.cos(Math.PI/9) + z*Math.sin(Math.PI/9), y, 
							-x*Math.sin(Math.PI/9) + z*Math.cos(Math.PI/9));
					Staff.LaunchMissilewoee(projectile.getLocation(), shooter, weapon, velocity.multiply(0.9));
					velocity.setX(x*Math.cos(-Math.PI/9) + z*Math.sin(-Math.PI/9));
					velocity.setZ(-x*Math.sin(-Math.PI/9) + z*Math.cos(-Math.PI/9));
					Staff.LaunchMissilewoee(projectile.getLocation(), shooter, weapon, velocity);
				}					
			}
			if(shooter.getItemInHand().getType().equals(Material.BOW))
			{
				Arrow oldarrow = (Arrow) projectile;
				ItemStack weapon = shooter.getItemInHand();	
				if(weapon.hasItemMeta() && weapon.getItemMeta().hasLore() &&
						weapon.getItemMeta().getLore().contains(ChatColor.LIGHT_PURPLE + "Жемчуг распада"))
				{
					double x = oldarrow.getVelocity().getX();
					double y = oldarrow.getVelocity().getY();
					double z = oldarrow.getVelocity().getZ();
					Vector velocity = new Vector(x*Math.cos(Math.PI/9) + z*Math.sin(Math.PI/9), y, 
							-x*Math.sin(Math.PI/9) + z*Math.cos(Math.PI/9));
					Arrow arrow = (Arrow) oldarrow.getWorld().spawnEntity(oldarrow.getLocation(), EntityType.ARROW);
					arrow.setVelocity(velocity.multiply(0.9));
					arrow.setShooter(shooter);
					arrow.setCritical(oldarrow.isCritical());	
					
					velocity.setX(x*Math.cos(-Math.PI/9) + z*Math.sin(-Math.PI/9));
					velocity.setZ(-x*Math.sin(-Math.PI/9) + z*Math.cos(-Math.PI/9));
					arrow = (Arrow) oldarrow.getWorld().spawnEntity(oldarrow.getLocation(), EntityType.ARROW);
					arrow.setVelocity(velocity.multiply(0.9));
					arrow.setShooter(shooter);
					arrow.setCritical(oldarrow.isCritical());				
				}			
			}
		}
	}

}
