package extrarecipes;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

public class mainClass extends JavaPlugin 
{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		return false;		
	}
	
	@SuppressWarnings("deprecation")
	public void onEnable()
	{	
		MaterialData[] stone = new MaterialData[3];
		stone[0] = new MaterialData(Material.STONE,(byte) 1);
		stone[1] = new MaterialData(Material.STONE,(byte) 3);
		stone[2] = new MaterialData(Material.STONE,(byte) 5);		
		ItemStack result = new ItemStack(Material.STONE_SWORD,1);
		ShapedRecipe recipe = new ShapedRecipe(result);
		
		//add swords
		recipe.shape("010","010","020");
		int i=0;
		for(i=0; i<3; i++)
		{
			recipe.setIngredient('1', stone[i]);
			recipe.setIngredient('2', Material.STICK);
			getServer().addRecipe(recipe);		
		}
		
		result.setType(Material.FURNACE);
		recipe = new ShapedRecipe(result);
		recipe.shape("111","101","111");
		for(i=0; i<3; i++)
		{
			recipe.setIngredient('1', stone[i]);
			getServer().addRecipe(recipe);		
		}
	}
	
	public void onDisable() { }

}
