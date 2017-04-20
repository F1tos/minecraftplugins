package com.gmail.fitostpm.zrtd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.fitostpm.zrtd.creep.Creep;
import com.gmail.fitostpm.zrtd.game.Game;
import com.gmail.fitostpm.zrtd.game.GameMode;
import com.gmail.fitostpm.zrtd.game.GameState;
import com.gmail.fitostpm.zrtd.missile.Missile;
import com.gmail.fitostpm.zrtd.tasks.VoteForStart;

import net.md_5.bungee.api.ChatColor;

public class MainClass extends JavaPlugin
{	
	public static JavaPlugin Instance;
	public static HashMap<Entity, Creep> ExistingCreeps;
	public static List<Missile> LaunchedMissiles;
	public static HashMap<String, Game> Games;
	public static ChatColor[] PlayerColors;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		//======================================================================================
		//===================================JOINING THE GAME===================================
		//======================================================================================
		if(cmd.getName().equalsIgnoreCase("zrtdjoin") && sender instanceof Player && 
				((Player)sender).hasPermission("zombierushtowerdefence.join"))
		{
			if(args.length == 1)
			{
				if(Games.containsKey(args[0]))
				{
					Player player = (Player) sender;
					Game game = getGame(player);
					if(game == null)
					{
						Game gameToJoin = Games.get(args[0]);
						switch(gameToJoin.getState())
						{
						case NOTSTARTED:
							if(gameToJoin.CountOfPlayers == 8)
								player.sendMessage(ChatColor.RED + "Sorry, but the game " + args[0] + " is full.");
							else
							{
								gameToJoin.addPlayer(player);
								player.sendMessage(ChatColor.GREEN + "You have successfully joined the game " 
										+ args[0]);
							}			
							break;
						case PLAYING:
							player.sendMessage(ChatColor.RED + "Sorry, but the game " + args[0] + " is already "
									+ "playing.");
							break;
						case VOTING:
							player.sendMessage(ChatColor.RED + "Sorry, but the game " + args[0] + " is currently "
									+ "voting. Try again in a few seconds.");
							break;
						default:
							break;
								
						}
					}
					else
						player.sendMessage(ChatColor.RED + "You are already in one of the games. Please leave it "
								+ "to join another.");
				}
				else
					((Player)sender).sendMessage(ChatColor.RED + "There is no such game " + args[0]);
				return true;
			}
		}
		
		//======================================================================================
		//===============================START VOTING FOR START=================================
		//======================================================================================

		if(cmd.getName().equalsIgnoreCase("zrtdvotestart") && sender instanceof Player && 
				((Player)sender).hasPermission("zombierushtowerdefence.votestart"))
		{
			if(args.length == 0)
			{
				Player player = (Player) sender;
				Game gameToStart = getGame(player);
				if(gameToStart == null)
					player.sendMessage(ChatColor.RED + "You should join a game to vote for start.");
				else
				{
					switch(gameToStart.getState())
					{
					case PLAYING:
						player.sendMessage(ChatColor.RED + "The game is already started.");
						break;
					case VOTING:
						player.sendMessage(ChatColor.RED + "Players is currently voting.");
						break;
					case NOTSTARTED:
						StringBuilder sb = new StringBuilder();
						if(gameToStart.getMode().equals(GameMode.TEAMS))										
						{
							for(int i : gameToStart.getTeamA())
								sb.append(PlayerColors[gameToStart.getPlayers().indexOf(player)] +
										gameToStart.getPlayers().get(i).getName() + " ");
						}		
						
						for(Player p : gameToStart.getPlayers())
						{
							if(gameToStart.getMode().equals(GameMode.SOLO))
								gameToStart.start();
							else									
								if(p.equals(player))
									p.sendMessage(ChatColor.GREEN + "Vote for start has been started!");
								else
								{
									p.sendMessage(ChatColor.BLUE + "Player " 
											+ PlayerColors[gameToStart.getPlayers().indexOf(player)] + player.getName() 
											+ " suggest to start the game. Type " + ChatColor.GREEN + "/zrtdagree "
											+ ChatColor.BLUE + "to accept starting the game or type " + ChatColor.RED
											+ "/zrtddisagree " + ChatColor.BLUE + "to decline.");
									p.sendMessage(ChatColor.BLUE + "GameMode: " + ChatColor.GOLD 
											+ gameToStart.getMode().toString());
									if(gameToStart.getMode().equals(GameMode.TEAMS))										
									{
										p.sendMessage(ChatColor.BLUE + "Teams:");
										p.sendMessage(ChatColor.BLUE + "  TeamA: " + sb.toString());
									}		
								}
						}
						gameToStart.setState(GameState.VOTING);
						gameToStart.setVote(new VoteForStart(gameToStart, player)); 
						int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, gameToStart.getVote(), 0, 20);
						gameToStart.getVote().setID(id);
						gameToStart.getVote().agree(player);
						break;
					default:
						break;					
					}
				}
				return true;
			}			
		}
		
		
		return false;
	}
	
	public void onEnable()
	{
		Instance = this;
		ExistingCreeps = new HashMap<Entity, Creep>();
		LaunchedMissiles = new ArrayList<Missile>();
		
		PlayerColors = new ChatColor[8];
		PlayerColors[0] = ChatColor.AQUA;
		PlayerColors[1] = ChatColor.DARK_AQUA;
		PlayerColors[2] = ChatColor.DARK_GRAY;
		PlayerColors[3] = ChatColor.DARK_PURPLE;
		PlayerColors[4] = ChatColor.GOLD;
		PlayerColors[5] = ChatColor.GRAY;
		PlayerColors[6] = ChatColor.LIGHT_PURPLE;
		PlayerColors[7] = ChatColor.YELLOW;
		World world = Bukkit.getWorld("world");
		Game testGame = new Game();
		testGame.addPath(new Location[] {
				new Location(world, 81.5, 81, 313.5),
				new Location(world, 70, 81, 313.5),
				new Location(world, 70.5, 81, 304),
				new Location(world, 61, 81, 304.5),
				new Location(world, 61.5, 81, 323),
				new Location(world, 68, 81, 322.5),
				new Location(world, 67.5, 81, 329),
				new Location(world, 55, 81, 328.5),
				new Location(world, 55.5, 81, 316),
				new Location(world, 43, 81, 316.5)
		});
		Games.put("TestGame", new Game());
	}
	
	public void onDisable() { }
	
	public Game getGame(Player player)
	{
		for(Game g : Games.values())
		{
			if(g.getPlayers().contains(player))
				return Games.get(g.getName());
		}
		return null;
	}
}
