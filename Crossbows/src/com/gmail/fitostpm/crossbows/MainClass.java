package com.gmail.fitostpm.crossbows;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.fitostpm.crossbows.listeners.TestListener;
import com.gmail.fitostpm.crossbows.tasks.TestTask;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagString;

public class MainClass extends JavaPlugin 
{
	public static HashMap<Player, Double> players = new HashMap<Player, Double>();
	
	public void onEnable()
	{
		net.minecraft.server.v1_8_R3.ItemStack spigotItemStack = CraftItemStack.asNMSCopy(new ItemStack(Material.DIAMOND_SWORD));
		NBTTagCompound tag = new NBTTagCompound();
		tag.set("hello", new NBTTagString("world"));
		spigotItemStack.setTag(tag);		
		
		ShapedRecipe recipe = new ShapedRecipe(CraftItemStack.asBukkitCopy(spigotItemStack));
		recipe.shape("0w0", "0w0", "0t0");
		recipe.setIngredient('w', Material.DIAMOND);
		recipe.setIngredient('t', Material.EMERALD);
		getServer().addRecipe(recipe);
		
		getServer().getPluginManager().registerEvents(new TestListener(), this);
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new TestTask(), 0, 2);
	}
	
	public void onDisable()
	{
		
	}

}
