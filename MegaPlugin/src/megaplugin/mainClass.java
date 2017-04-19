package megaplugin;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import net.md_5.bungee.api.ChatColor;    
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;



public class mainClass extends JavaPlugin
{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("storojevayailigonchaya?"))
		{
			getLogger().info("gonchaya");
			return true;
		}
		return false;
	}
	public void onEnable() 
	{
        getServer().getPluginManager().registerEvents(new LoginListener(), this);
		 
		ItemStack bottle = new ItemStack(Material.BOW, 1);
		 
		bottle.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
		
		ItemMeta lore = bottle.getItemMeta();
		lore.setDisplayName(ChatColor.BLACK + "go osu");
		List<String> lorestring = new ArrayList<String>();
		lorestring.add(ChatColor.BLUE + "Hello");
		lorestring.add("World");
		lorestring.add("!!!");
 		lore.setLore(lorestring);
		bottle.setItemMeta(lore);
		
		ShapedRecipe expBottle = new ShapedRecipe(bottle);
		 
		expBottle.shape("qwe","wqe","qwe");
		
		MaterialData item = new MaterialData(Material.IRON_INGOT);
		
		//expBottle.setIngredient('q', Material.APPLE);
		expBottle.setIngredient('w', item);
		expBottle.setIngredient('e', Material.STRING);
		 
		getServer().addRecipe(expBottle);
		 
	}
		 
	public void onDisable() 
	{
		 
	}
}
