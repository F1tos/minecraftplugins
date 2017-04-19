package auras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.inventivegames.particle.ParticleEffect;

public class mainClass extends JavaPlugin implements CommandExecutor
{
	public HashMap<Player, Aura> AurhjaMap;
	public HashMap<String, ParticleEffect> Particles;
	public List<String> Types;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("addaura") && sender instanceof Player 
				&& ((Player)sender).hasPermission("auras.add"))
		{
			Player player = (Player) sender;
			switch (args.length)
			{				
			case 4:
				if(Particles.containsKey(args[0].toLowerCase()) && Types.contains(args[1])
						&& tryParseDouble(args[2]) && tryParseDouble(args[3]))
				{
					if(args[1].equals("sphere"))
					{
						ParticleEffect effect = Particles.get(args[0].toLowerCase());
						double height = Double.parseDouble(args[2]);
						double radius = Double.parseDouble(args[3]);
						Sphere sphere = new Sphere(player, effect, height, radius);
						AuraMap.put(player, sphere);
						return true;
					}
				}
				
				else 
					return false;
				break;
				
			case 5:
				if(Particles.containsKey(args[0].toLowerCase()) && Types.contains(args[1])
						&& tryParseDouble(args[2]) && tryParseDouble(args[3]) && args[4].length()==1)
				{
					char[] c = args[4].toCharArray();
					if(args[1].equals("circle") && (c[0]=='x' || c[0]=='y' || c[0]=='z'))
					{
						ParticleEffect effect = Particles.get(args[0].toLowerCase());
						double height = Double.parseDouble(args[2]);
						double radius = Double.parseDouble(args[3]);
						Circle circle = new Circle(player, effect, height, radius, c[0]);
						AuraMap.put(player, circle);
						return true;
					} else if(args[1].equals("hypocycloid") && (c[0]=='x' || c[0]=='y' || c[0]=='z'))
					{
						ParticleEffect effect = Particles.get(args[0].toLowerCase());
						double height = Double.parseDouble(args[2]);
						double radius = Double.parseDouble(args[3]);
						Hypocycloid hypocycloid = new Hypocycloid(player, effect, height, radius, c[0]);
						AuraMap.put(player, hypocycloid);
						return true;
					} else return false;
				}
				break;
				
			case 6:
				if(Particles.containsKey(args[0].toLowerCase()) && Types.contains(args[1])
						&& tryParseDouble(args[2]) && tryParseDouble(args[3])  && tryParseDouble(args[4])  && tryParseDouble(args[5]))
				{
					if(args[1].equals("spirit"))
					{
						ParticleEffect effect = Particles.get(args[0].toLowerCase());
						double minh = Double.parseDouble(args[2]);
						double maxh = Double.parseDouble(args[3]);
						double minr = Double.parseDouble(args[4]);
						double maxr = Double.parseDouble(args[5]);
						Spirit spirit = new Spirit(player, effect, minh, maxh, minr, maxr);
						AuraMap.put(player, spirit);
						return true;
					}
				}
				break;
			default:
				return false;
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("removeauras") && sender instanceof Player)
		{
			AuraMap.remove((Player) sender);
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("allparticles") && sender instanceof Player)
		{
			((Player)sender).sendMessage("Все доступные частицы: ");
			String msg = "";
			for(String s : Particles.keySet())	
			{
				msg += s;
				msg += ", ";
			}
			((Player)sender).sendMessage(msg.substring(0,msg.lastIndexOf(',')));
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("alltypes") && sender instanceof Player)
		{
			Player player = (Player) sender;
			for(String s : Types)
			{
				if(s.equals("sphere")) player.sendMessage(s + " параметы: высота, радиус");
				if(s.equals("hypocycloid") || s.equals("circle")) player.sendMessage(s + " параметры: высота, радиус, ось (x, y, или z)");
				if(s.equals("spirit")) player.sendMessage(s + " параметры: минимальная высота, максимальная высота, минимальный радиус, максимальный радиус");
			}
			return true;
		}
			
		return false;
	}	
	
	public void onEnable()
	{
		
		Particles = new HashMap<String, ParticleEffect>();
		for(ParticleEffect p : ParticleEffect.values())
			Particles.put(p.toString().toLowerCase(), p);
		
		Types = new ArrayList<String>();
		Types.add("circle");
		Types.add("hypocycloid");
		Types.add("spirit");
		Types.add("sphere");
		
		AuraMap = new HashMap<Player, Aura>();
		getServer().getPluginManager().registerEvents(new QuitListeners(AuraMap), this);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			@Override
			public void run() 
			{
				Set<Player> players = AuraMap.keySet();
				for(Player p : players)
				{
					AuraMap.get(p).show();
				}
			}			
		}, 0, 2);
	}
	
	public void onDisable()
	{
		
	}
	
	public boolean tryParseDouble(String value)
	{
		try {
			Double.parseDouble(value);
			return true;
		}
		catch (NumberFormatException e)	{
			return false;
		}
	}

}
