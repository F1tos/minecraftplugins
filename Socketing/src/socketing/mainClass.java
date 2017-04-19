package socketing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class mainClass extends JavaPlugin
{
	static private HashMap<String,String> commands = new HashMap<String,String>();
	static public List<Projectile> duplicated = new ArrayList<Projectile>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("addsocket") && sender instanceof Player)
		{
			AddSocketCommand((Player) sender);
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("insertgem") && sender instanceof Player)
		{
			InsertGem((Player) sender);
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("giveall") && sender instanceof Player && ((Player)sender).isOp())
		{
			switch (args.length)
			{
			case 0:
				GiveAll((Player) sender);
				return true;
			case 1:
				for(Player p : getServer().getOnlinePlayers())
					if(p.getDisplayName().equals(args[0]))
					{
						GiveAll(p);
						return true;
					}
				break;
			}
			return false;
		}
		
		if(cmd.getName().equalsIgnoreCase("givegem") && sender instanceof Player && ((Player)sender).isOp())
		{
			switch (args.length)
			{
			case 2:
				if(commands.keySet().contains(args[0]) && tryParseInt(args[1]))
				{
					GiveGem((Player) sender, args[0], Integer.parseInt(args[1]));
					return true;
				}
			case 3:
				if(commands.keySet().contains(args[1]) && tryParseInt(args[2]))
				{
					for(Player p : getServer().getOnlinePlayers())
						if(p.getDisplayName().equals(args[0]))
						{
							GiveGem(p, args[1], Integer.parseInt(args[2]));
							return true;
						}
				}
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("givechisel") && sender instanceof Player && ((Player)sender).isOp())
		{
			switch (args.length)
			{
			case 1:
				if(tryParseInt(args[0]))
				{
					GiveChisel((Player) sender, Integer.parseInt(args[0]));
					return true;
				}
			case 2:
				if(tryParseInt(args[1]))
				{
					for(Player p : getServer().getOnlinePlayers())
						if(p.getDisplayName().equals(args[0]))
						{
							GiveChisel(p, Integer.parseInt(args[1]));
							return true;
						}
				}
				
			}
		}
		return false;		
		
	}
	
	public void GenerateCommands()
	{
		String name = ChatColor.WHITE + "�������� ������";
		String[] lore = {
				ChatColor.GRAY + "----����� ���� �������� �----",
				ChatColor.GRAY + "  - �����",
				ChatColor.GRAY + "-----------------------------",
				ChatColor.GRAY + "�������� ������ �������.","","","","","","",""
		};
		String cmd = "minecraft:give @p nether_star * 0 {display:{Name:" + name + ",Lore:[" + lore[0] + "," + lore[1] + "," + lore[2] + "," + lore[3] + "]},ench:[{id:34,lvl:1}],HideFlags:1}";
		commands.put("lightning",cmd);
		Gem.Gems.add(ChatColor.WHITE + "�������� ������");
		
		name = ChatColor.GREEN + "�������� ��������";
		lore[0] = ChatColor.GRAY + "----����� ���� �������� �----";
		lore[1] = ChatColor.GRAY + "  - ���";
		lore[2] = ChatColor.GRAY + "  - �����";
		lore[3] = ChatColor.GRAY + "-----------------------------";
		lore[4] = ChatColor.GRAY + "��������� ������.";
		cmd = "minecraft:give @p emerald * 0 {display:{Name:" + name + ",Lore:[" + lore[0] + "," + lore[1] + "," + lore[2] + "," + lore[3] + "," + lore[4] + "]},ench:[{id:34,lvl:1}],HideFlags:1}";
		commands.put("poison",cmd);		
		Gem.Gems.add(ChatColor.GREEN + "�������� ��������");
		
		name = ChatColor.WHITE + "������ ��������";
		lore[0] = ChatColor.GRAY + "----����� ���� �������� �----";
		lore[1] = ChatColor.GRAY + "  - ���";
		lore[2] = ChatColor.GRAY + "-----------------------------";
		lore[3] = ChatColor.GRAY + "������� ������������� ������� � ������.";
		cmd = "minecraft:give @p ghast_tear * 0 {display:{Name:" + name + ",Lore:[" + lore[0] + "," + lore[1] + "," + lore[2] + "," + lore[3] + "]},ench:[{id:34,lvl:1}],HideFlags:1}";
		commands.put("purge",cmd);
		Gem.Gems.add(ChatColor.WHITE + "������ ��������");
		
		name = ChatColor.AQUA + "���������� ������";
		lore[0] = ChatColor.GRAY + "----����� ���� �������� �----";
		lore[1] = ChatColor.GRAY + "  - ���";
		lore[2] = ChatColor.GRAY + "-----------------------------";
		lore[3] = ChatColor.GRAY + "����������� �������� ����� ������.";
		cmd = "minecraft:give @p string * 0 {display:{Name:" + name + ",Lore:[" + lore[0] + "," + lore[1] + "," + lore[2] + "," + lore[3] + "]},ench:[{id:34,lvl:1}],HideFlags:1}";
		commands.put("emstring",cmd);
		Gem.Gems.add(ChatColor.AQUA + "���������� ������");
		
		name = ChatColor.DARK_GRAY + "�������� ����������";
		lore[0] = ChatColor.GRAY + "----����� ���� �������� �----";
		lore[1] = ChatColor.GRAY + "  - ���";
		lore[2] = ChatColor.GRAY + "  - �����";
		lore[3] = ChatColor.GRAY + "-----------------------------";
		lore[4] = ChatColor.GRAY + "�������� ������.";
		cmd = "minecraft:give @p coal * 0 {display:{Name:" + name + ",Lore:[" + lore[0] + "," + lore[1] + "," + lore[2] + "," + lore[3] + "," + lore[4] + "]},ench:[{id:34,lvl:1}],HideFlags:1}";
		commands.put("wither",cmd);
		Gem.Gems.add(ChatColor.DARK_GRAY + "�������� ����������");
		
		name = ChatColor.YELLOW + "��������� ��������";
		lore[0] = ChatColor.GRAY + "----����� ���� �������� �----";
		lore[1] = ChatColor.GRAY + "  - ���";
		lore[2] = ChatColor.GRAY + "  - �����";
		lore[3] = ChatColor.GRAY + "-----------------------------";
		lore[4] = ChatColor.GRAY + "25% ���� �������� �����.";
		cmd = "minecraft:give @p gold_nugget * 0 {display:{Name:" + name + ",Lore:[" + lore[0] + "," + lore[1] + "," + lore[2] + "," + lore[3] + "," + lore[4] + "]},ench:[{id:34,lvl:1}],HideFlags:1}";
		commands.put("sun",cmd);
		Gem.Gems.add(ChatColor.YELLOW + "��������� ��������");
		
		name = ChatColor.BLUE + "��������� ��������";
		lore[0] = ChatColor.GRAY + "----����� ���� �������� �----";
		lore[1] = ChatColor.GRAY + "  - �����";
		lore[2] = ChatColor.GRAY + "-----------------------------";
		lore[3] = ChatColor.GRAY + "����� ������� ����� � ����� �����";
		lore[4] = ChatColor.GRAY + "������� 40% �����.";
		cmd = "minecraft:give @p dye * 8 {display:{Name:" + name + ",Lore:[" + lore[0] + "," + lore[1] + "," + lore[2] + "," + lore[3] + "," + lore[4] + "]},ench:[{id:34,lvl:1}],HideFlags:1}";
		commands.put("massive",cmd);
		Gem.Gems.add(ChatColor.BLUE + "��������� ��������");
		
		name = ChatColor.DARK_PURPLE + "������ ��������";
		lore[0] = ChatColor.GRAY + "----����� ���� �������� �----";
		lore[1] = ChatColor.GRAY + "  - ���";
		lore[2] = ChatColor.GRAY + "  - �����";
		lore[3] = ChatColor.GRAY + "-----------------------------";
		lore[4] = ChatColor.GRAY + "�������� ���� ����� ������.";
		cmd = "minecraft:give @p slime_ball * 0 {display:{Name:" + name + ",Lore:[" + lore[0] + "," + lore[1] + "," + lore[2] + "," + lore[3] + "," + lore[4] + "]},ench:[{id:34,lvl:1}],HideFlags:1}";
		commands.put("weakness",cmd);
		Gem.Gems.add(ChatColor.DARK_PURPLE + "������ ��������");
		
		name = ChatColor.DARK_RED + "������ ��������";
		lore[0] = ChatColor.GRAY + "----����� ���� �������� �----";
		lore[1] = ChatColor.GRAY + "  - ���";
		lore[2] = ChatColor.GRAY + "  - �����";
		lore[3] = ChatColor.GRAY + "-----------------------------";
		lore[4] = ChatColor.GRAY + "25% ���� ������� �� 50% ������ �����,";
		lore[5] = ChatColor.GRAY + "� ����� ��������� �����.";
		cmd = "minecraft:give @p prismarine_shard * 0 {display:{Name:" + name + ",Lore:[" + lore[0] + "," + lore[1] + "," + lore[2] + "," + lore[3] + "," + lore[4] + "," + lore[5] + "]},ench:[{id:34,lvl:1}],HideFlags:1}";
		commands.put("sharp",cmd);	
		Gem.Gems.add(ChatColor.DARK_RED + "������ ��������");
		
		name = ChatColor.LIGHT_PURPLE + "������ �������";
		lore[0] = ChatColor.GRAY + "----����� ���� �������� �----";
		lore[1] = ChatColor.GRAY + "  - ���";
		lore[2] = ChatColor.GRAY + "  - �����";
		lore[3] = ChatColor.GRAY + "-----------------------------";
		lore[4] = ChatColor.GRAY + "������������ ������ ���������� �������";
		lore[5] = ChatColor.GRAY + "� ���� �������� �� 3 �������.";
		cmd = "minecraft:give @p prismarine_crystals * 0 {display:{Name:" + name + ",Lore:[" + lore[0] + "," + lore[1] + "," + lore[2] + "," + lore[3] + "," + lore[4] + "," + lore[5] + "]},ench:[{id:34,lvl:1}],HideFlags:1}";
		commands.put("decay",cmd);
		Gem.Gems.add(ChatColor.LIGHT_PURPLE + "������ �������");
	
	}
	
	public void onEnable()
	{
		GenerateCommands();
		//======================================================================================
		//=========================������ ��������� ��� ��������� ������========================
		//======================================================================================
		
		Gem.Swords.add(Material.WOOD_SWORD);
		Gem.Swords.add(Material.STONE_SWORD);
		Gem.Swords.add(Material.IRON_SWORD);
		Gem.Swords.add(Material.GOLD_SWORD);
		Gem.Swords.add(Material.DIAMOND_SWORD);

		Gem.Axes.add(Material.WOOD_AXE);
		Gem.Axes.add(Material.STONE_AXE);
		Gem.Axes.add(Material.IRON_AXE);
		Gem.Axes.add(Material.GOLD_AXE);
		Gem.Axes.add(Material.DIAMOND_AXE);
		
		Gem.Chestplates.add(Material.LEATHER_CHESTPLATE);
		Gem.Chestplates.add(Material.GOLD_CHESTPLATE);
		Gem.Chestplates.add(Material.IRON_CHESTPLATE);
		Gem.Chestplates.add(Material.CHAINMAIL_CHESTPLATE);
		Gem.Chestplates.add(Material.DIAMOND_CHESTPLATE);
				
		getServer().getPluginManager().registerEvents(new Menu(), this);
					
		getServer().getPluginManager().registerEvents(new EmeraldString(), this);
		getServer().getPluginManager().registerEvents(new DecayWeapons(), this);
		getServer().getPluginManager().registerEvents(new GravityWeapons(), this);
		getServer().getPluginManager().registerEvents(new LightningWeapons(), this);
		getServer().getPluginManager().registerEvents(new MassiveWeapons(), this);
		getServer().getPluginManager().registerEvents(new PoisonWeapons(), this);
		getServer().getPluginManager().registerEvents(new PurgeWeapons(), this);
		getServer().getPluginManager().registerEvents(new SharpWeapons(), this);
		getServer().getPluginManager().registerEvents(new SunWeapons(), this);
		getServer().getPluginManager().registerEvents(new WeaknessWeapons(), this);
		getServer().getPluginManager().registerEvents(new WitherWeapons(), this);		
		
	}
	
	public void onDisable()
	{
		
	}
	
	//======================================================================================
	//===================================���������� �����===================================
	//======================================================================================
	public boolean AddSocketCommand(Player player)
	{
		ItemStack item = player.getItemInHand();
		if(Gem.isSocketable(item))
		{
			Inventory inv = player.getInventory();
			ItemStack chisel = new ItemStack(Material.FLINT);
			chisel.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
			ItemMeta im = chisel.getItemMeta();
			im.setDisplayName(ChatColor.AQUA + "��������");
			List<String> lorelist = new ArrayList<String>();
			lorelist.add(ChatColor.GRAY + "������������ ��� ���������� ������ ������ � ���� ������");
			im.setLore(lorelist);
			chisel.setItemMeta(im);
			if(inv.containsAtLeast(chisel,1))
			{
				if(AddNewSocket(item))
				{
					RemoveOne(inv,chisel);
					player.sendMessage("������ ���� ��� ������� �������� � ����� ��������");
					return true;
				}
				else
					player.sendMessage(ChatColor.RED + "� ����� �������� ������ ������ �������� ������ �����");
			}
			else
				player.sendMessage(ChatColor.RED + "� ��� ��� �� ����� ��������");
		}
		else
			player.sendMessage(ChatColor.RED + "� ����� �������� ������ �������� ������ ������");
		return false;		
	}
	
	public boolean AddNewSocket(ItemStack item)
	{
		ItemMeta meta = item.getItemMeta();
		if(meta.hasLore())
		{
			List<String> lore = meta.getLore();
			if (NumberOfSlots(lore) < 3)
			{
				lore = ChangeLore(lore,NumberOfSlots(lore));
				meta.setLore(lore);
				item.setItemMeta(meta);
				return true;
			}
			else
				return false;
		}
		else
		{
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "------���������------");
			lore.add(ChatColor.GRAY + "<������ ����>");
			lore.add(ChatColor.GRAY + "---------------------");
			meta.setLore(lore);
			item.setItemMeta(meta);
			return true;
		}
	}
	
	public List<String> ChangeLore(List<String> lore, int n)
	{
		if (n == 0)
		{
			List<String> newlore = new ArrayList<String>();
			newlore.add(ChatColor.GRAY + "------���������------");
			newlore.add(ChatColor.GRAY + "<������ ����>");
			newlore.add(ChatColor.GRAY + "---------------------");
			newlore.addAll(lore);
			lore = newlore;
		}
		else
		{
			String[] alore = new String[lore.size()];
			lore.toArray(alore);
			lore = new ArrayList<String>();
			for(int i=0; i<alore.length; i++)
			{
				if(alore[i].equals(ChatColor.GRAY + "---------------------"))
					lore.add(ChatColor.GRAY + "<������ ����>");
				lore.add(alore[i]);
			}		
		}
		return lore;	
	}
	
	public int NumberOfSlots(List<String> lore)
	{
		String[] alore = new String[lore.size()];
		int i1=0,i2=0;
		lore.toArray(alore);
		while(i1<alore.length && !alore[i1].equals(ChatColor.GRAY + "------���������------"))
			i1++;
		if(i1 == alore.length)
			return 0;
		i2=i1;
		while(i2<alore.length && !alore[i2].equals(ChatColor.GRAY + "---------------------"))
			i2++;
		return Math.max(i2-i1-1, 0);
	}
	
	//======================================================================================
	//===================================������� ����=======================================
	//======================================================================================
	
	public boolean InsertGem(Player player)
	{
		ItemStack gemitemstack = player.getItemInHand();
		ItemStack weapon = player.getInventory().getItem(0);
		if(Gem.isGem(gemitemstack))
		{
			Gem gem = new Gem(gemitemstack);
			if(Gem.isInsertable(gem, weapon))
			{
				ItemMeta meta = weapon.getItemMeta();
				if(meta.hasLore())
				{
					List<String> lore = meta.getLore();
					for(int i=0; i<lore.size(); i++)
					{
						if(lore.get(i).equals(gem.Name))
						{
							player.sendMessage(ChatColor.RED + "����� �������� ��� �������� � ������� � ������ �����");
							return false;
						}
						if(lore.get(i).equals(ChatColor.GRAY + "<������ ����>"))
						{
							lore.set(i, gem.Name);
							meta.setLore(lore);
							weapon.setItemMeta(meta);
							ItemStack onegem = new ItemStack(gemitemstack);
							onegem.setAmount(1);
							RemoveOne(player.getInventory(), onegem);
							player.sendMessage("�������� ��� ������� �������� � �������� � ������ �����");
							return true;
						}	
					}
				}
				player.sendMessage(ChatColor.RED + "��� ������ ������");
			}
			else
				player.sendMessage(ChatColor.RED + "����������� � �������� � ������ �����");
		}
		else
			player.sendMessage(ChatColor.RED + "������� � ���� �� �������� �����");
		return false;
	}

	//======================================================================================
	//=================================�������� ��������====================================
	//======================================================================================

	
	public void RemoveOne(Inventory inv, ItemStack item)
	{
		int slot = getSlot(inv,item);
		int amount = inv.getItem(slot).getAmount();
		if(amount == 1)
			inv.setItem(slot, new ItemStack(Material.AIR));
		else
		{
			item.setAmount(amount-1);
			inv.setItem(slot, item);
		}	
	}
	
	public int getSlot(Inventory inv, ItemStack item)
	{
		int i;
		for(i=1; i<65; i++)
		{
			item.setAmount(i);
			if(inv.contains(item))
				return inv.first(item);
		}
		return 0;
	}

	//======================================================================================
	//=================================��������� ��������===================================
	//======================================================================================
	
	public void GiveAll(Player p)
	{
		GiveChisel(p,64);
		for(String s : commands.keySet())
			GiveGem(p,s,64);
	}
	
	public void GiveGem(Player p, String s, int i)
	{
		String cmd = commands.get(s);
		getServer().dispatchCommand(p, cmd.substring(0,cmd.indexOf('*')) + i + cmd.substring(cmd.indexOf('*')+1));
	}
	
	public void GiveChisel(Player p, int i)
	{
		ItemStack chisel = new ItemStack(Material.FLINT);
		chisel.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		ItemMeta im = chisel.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "��������");
		List<String> lorelist = new ArrayList<String>();
		lorelist.add(ChatColor.GRAY + "������������ ��� ���������� ������ ������ � ���� ������");
		im.setLore(lorelist);
		chisel.setItemMeta(im);
		chisel.setAmount(i);
		p.getWorld().dropItem(p.getLocation(), chisel);
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
}




