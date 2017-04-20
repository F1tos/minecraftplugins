package additionalweapons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.java.JavaPlugin;

import de.inventivegames.particle.ParticleEffect;
import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("deprecation")
public class mainClass extends JavaPlugin
{	
	public List<Projectile> FlyingExplGrenades = new ArrayList<Projectile>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("grenade") && sender instanceof Player && ((Player)sender).isOp())
		{
			switch (args.length)
			{
			case 1:
				if(tryParseInt(args[0]))
				{
					GiveExplGrenade((Player) sender, Integer.parseInt(args[0]));
					return true;
				}
			case 2:
				if(tryParseInt(args[1]))
				{
					for(Player p : getServer().getOnlinePlayers())
						if(p.getDisplayName().equals(args[0]))
						{
							GiveExplGrenade(p, Integer.parseInt(args[1]));
							return true;
						}
				}
				
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("giverespirator") && sender instanceof Player && ((Player)sender).isOp())
		{
			switch (args.length)
			{
			case 0:
				GiveRespirator((Player) sender);
				return true;
			case 1:
				for(Player p : getServer().getOnlinePlayers())
					if(p.getDisplayName().equals(args[0]))
					{
						GiveRespirator(p);
						return true;
					}				
			}			
		}
		
		return false;				
	}
	
	public void onEnable()
	{
		
		Runner();
		getServer().getPluginManager().registerEvents(new ThrowListener(this), this);
		getServer().getPluginManager().registerEvents(new HitListener(this), this);
	}
	
	public void onDisable()
	{
		
	}	
	
	public void GiveExplGrenade(Player player, int count)
	{	
		String grenadename = ChatColor.DARK_RED + "Осколочная граната";
		getServer().dispatchCommand(player, "minecraft:give @p firework_charge " + count + " 0 {display:{Name:" + grenadename + "},ench:[{id:16,lvl:1}],HideFlags:1}");		
	}
	
	public void GiveRespirator(Player player)
	{
		String Respiratorname = ChatColor.BLUE + "Противогаз";
		getServer().dispatchCommand(player, "minecraft:give @p skull 1 3 {display:{Name:\""+ Respiratorname +"\"},SkullOwner:{Id:\"f22cc408-f153-4244-a5f0-8a7f9fdfbdd1\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjZlMWVlZDE1ZDVhOWJmMWI1NDQ4NTY4ZGJlNTQyMzgzOTZkMzYxZTVkYTdhNWZjNTMyNjlhNTRmNzMzIn19fQ==\"}]}}}");
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
	
	public void Runner()
	{
		ParticleEffect ExplosiveGrenade = ParticleEffect.LARGE_SMOKE;
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			@Override
			public void run()
			{
				//эффект осколочной
				for(Projectile p : FlyingExplGrenades)
				{
					try {
						ExplosiveGrenade.sendToPlayers(getServer().getOnlinePlayers(), p.getLocation(), 
								0.1F, 0.1F, 0.1F, 0.0001F, 1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}, 0, 2);	
		
	}
}
