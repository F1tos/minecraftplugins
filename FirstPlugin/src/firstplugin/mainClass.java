package firstplugin;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class mainClass extends JavaPlugin
{
	public void onEnable()
	{	
		//получение посоха огня 1 (2,5)
		
		ItemStack item = new ItemStack(Material.BLAZE_ROD,1);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "Посох огня");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add(ChatColor.GRAY + "Огненный шар I");
		im.setLore(lorelist);
		item.setItemMeta(im);
				
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("100","200","000");
		recipe.setIngredient('1', Material.BLAZE_POWDER);
		recipe.setIngredient('2', Material.BLAZE_ROD);
		
		getServer().addRecipe(recipe);
		
		//получение посоха огня 2 (3)
		
		item = new ItemStack(Material.BLAZE_ROD,1);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "Посох огня");
		lorelist = new ArrayList<String>();
		lorelist.add(ChatColor.GRAY + "Огненный шар II");
		im.setLore(lorelist);
		item.setItemMeta(im);
				
		recipe = new ShapedRecipe(item);
		recipe.shape("010","020","000");
		recipe.setIngredient('1', Material.BLAZE_POWDER);
		recipe.setIngredient('2', Material.BLAZE_ROD);
		
		getServer().addRecipe(recipe);
		
		//получение посоха огня 3 (3)
		
		item = new ItemStack(Material.BLAZE_ROD,1);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "Посох огня");
		lorelist = new ArrayList<String>();
		lorelist.add(ChatColor.GRAY + "Огненный шар III");
		im.setLore(lorelist);
		item.setItemMeta(im);
				
		recipe = new ShapedRecipe(item);
		recipe.shape("001","002","001");
		recipe.setIngredient('1', Material.BLAZE_POWDER);
		recipe.setIngredient('2', Material.BLAZE_ROD);
		
		getServer().addRecipe(recipe);
		
		//получение железного лука
		item = new ItemStack(Material.BOW,1);
		im = item.getItemMeta();
		im.setDisplayName("Железный лук");
		item.setItemMeta(im);
		
		recipe = new ShapedRecipe(item);
		recipe.shape("012","102","012");
		recipe.setIngredient('1', Material.IRON_INGOT);
		recipe.setIngredient('2', Material.STRING);
		
		getServer().addRecipe(recipe);
		
		//получение золотого лука
		item = new ItemStack(Material.BOW,1);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.ALL_CODES + "Золотой лук");
		lorelist = new ArrayList<String>();
		lorelist.add(ChatColor.GRAY + "Пиу пиу пиу");
		im.setLore(lorelist);
		item.setItemMeta(im);
		
		recipe = new ShapedRecipe(item);
		recipe.shape("012","102","012");
		recipe.setIngredient('1', Material.GOLD_INGOT);
		recipe.setIngredient('2', Material.STRING);
		
		getServer().addRecipe(recipe);
		
		//получение лука с изумрудной струной
		item = new ItemStack(Material.BOW,1);
		im = item.getItemMeta();
		im.setDisplayName("Лук с изумрудной струной");
		item.setItemMeta(im);
		
		recipe = new ShapedRecipe(item);
		recipe.shape("012","102","012");
		recipe.setIngredient('1', Material.STICK);
		recipe.setIngredient('2', Material.EMERALD);

		getServer().addRecipe(recipe);	
		
		//получение зачарованных ботинок
		item = new ItemStack(Material.GLASS_BOTTLE);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "Облако в пузырьке");
		lorelist = new ArrayList<String>();
		lorelist.add(ChatColor.GRAY + "При падении нажмите правую кнопку мыши,");
		lorelist.add(ChatColor.GRAY + "чтобы подпрыгнуть снова.");
		im.setLore(lorelist);
		item.setItemMeta(im);
		
		recipe = new ShapedRecipe(item);
		recipe.shape("232","313","232");
		recipe.setIngredient('1', Material.GLASS_BOTTLE);
		recipe.setIngredient('2', Material.FEATHER);
		recipe.setIngredient('3', Material.SUGAR);
		
		getServer().addRecipe(recipe);	

		
		getServer().getPluginManager().registerEvents(new PlayerListeners(), this);
	}
	
	public void onDisable()
	{
		
	}
}
