package com.gmail.fitostpm.diamondshooter;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.fitostpm.diamondshooter.config.ConfigLoader;
import com.gmail.fitostpm.diamondshooter.listeners.ArrowHitListener;
import com.gmail.fitostpm.diamondshooter.listeners.DamagedByArrowListener;
import com.gmail.fitostpm.diamondshooter.listeners.PlayerMoveListener;
import com.gmail.fitostpm.diamondshooter.tasks.VoteForStart;
import com.gmail.fitostpm.diamondshooter.util.TryParse;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class MainClass extends JavaPlugin
{
	public static HashMap<String, DSGame> SavedGames = new HashMap<String, DSGame>();
	public static HashMap<String, DSGame> NewGames = new HashMap<String, DSGame>();
	public static DSGame Game = new DSGame();
	public static VoteForStart voteforstart;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		//============================================CREATE NEW GAME============================================
		if(cmd.getName().equalsIgnoreCase("dsnew") && sender instanceof Player 
				&& ((Player)sender).hasPermission("diamondshooter.create"))
		{
			if(args.length == 1)
			{
				NewGames.put(args[0], new DSGame(args[0]));
				((Player)sender).sendMessage(ChatColor.GREEN + "Game " + args[0] + " was successfully created.");
				return true;
			}
		}
		
		

		//=========================================ADD PLAYER TO GAME============================================
		else if(cmd.getName().equalsIgnoreCase("dsaddplayer") && sender instanceof Player 
				&& ((Player)sender).hasPermission("diamondshooter.add"))
		{
			if(args.length == 1 && SavedGames.containsKey(args[0]))
			{
				SavedGames.get(args[0]).addPlayer((Player)sender);
				((Player)sender).sendMessage(ChatColor.GREEN + "Player " + ChatColor.BLUE 
						+ ((Player)sender).getDisplayName() + ChatColor.GREEN + " was successfully added to the"
						+ " game " + ChatColor.BLUE + args[0] + ".");
				return true;
			}
			else if(args.length == 2 && SavedGames.containsKey(args[0]))
			{
				for(Player player : getServer().getOnlinePlayers())
					if(player.getDisplayName().equals(args[1]))
					{
						SavedGames.get(args[0]).addPlayer(player);
						((Player)sender).sendMessage(ChatColor.GREEN + "Player " + ChatColor.BLUE
							+ ((Player)sender).getDisplayName()	+ ChatColor.GREEN + " was successfully added "
							+ "to the game " + ChatColor.BLUE + args[0] + ".");
						return true;
					}					
			}
		}
		
		

		//================================REMOVE PLAYER FROM THE GAME============================================
		else if(cmd.getName().equalsIgnoreCase("dsremoveplayer") && sender instanceof Player 
				&& ((Player)sender).hasPermission("diamondshooter.remove"))
		{
			if(args.length == 2 && SavedGames.containsKey(args[0]))
			{
				for(Player player : getServer().getOnlinePlayers())
					if(player.getDisplayName().equals(args[1]))
					{
						SavedGames.get(args[0]).removePlayer(player);
						return true;
					}					
			}			
		}

		

		//=======================================START VOTE FOR START============================================
		else if(cmd.getName().equalsIgnoreCase("dsvote") && sender instanceof Player 
				&& ((Player)sender).hasPermission("diamondshooter.vote") && isInAnyGame(((Player)sender)))
		{
			if(args.length == 0)
			{
				Player player = ((Player)sender);
				for(DSGame game : SavedGames.values())
					if(game.getPlayers().contains(player) && game.CurrentState == GameState.NOTSTARTED)
					{
						game.CurrentState = GameState.VOTING;
						for(Player p : game.getPlayers())
						{
							p.sendMessage(ChatColor.GOLD + "Player " + player.getDisplayName() + " suggest to start "
									+ "the game. Type " + ChatColor.GREEN + "/dsaccept " + ChatColor.BLUE + "if you "
									+ "agree.");
							p.sendMessage(ChatColor.RED + "Remember to save all your inventory! Your inventory will "
									+ "be cleared as game starts!");
						}
						SavedGames.get(game.getName()).createVote();
						Bukkit.getScheduler().scheduleSyncRepeatingTask(this, 
								SavedGames.get(game.getName()).CurrentTask,	0, 20);
						SavedGames.get(game.getName()).voteforstart.accept(player);
						return true;
					}
					else if (game.CurrentState == GameState.VOTING)
					{
						player.sendMessage(ChatColor.RED + "Vote has already started.");
						return true;
					}
					else if(game.CurrentState == GameState.WAITING || game.CurrentState == GameState.PLAYING)
					{
						player.sendMessage(ChatColor.RED + "Game has already started.");
						return true;
					}
				player.sendMessage(ChatColor.RED + "You should be in game to vote for start.");
				return true;
			}
		}

		

		//============================================SET SPAWN==================================================
		else if(cmd.getName().equalsIgnoreCase("dssetspawn") && sender instanceof Player 
				&& ((Player)sender).hasPermission("diamondshooter.create"))
		{
			if(args.length == 1 && NewGames.containsKey(args[0]))
			{
				NewGames.get(args[0]).setSpawn(((Player)sender).getLocation());
				((Player) sender).sendMessage(ChatColor.GREEN + "Spawn has been set.");
				return true;
			}
		}	

		

		//============================================SET LOBBY==================================================
		else if(cmd.getName().equalsIgnoreCase("dssetlobby") && sender instanceof Player 
				&& ((Player)sender).hasPermission("diamondshooter.create"))
		{
			if(args.length == 1 && NewGames.containsKey(args[0]))
			{
				NewGames.get(args[0]).setLobby(((Player)sender).getLocation());
				((Player) sender).sendMessage(ChatColor.GREEN + "Lobby has been set.");
				return true;
			}
		}

		

		//=========================================SET MAXSCORE==================================================
		else if(cmd.getName().equalsIgnoreCase("dssetscore") && sender instanceof Player 
				&& ((Player)sender).hasPermission("diamondshooter.create"))
		{
			if(args.length == 2 && NewGames.containsKey(args[0]) && TryParse.toInt(args[1]))
			{
				NewGames.get(args[0]).setMaxScore(Integer.parseInt(args[1]));
				((Player) sender).sendMessage(ChatColor.GREEN + "Score has been set.");
				return true;
			}
		}	

		

		//=========================================SET RADIUS====================================================
		else if(cmd.getName().equalsIgnoreCase("dssetarenaradius") && sender instanceof Player 
				&& ((Player)sender).hasPermission("diamondshooter.create"))
		{
			if(args.length == 2 && NewGames.containsKey(args[0]) && TryParse.toInt(args[1]))
			{
				NewGames.get(args[0]).setArenaRadius(Integer.parseInt(args[1]));
				((Player) sender).sendMessage(ChatColor.GREEN + "Arena's radius has been set.");
				return true;
			}
		}	

		

		//=========================================SET HIT RADIUS================================================
		else if(cmd.getName().equalsIgnoreCase("dssethitradius") && sender instanceof Player 
				&& ((Player)sender).hasPermission("diamondshooter.create"))
		{
			if(args.length == 2 && NewGames.containsKey(args[0]) && TryParse.toDouble(args[1]))
			{
				NewGames.get(args[0]).setHitRadius(Double.parseDouble(args[1]));
				((Player) sender).sendMessage(ChatColor.GREEN + "Radius of hit has been set.");
				return true;
			}
		}

		

		//=========================================SET PARTICLE==================================================
		else if(cmd.getName().equalsIgnoreCase("dssetparticle") && sender instanceof Player 
				&& ((Player)sender).hasPermission("diamondshooter.create"))
		{
			if(args.length == 2 && NewGames.containsKey(args[0]) && TryParse.toEnumParticle(args[1]))
			{
				NewGames.get(args[0]).setParticle(EnumParticle.valueOf(args[1]));
				((Player) sender).sendMessage(ChatColor.GREEN + "Particle has been set.");
				return true;
			}
		}		

		

		//=========================================SET PRIZE=====================================================
		else if(cmd.getName().equalsIgnoreCase("dssetprize") && sender instanceof Player 
				&& ((Player)sender).hasPermission("diamondshooter.create"))
		{
			if(args.length == 2 && NewGames.containsKey(args[0]))
			{
				NewGames.get(args[0]).setPrize(args[1]);
				((Player) sender).sendMessage(ChatColor.GREEN + "Prize has been set.");
				return true;
			}
		}	

		

		//=========================================SAVE GAME=====================================================
		else if(cmd.getName().equalsIgnoreCase("dssave") && sender instanceof Player 
				&& ((Player)sender).hasPermission("diamondshooter.create"))
		{
			if(args.length == 1 && NewGames.containsKey(args[0]))
			{
				if(!NewGames.get(args[0]).getSpawn().equals(null) 
						&& !NewGames.get(args[0]).getLobby().equals(null))
				{
					ConfigLoader.SaveGame(this, NewGames.get(args[0]));
					NewGames.remove(args[0]);
				}
				return true;
			}
		}
		

		
		//============================================SHOW INFO==================================================
		else if(cmd.getName().equalsIgnoreCase("dsinfo") && sender instanceof Player 
				&& ((Player)sender).hasPermission("diamondshooter.info"))
		{
			if(args.length == 1)
			{
				if(SavedGames.containsKey(args[0]))
				{
					DSGame game = SavedGames.get(args[0]);
					Player p = (Player) sender;
					p.sendMessage(ChatColor.GOLD + "Inforamation about game " + args[0] + ":");
					p.sendMessage(ChatColor.GOLD + "  Status: "+ ChatColor.GREEN +"Saved");
					p.sendMessage(ChatColor.GOLD + "  Spawn: " + ChatColor.AQUA + "(" 
							+ game.getSpawn().getBlockX() + "; " + game.getSpawn().getBlockY() + "; " 
							+ game.getSpawn().getBlockZ() + ")");
					p.sendMessage(ChatColor.GOLD + "  Spawn World: " + ChatColor.AQUA + 
							game.getSpawn().getWorld().getName());
					p.sendMessage(ChatColor.GOLD + "  Lobby: " + ChatColor.AQUA + "(" 
							+ game.getLobby().getBlockX() + "; " + game.getLobby().getBlockY() + "; " 
							+ game.getLobby().getBlockZ() + ")");
					p.sendMessage(ChatColor.GOLD + "  Lobby World: " + ChatColor.AQUA + 
							game.getLobby().getWorld().getName());
					p.sendMessage(ChatColor.GOLD + "  Scores to win: " + ChatColor.AQUA + game.getMaxScore());
					p.sendMessage(ChatColor.GOLD + "  CurrentPlayers:");
					for(Player player : game.getPlayers())
						p.sendMessage(ChatColor.AQUA + "    " + player.getDisplayName());
					return true;
				}
				else if(NewGames.containsKey(args[0]))
				{
					DSGame game = NewGames.get(args[0]);
					Player p = (Player) sender;
					p.sendMessage(ChatColor.GOLD + "Inforamation about game " + args[0] + ":");
					p.sendMessage(ChatColor.GOLD + "  Status: "+ ChatColor.RED +"New");
					if(game.getSpawn().equals(null))
						p.sendMessage(ChatColor.GOLD + "  Spawn: " + ChatColor.RED + "None.");						
					else
						p.sendMessage(ChatColor.GOLD + "  Spawn: " + ChatColor.AQUA + "(" 
							+ game.getSpawn().getBlockX() + "; " + game.getSpawn().getBlockY() + "; " 
							+ game.getSpawn().getBlockZ() + ")");
					if(game.getLobby().equals(null))
						p.sendMessage(ChatColor.GOLD + "  Lobby: " + ChatColor.RED + "None.");	
					else
						p.sendMessage(ChatColor.GOLD + "  Lobby: " + ChatColor.AQUA + "(" 
							+ game.getLobby().getBlockX() + "; " + game.getLobby().getBlockY() + "; " 
							+ game.getLobby().getBlockZ() + ")");
					p.sendMessage(ChatColor.GOLD + "  Scores to win: " + ChatColor.AQUA + game.getMaxScore());
					return true;			
				}
			}
		}	
		
		

		//============================================ACCEPT THE VOTE============================================
		else if(cmd.getName().equalsIgnoreCase("dsaccept") && sender instanceof Player 
				&& ((Player)sender).hasPermission("diamondshooter.dsaccept"))
		{
			if(args.length == 0)
			{
				Player player = (Player) sender;
				for(DSGame game : MainClass.SavedGames.values())
				{
					if(game.CurrentState == GameState.VOTING && game.getPlayers().contains(player))
						game.voteforstart.accept(player);
				}
				return true;
			}
		}	
		
		

		//============================================RELOAD=====================================================
		else if(cmd.getName().equalsIgnoreCase("dsreload") && sender instanceof Player 
				&& ((Player)sender).hasPermission("diamondshooter.dsreload"))
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
		
		DSGame.plugin = this;		
		
		getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
		getServer().getPluginManager().registerEvents(new DamagedByArrowListener(), this);
		getServer().getPluginManager().registerEvents(new ArrowHitListener(), this);
	}
	
	public void onDisable()
	{
		
	}
	
	public static boolean isInAnyGame(Player player)
	{
		for(DSGame game : SavedGames.values())
			if(!game.getPlayers().contains(player))
				return false;
		return true;
	}
	
	public static DSGame getGame(Player player)
	{
		for(DSGame game : SavedGames.values())
			if(game.getPlayers().contains(player))
				return game;
		return null;		
	}
	

}
