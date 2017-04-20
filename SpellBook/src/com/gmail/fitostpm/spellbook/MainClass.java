package com.gmail.fitostpm.spellbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.fitostpm.spellbook.listeners.ClickListener;
import com.gmail.fitostpm.spellbook.listeners.InventoryEventsListeners;
import com.gmail.fitostpm.spellbook.listeners.TargetSelection;
import com.gmail.fitostpm.spellbook.spells.EnumSpell;
import com.gmail.fitostpm.spellbook.tasks.TargetSelector;

import net.minecraft.server.v1_11_R1.NBTTagCompound;

public class MainClass extends JavaPlugin
{
	public static MainClass Instance;
	public static List<HumanEntity> SpellChooseDialog = new ArrayList<HumanEntity>();
	public static HashMap<Player, TargetSelector> CastingPlayers = new HashMap<Player, TargetSelector>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("givesb"))
		{
			if(args.length == 0 && sender instanceof Player)
			{
				GiveBook((Player)sender);
				return true;				
			}
			else if(args.length == 1 && getServer().getPlayer(args[0]) != null)
			{
				GiveBook(getServer().getPlayer(args[0]));
				return true;	
			}					
				
		}
				
		return false;
	}

	@Override
	public void onEnable()
	{
		Instance = this;
		RegisterEvents(new ClickListener(), new InventoryEventsListeners(), new TargetSelection());
	}

	@Override
	public void onDisable()
	{
		
	}
	
	public void GiveBook(Player player)
	{
		NBTTagCompound tags = new NBTTagCompound();
		tags.setString("SpellBookOwner", player.getName());
		NBTTagCompound spells = new NBTTagCompound();
		for(EnumSpell s : EnumSpell.values())
			spells.setInt(s.getTag(), 1);	
		tags.set("SpellBookSpells", spells);
		ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
		net.minecraft.server.v1_11_R1.ItemStack nmsBook = CraftItemStack.asNMSCopy(book);
		nmsBook.setTag(tags);
		book = CraftItemStack.asBukkitCopy(nmsBook);
		ItemMeta meta = book.getItemMeta();
		meta.setDisplayName("Spell Book");
		book.setItemMeta(meta);
		player.getWorld().dropItem(player.getLocation(), book);
	}
	
	public void RegisterEvents(Listener... listeners)
	{
		for(Listener l : listeners)
			getServer().getPluginManager().registerEvents(l, this);
	}
}
