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
		//��������� ������ ���� 1 (2,5)
		
		ItemStack item = new ItemStack(Material.BLAZE_ROD,1);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "����� ����");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add(ChatColor.GRAY + "�������� ��� I");
		im.setLore(lorelist);
		item.setItemMeta(im);
				
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("100","200","000");
		recipe.setIngredient('1', Material.BLAZE_POWDER);
		recipe.setIngredient('2', Material.BLAZE_ROD);
		
		getServer().addRecipe(recipe);
		
		//��������� ������ ���� 2 (3)
		
		item = new ItemStack(Material.BLAZE_ROD,1);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "����� ����");
		lorelist = new ArrayList<String>();
		lorelist.add(ChatColor.GRAY + "�������� ��� II");
		im.setLore(lorelist);
		item.setItemMeta(im);
				
		recipe = new ShapedRecipe(item);
		recipe.shape("010","020","000");
		recipe.setIngredient('1', Material.BLAZE_POWDER);
		recipe.setIngredient('2', Material.BLAZE_ROD);
		
		getServer().addRecipe(recipe);
		
		//��������� ������ ���� 3 (3)
		
		item = new ItemStack(Material.BLAZE_ROD,1);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "����� ����");
		lorelist = new ArrayList<String>();
		lorelist.add(ChatColor.GRAY + "�������� ��� III");
		im.setLore(lorelist);
		item.setItemMeta(im);
				
		recipe = new ShapedRecipe(item);
		recipe.shape("001","002","001");
		recipe.setIngredient('1', Material.BLAZE_POWDER);
		recipe.setIngredient('2', Material.BLAZE_ROD);
		
		getServer().addRecipe(recipe);
		
		//��������� ��������� ����
		item = new ItemStack(Material.BOW,1);
		im = item.getItemMeta();
		im.setDisplayName("�������� ���");
		item.setItemMeta(im);
		
		recipe = new ShapedRecipe(item);
		recipe.shape("012","102","012");
		recipe.setIngredient('1', Material.IRON_INGOT);
		recipe.setIngredient('2', Material.STRING);
		
		getServer().addRecipe(recipe);
		
		//��������� �������� ����
		item = new ItemStack(Material.BOW,1);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.ALL_CODES + "������� ���");
		lorelist = new ArrayList<String>();
		lorelist.add(ChatColor.GRAY + "��� ��� ���");
		im.setLore(lorelist);
		item.setItemMeta(im);
		
		recipe = new ShapedRecipe(item);
		recipe.shape("012","102","012");
		recipe.setIngredient('1', Material.GOLD_INGOT);
		recipe.setIngredient('2', Material.STRING);
		
		getServer().addRecipe(recipe);
		
		//��������� ���� � ���������� �������
		item = new ItemStack(Material.BOW,1);
		im = item.getItemMeta();
		im.setDisplayName("��� � ���������� �������");
		item.setItemMeta(im);
		
		recipe = new ShapedRecipe(item);
		recipe.shape("012","102","012");
		recipe.setIngredient('1', Material.STICK);
		recipe.setIngredient('2', Material.EMERALD);

		getServer().addRecipe(recipe);	
		
		//��������� ������������ �������
		item = new ItemStack(Material.GLASS_BOTTLE);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "������ � ��������");
		lorelist = new ArrayList<String>();
		lorelist.add(ChatColor.GRAY + "��� ������� ������� ������ ������ ����,");
		lorelist.add(ChatColor.GRAY + "����� ����������� �����.");
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
