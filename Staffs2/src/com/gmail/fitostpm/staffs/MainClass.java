package com.gmail.fitostpm.staffs;

import com.gmail.fitostpm.staffs.listeners.AttackListeners;
import com.gmail.fitostpm.staffs.listeners.MissilesHitsListeners;
import com.gmail.fitostpm.staffs.listeners.PlayerDropItemsListeners;
import com.gmail.fitostpm.staffs.listeners.PlayerItemHeldListeners;
import com.gmail.fitostpm.staffs.listeners.RightClickListener;
import com.gmail.fitostpm.staffs.listeners.TestListener;
import com.gmail.fitostpm.staffs.tasks.Cast;
import com.gmail.fitostpm.staffs.tasks.Charging;
import com.gmail.fitostpm.staffs.tasks.CritEffect;
import com.gmail.fitostpm.staffs.util.TryParse;
import com.gmail.fitostpm.staffs.config.ConfigLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class MainClass extends JavaPlugin
{
	//Map of messages that appears at player's screen, could be customized through messages.yml
	public static HashMap<String,String> Msgs;
	
	//Map of placeholders such as 'damage' or 'durability'
	public static HashMap<String,String> Plcholders;
	
	//Map of missiles and staff which created them
	public static HashMap<Projectile,Staff> LaunchedMissiles = new HashMap<Projectile,Staff>();	
	
	//Map of players that casting a charge and an instance of class that responsible for the process
	public static HashMap<Player,Cast> CastingPlayers = new HashMap<Player,Cast>();
	
	//Map of staff's types and the staffs itself
	public static HashMap<String,ItemStack> commands = new HashMap<String,ItemStack>();
	
	//Command for book with info
	public static String infocommand;
	
	//Instance of plugin
	public static MainClass instance;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("getstaff") && sender instanceof Player 
				&& ((Player)sender).hasPermission("staffs.get"))
		{
			switch (args.length)
			{
			case 1:
				if(commands.keySet().contains(args[0]) && sender instanceof Player)
				{
					GiveStaff((Player)sender,args[0],1);
					return true;
				}
				break;
			case 2:
				if(commands.keySet().contains(args[0]) && sender instanceof Player && TryParse.toInt(args[1]))
				{
					GiveStaff((Player)sender,args[0],Integer.parseInt(args[1]));
					return true;
				}
				else if(commands.keySet().contains(args[1]))
				{
					for(Player p : getServer().getOnlinePlayers())
						if(p.getDisplayName().equals(args[0]))
						{
							GiveStaff(p,args[1],1);
							return true;
						}		
				}	
				break;			
			case 3:
				if(commands.keySet().contains(args[1]) && TryParse.toInt(args[2]))
				{
					for(Player p : getServer().getOnlinePlayers())
						if(p.getDisplayName().equals(args[0]))
						{
							GiveStaff(p,args[1],Integer.parseInt(args[2]));
							return true;
						}						
				}	
				break;					
			}
		}
		
		else if(cmd.getName().equalsIgnoreCase("givestaff") 
				&& (sender instanceof Player && ((Player)sender).hasPermission("staffs.get")
						|| sender instanceof ConsoleCommandSender))
		{
			switch(args.length)
			{
			case 2:
				if(commands.keySet().contains(args[1]))
				{
					for(Player p : getServer().getOnlinePlayers())
						if(p.getDisplayName().equals(args[0]))
						{
							GiveStaff(p,args[1],1);
							return true;
						}		
				}	
				break;	
			case 3:
				if(commands.keySet().contains(args[1]) && TryParse.toInt(args[2]))
				{
					for(Player p : getServer().getOnlinePlayers())
						if(p.getDisplayName().equals(args[0]))
						{
							GiveStaff(p,args[1],Integer.parseInt(args[2]));
							return true;
						}						
				}	
				break;
			}
		}
		

		else if(cmd.getName().equalsIgnoreCase("repairstaff") && sender instanceof Player 
				&& ((Player)sender).hasPermission("staffs.repair"))
		{
			if(args.length == 0)
			{
				//Repair((Player)sender, ((Player)sender).getItemInHand());
				Repair((Player)sender, ((Player)sender).getInventory().getItemInMainHand());
				return true;
			}
		}
		
		else if(cmd.getName().equalsIgnoreCase("repairstaff") && sender instanceof ConsoleCommandSender)
		{
			if(args.length == 1)
			{
				for(Player p : getServer().getOnlinePlayers())
					if(p.getDisplayName().equals(args[0]))
					{
						//Repair(p, p.getItemInHand());
						Repair(p, p.getInventory().getItemInMainHand());
						return true;
					}		
			}
		}
		
		else if(cmd.getName().equalsIgnoreCase("staffsinfo") && sender instanceof Player)
		{
			if(args.length == 0)
			{
				GetBook((Player)sender);
				return true;
			}			
		}
		
		else if(cmd.getName().equalsIgnoreCase("screload") && sender instanceof Player 
				&& ((Player)sender).hasPermission("staffs.configreload"))
		{
			if(args.length == 0)
			{
				ConfigLoader.LoadConfigs(this);
				return true;
			}				
		}
		
		return false;				
	}
	
	public void onEnable()
	{				
		ConfigLoader.LoadConfigs(this);
		instance = this;
		infocommand = GenerateBook();

		getServer().getPluginManager().registerEvents(new AttackListeners(), this);
		getServer().getPluginManager().registerEvents(new MissilesHitsListeners(), this);
		getServer().getPluginManager().registerEvents(new RightClickListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerItemHeldListeners(), this);
		getServer().getPluginManager().registerEvents(new PlayerDropItemsListeners(), this);
		getServer().getPluginManager().registerEvents(new TestListener(), this);
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new CritEffect(), 0, 2);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Charging(), 0, 2);
	}
	
	public void onDisable()	{ }
	
	public void GiveStaff(Player p, String s, int i)
	{
		ItemStack staff = commands.get(s);
		staff.setAmount(i);
		p.getWorld().dropItem(p.getLocation(), staff);
	}
	
	public void Repair(Player player, ItemStack item)
	{
		//if(Staff.isStaff(player.getItemInHand()))
		if(Staff.isStaff(player.getInventory().getItemInMainHand()))
		{
			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.getLore();
			String[] alore = new String[lore.size()];
			lore.toArray(alore);
			int i = 0;
			while(i < alore.length && alore[i].indexOf(ChatColor.GRAY + "Durability:") == -1)
				i++;
			int endIndex = alore[i].indexOf('/');
			int maxdurability = Integer.parseInt(alore[i].substring(endIndex + 1));
			int currentdurability = maxdurability;
			alore[i] = ChatColor.GRAY + "Durability: " + currentdurability + "/" + maxdurability;
			lore = new ArrayList<String>();
			for(String s : alore)
				lore.add(s);
			meta.setLore(lore);
			item.setItemMeta(meta);
			player.sendMessage(ChatColor.GREEN + Msgs.get("repairsuccess"));
		}
		else
			player.sendMessage(ChatColor.RED + Msgs.get("notstaffinhand"));
	}
	
	public boolean tryParseInt(String value)
	{
		try {
			Integer.parseInt(value);
			return true;
		}
		catch (NumberFormatException e)	{
			return false;
		}
	}
	
	public String GenerateBook()
	{
		String title = ChatColor.AQUA + "Info about staffs";
		
		String author = ChatColor.GOLD + "Fitos";
		
		String[] page = new String[11];
		
		page[0] = ChatColor.GOLD +"  Content\n" + ChatColor.DARK_PURPLE + "I. Introduction\n" + ChatColor.DARK_PURPLE 
				+ "II. Type of staffs\n" + ChatColor.DARK_PURPLE + "III. Characteristics\n" + ChatColor.DARK_PURPLE 
				+ "IV. Recipies";
		
		page[1] = ChatColor.DARK_PURPLE +"I. Introduction\nStaff - new type of distance weapon tha"
				+ "t allow you to shoot your enemies with charges of magic energy. \n\nStaff behaves like an ordinary wea"
				+ "pon. It lose its durability with each attack and will broke when durability turns 0.";
		
		page[2] = "Due to impossibility of displaying durability indicator it makes sound of broken glass when durability"
				+ " become lower than 10% of a maximum and also message you that your staff needs to be repaired.\n\n"
				+ "To repair your staff type /repairstaff.";
		
		page[3] = ChatColor.DARK_PURPLE +"II. Type of staffs\n1. Wooden Staff\n  " + ChatColor.BLUE 
				+ "Damage 5\n  " + ChatColor.GRAY + "Durability 250\n  " + ChatColor.RED + "Exhaustion 3.1\n  " 
				+ ChatColor.GREEN + "Max Distance 39\n\n2. Bone Staff\n  " + ChatColor.BLUE + "Damage 6\n  " 
				+ ChatColor.GRAY + "Durability 400\n  " + ChatColor.RED + "Exhaustion 2.8\n  " + ChatColor.GREEN 
				+ "Max Distance 46";
		
		page[4] = "3. Amber Staff\n  " + ChatColor.BLUE + "Damage 8\n  " + ChatColor.GRAY + "Durability 700\n  " 
				+ ChatColor.RED + "Exhaustion 2.5\n  " + ChatColor.GREEN + "Max Distance 60";
		
		page[5] = ChatColor.DARK_PURPLE + "III. Characteristics\n" + ChatColor.BLUE + "Damage\n  "
				+ "Only charges deal actual damage. Left-click with a staff in hand is equivalent to barehanded left-cl"
				+ "ick. When your food level is 90% of a maximum or higher you will deal more damage.";
		
		page[6] = "Otherwise having food level equivalent to 30 percent of a maximum or lower will decrease your damage."
				+ "\n\n" + ChatColor.GRAY + "Durability\n  Number of charges that sraff can launch.";
		
		page[7] = ChatColor.RED + "Exhaustion\n  Any magic takes a lot of power from its caster. The higher this paramete"
				+ "r staff has - the sooner you get hunger while using it.\n\n" + ChatColor.GREEN + "Max Distance\n  The "
				+ "longest distance that charge could reach.";
		
		page[8] = ChatColor.DARK_PURPLE + "IV. Recipies\nWooden staff"
				+ "\n| I | E | I |"
				+ "\n| I | S | I |"
				+ "\n| N | S | N |"
				+ "\nWhere 'I' -" + ChatColor.GRAY + " iron ingot"
				+ "\n      'E' -" + ChatColor.GREEN + " emerald"
				+ "\n      'S' -" + ChatColor.DARK_GRAY + " stick"
				+ "\n      'N' - nothing";
		
		page[9] = "\nBone staff"
				+ "\n| I | D | I |"
				+ "\n| I | B | I |"
				+ "\n| N | B | N |"
				+ "\nWhere 'I' -" + ChatColor.GRAY + " iron ingot"
				+ "\n      'D' -" + ChatColor.AQUA + " diamod"
				+ "\n      'B' -" + ChatColor.WHITE + " bone"
				+ "\n      'N' - nothing";
		
		page[10] = "Amber staff"
				+ "\n| G | D | G |"
				+ "\n| G | B | G |"
				+ "\n| N | B | N |"
				+ "\nWhere 'G' -" + ChatColor.YELLOW + " gold ingot"
				+ "\n      'D' -" + ChatColor.AQUA + " diamod"
				+ "\n      'B' -" + ChatColor.GOLD + " blaze rod"
				+ "\n      'N' - nothing";
		
		return "minecraft:give @p written_book 1 0 {title:" + title + ",author:" + author
				+ ",pages:[" + page[0] + "," + page[1] + "," + page[2] + "," + page[3] + "," + page[4] + "," 
				+ page[5] + "," + page[6] + "," + page[7] + "," + page[8] + "," + page[9] + "," + page[10] +"]}";
	}
	
	public void GetBook(Player player)
	{
		getServer().dispatchCommand(player, infocommand);
	}
	
}
