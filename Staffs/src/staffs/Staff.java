package staffs;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class Staff 
{
	private Player Owner;
	private ItemStack Item;
	private double Damage;
	private boolean Crit;
	private boolean Weak;
	
	public Player getOwner() {
		return Owner;
	}
	public void setOwner(Player owner) {
		Owner = owner;
	}
	public ItemStack getItem() {
		return Item;
	}
	public void setItem(ItemStack item) {
		Item = item;
	}
	
	public Staff(Player owner, ItemStack item)
	{
		Owner = owner;
		Item = item;
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		String[] alore = new String[lore.size()];
		lore.toArray(alore);
		int i = 0;
		while(i < alore.length && alore[i].indexOf(ChatColor.BLUE + "+") == -1)
			i++;
		Damage = Integer.parseInt(alore[i].substring(alore[i].indexOf('+') + 1, alore[i].indexOf(' ')));
		while(i < alore.length && alore[i].indexOf(ChatColor.RED + "Exhaustion:") == -1)
			i++;
		double exhaustion = Double.parseDouble(alore[i].substring(alore[i].indexOf(' ') + 1));
		owner.setSaturation((float) Math.max(0, owner.getSaturation()-exhaustion));
		Crit = owner.getFoodLevel() > 17;
		Weak = owner.getFoodLevel() < 6;
	};
	
	static public String[] Staffs = {
			ChatColor.DARK_AQUA + "Wooden staff",
			ChatColor.BLUE + "Bone staff",
			ChatColor.DARK_PURPLE + "Amber staff"
		};

	static public void LaunchMissile(Player p, ItemStack i)
	{
		Projectile pr = p.launchProjectile(Snowball.class);
		if(i.getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "Wooden staff"))
			pr.setVelocity(pr.getVelocity().multiply(0.9));
		if(i.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Amber staff"))
			pr.setVelocity(pr.getVelocity().multiply(1.2));
		mainClass.LaunchedMissiles.put(pr, new Staff(p, i));
	}
	
	static public void LaunchMissilewoee(Location l, Player p, ItemStack i, Vector v)
	{
		Projectile missile = (Projectile) p.getWorld().spawnEntity(l, EntityType.SNOWBALL);
		missile.setVelocity(v);
		missile.setShooter(p);
		mainClass.LaunchedMissiles.put(missile, new Staff(p, i));
	}
	
	static public boolean isStaff(ItemStack item)
	{
		if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLore())
		{
			String name = item.getItemMeta().getDisplayName();
			for (String s : Staffs)
				if(s.equals(name))
					return true;
		}
		return false;
	}
	public double Damage() {
		return Damage;
	}
	public boolean isCrit() {
		return Crit;
	}
	public boolean isWeak() {
		return Weak;
	}
}
