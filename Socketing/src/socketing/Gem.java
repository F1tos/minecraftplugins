package socketing;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import com.gmail.fitostpm.staffs.Staff;

public class Gem 
{
	public String Name;
	public List<String> Lore;
	public List<Material> targets;
	
	public Gem(ItemStack item)
	{
		Name = item.getItemMeta().getDisplayName();
		Lore = item.getItemMeta().getLore();
		targets = getTargets(item.getItemMeta().getLore());
	}
	
	public static List<String> Gems = new ArrayList<String>();
	
	public static List<Material> Swords = new ArrayList<Material>();
	
	public static List<Material> Axes = new ArrayList<Material>();
	
	public static List<Material> Chestplates = new ArrayList<Material>();
	
	public static boolean isGem(ItemStack item)
	{
		if(item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLore())
		{
			return Gems.contains(item.getItemMeta().getDisplayName()) && 
					item.getItemMeta().getLore().contains(ChatColor.GRAY + "----Может быть вставлен в----") &&
					item.getItemMeta().getLore().contains(ChatColor.GRAY + "-----------------------------");
		}
		return false;
	}
	
	public static ItemStack hasChisel(Player player)
	{
		Inventory inv = player.getInventory();
		for(ItemStack is : inv.getContents())
		{
			if(is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().hasLore() &&
					is.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Стамеска") &&
					is.getItemMeta().getLore().contains(ChatColor.GRAY + "Используется для добавления пустых слотов в ваше оружие"))
				return is;
		}
		return null;
	}
	
	public static boolean isSocketable(ItemStack item)
	{
		Material material = item.getType();
		return Swords.contains(material) || Axes.contains(material) || Chestplates.contains(material) || 
				Staff.isStaff(item) || material.equals(Material.BOW);
	}
	
	public List<Material> getTargets(List<String> lore)
	{
		List<Material> targets = new ArrayList<Material>();
		for(String s : lore)
		{
			if(s.equals(ChatColor.GRAY + "  - Меч"))
				targets.addAll(Swords);
			else if(s.equals(ChatColor.GRAY + "  - Лук"))
				targets.add(Material.BOW);
			else if(s.equals(ChatColor.GRAY + "  - Нагрудник"))
				targets.addAll(Chestplates);
			else if(s.equals(ChatColor.GRAY + "  - Топор"))
				targets.addAll(Axes);
		}
		return targets;		
	}
	
	static public boolean isInsertable(Gem gem, ItemStack item)
	{
		if(Staff.isStaff(item))
			return gem.Lore.contains(ChatColor.GRAY + "  - Посох");
		else
			return gem.targets.contains(item.getType());
	}
}
