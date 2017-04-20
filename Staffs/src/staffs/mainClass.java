package staffs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class mainClass extends JavaPlugin
{
	public static HashMap<String,String> Messages = new HashMap<String,String>();
	public static HashMap<Projectile,Staff> LaunchedMissiles = new HashMap<Projectile,Staff>();	
	public static HashMap<String,ItemStack> commands = new HashMap<String,ItemStack>();
	public static String infocommand;
	public static mainClass instance;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("getstaff") && ((Player)sender).isOp())
		{
			switch (args.length)
			{
			case 1:
				if(commands.keySet().contains(args[0]) && sender instanceof Player)
				{
					GiveStaff((Player)sender,args[0],1);
					return true;
				}
			case 2:
				if(commands.keySet().contains(args[0]) && sender instanceof Player && tryParseInt(args[1]))
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
			case 3:
				if(commands.keySet().contains(args[1]) && tryParseInt(args[2]))
				{
					for(Player p : getServer().getOnlinePlayers())
						if(p.getDisplayName().equals(args[0]))
						{
							GiveStaff(p,args[1],Integer.parseInt(args[2]));
							return true;
						}						
				}						
			}
		}
		

		else if(cmd.getName().equalsIgnoreCase("repairstaff") && sender instanceof Player && ((Player)sender).isOp())
		{
			if(args.length == 0 && Staff.isStaff(((Player)sender).getItemInHand()))
			{
				Repair((Player)sender, ((Player)sender).getItemInHand());
				return true;
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
		
		return false;				
	}
	
	public void ConfigReader() throws IOException
	{	
		File file = new File(instance.getDataFolder(), "message.yml");
		if(!file.exists())
			instance.saveResource("message.yml", false);
		List<String> lines = new ArrayList<String>();
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		while (line != null) 
		{				
			lines.add(line);
			line = br.readLine();
		}
		if()
	}

	public void onEnable()
	{
		instance = this;
		infocommand = GenerateBook();
				
		ItemStack item = new ItemStack(Material.STICK,1);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(ChatColor.BLUE + "+5 Damage");
		lore.add(ChatColor.GRAY + "Durability: 250/250");
		lore.add(ChatColor.RED + "Exhaustion: 3.1");
		meta.setDisplayName(ChatColor.DARK_AQUA + "Wooden staff");
		meta.setLore(lore);
		item.setItemMeta(meta);
		commands.put("wood", item);
		
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("iei","isi","0s0");
		recipe.setIngredient('i', Material.IRON_INGOT);
		recipe.setIngredient('e', Material.EMERALD);
		recipe.setIngredient('s', Material.STICK);
		getServer().addRecipe(recipe);
		
		item = new ItemStack(Material.BONE,1);
		meta = item.getItemMeta();
		lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(ChatColor.BLUE + "+6 Damage");
		lore.add(ChatColor.GRAY + "Durability: 400/400");
		lore.add(ChatColor.RED + "Exhaustion: 2.8");
		meta.setDisplayName(ChatColor.BLUE + "Bone staff");
		meta.setLore(lore);
		item.setItemMeta(meta);
		commands.put("bone", item);	
		
		recipe = new ShapedRecipe(item);
		recipe.shape("idi","ibi","0b0");
		recipe.setIngredient('i', Material.IRON_INGOT);
		recipe.setIngredient('d', Material.DIAMOND);
		recipe.setIngredient('b', Material.BONE);
		getServer().addRecipe(recipe);
		
		item = new ItemStack(Material.BLAZE_ROD,1);
		meta = item.getItemMeta();
		lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(ChatColor.BLUE + "+8 Damage");
		lore.add(ChatColor.GRAY + "Durability: 700/700");
		lore.add(ChatColor.RED + "Exhaustion: 2.5");
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Amber staff");
		meta.setLore(lore);
		item.setItemMeta(meta);
		commands.put("amber", item);

		recipe = new ShapedRecipe(item);
		recipe.shape("gdg","gbg","0b0");
		recipe.setIngredient('g', Material.GOLD_INGOT);
		recipe.setIngredient('d', Material.DIAMOND);
		recipe.setIngredient('b', Material.BLAZE_ROD);
		getServer().addRecipe(recipe);
		
		getServer().getPluginManager().registerEvents(new AttackListeners(), this);
		getServer().getPluginManager().registerEvents(new MissilesHitsListeners(), this);
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			@Override
			public void run() 
			{
				for(Projectile p : mainClass.LaunchedMissiles.keySet())
					if(mainClass.LaunchedMissiles.get(p).isCrit())
					{
						float x = (float) p.getLocation().getX();
						float y = (float) p.getLocation().getY();
						float z = (float) p.getLocation().getZ();
						PacketPlayOutWorldParticles packet = 
								new PacketPlayOutWorldParticles(EnumParticle.CRIT, true, x, y, z, 0.1f, 0.1f, 0.1f, 0.0001f, 10, 1);
						((CraftPlayer) p.getShooter()).getHandle().playerConnection.sendPacket(packet);
					}
			}
			
		}, 0, 2);
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
