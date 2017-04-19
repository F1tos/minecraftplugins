package com.gmail.fitostpm.diamondshooter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import com.gmail.fitostpm.diamondshooter.tasks.PlayThread;
import com.gmail.fitostpm.diamondshooter.tasks.VoteForStart;
import com.gmail.fitostpm.diamondshooter.tasks.VoteThread;
import com.gmail.fitostpm.diamondshooter.tasks.WaitThread;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class DSGame 
{
	public static JavaPlugin plugin;
	
	private String Name;
	private Location Spawn = null;
	private Location Lobby = null;
	private List<Player> Players = new LinkedList<Player>();
	private HashMap<Player,Location> Positions = new HashMap<Player,Location>();
	private HashMap<Player,Integer> Score = new HashMap<Player,Integer>();
	private HashMap<Player,HashMap<Integer,ItemStack>> Invs = new HashMap<Player,HashMap<Integer,ItemStack>>();
	private int MaxScore = 10;
	private int ArenaRadius = 6;
	private double HitRadius = 0.7;
	private String Prize = "give %winner% minecraft:diamond 10";
	private EnumParticle Particle = EnumParticle.FLAME;
	private int SecondsToNextRound = 0;
	private Item CurrentTarget;
	public GameState CurrentState = GameState.NOTSTARTED;
	public Runnable CurrentTask;
	public VoteForStart voteforstart;
	
	public DSGame()	{ }
	
	public DSGame(String name)
	{
		Name = name;
	}
	
	public boolean addPlayer(Player player)
	{
		if(!Players.contains(player))
		{
			Players.add(player);
			Score.put(player, 0);
			return true;
		}
		else
			return false;
	}
	
	public boolean removePlayer(Player player)
	{
		if(Players.contains(player))
		{
			Players.remove(player);
			Score.remove(player);
			return true;
		}
		else
			return false;
	}
	
	public int getScoreForPlayer(Player player)
	{
		if(Players.contains(player))
			return Score.get(player);
		else
			return -1;
	}
	
	public boolean setScoreForPlayer(Player player, int score)
	{
		if(Players.contains(player))
		{
			Score.replace(player, score);
			return true;
		}
		else
			return false;
	}
	
	public void startNewRound()
	{
		CurrentState = GameState.PLAYING;
		CurrentTask = new Thread(new PlayThread(Name));
		CurrentTask.start();
		
		for(Player player : Players)
			player.sendMessage(ChatColor.GOLD + "The round has started...");	
		Spawn.getWorld().playSound(Spawn, Sound.CLICK, 1, 1);
		CurrentTarget = Spawn.getWorld().dropItem(Spawn, new ItemStack(Material.DIAMOND, 1));
		CurrentTarget.setPickupDelay(32767);
		Random r = new Random();
		double x = r.nextDouble()/5-0.1;
		double y = r.nextDouble()/2+1;
		double z = r.nextDouble()/5-0.1;
		CurrentTarget.setVelocity(new Vector(x,y,z));
	}
	
	public void startGame()
	{
		Bukkit.getLogger().info("startGame() executed"); 
		CurrentState = GameState.WAITING;
		Random r = new Random();
		SecondsToNextRound = r.nextInt(6) + 3;
		try {
			CurrentTask.join();
		} catch (InterruptedException e) { }
		CurrentTask = new Thread(new WaitThread(Name));
		CurrentTask.start();
		
		for(int i=0; i<Players.size(); i++)
		{
			Location loc = new Location(Spawn.getWorld(), 
					Spawn.getX() + ArenaRadius * Math.sin(Math.toRadians(i*(360/Players.size()))),
					Spawn.getY(), 
					Spawn.getZ() + ArenaRadius * Math.cos(Math.toRadians(i*(360/Players.size()))));
			loc.setYaw((float) Math.toRadians(i*(360/Players.size() + 90)));
			Positions.put(Players.get(i), loc);
			Players.get(i).teleport(loc);
		}
		ItemStack bow = new ItemStack(Material.BOW, 1);
		bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		bow.addEnchantment(Enchantment.DURABILITY, 1);
		for(Player player : Players)
		{
			player.sendMessage(ChatColor.GOLD + "The game has started!!!");
			player.getInventory().clear();
			player.getInventory().addItem(bow);
			player.getInventory().addItem(new ItemStack(Material.ARROW, 1));
		}
	}
	
	public int tick()
	{
		SecondsToNextRound--;
		return SecondsToNextRound;
	}
	
	public void checkWinner()
	{
		Bukkit.getLogger().info("checkWinner() executed"); 
		List<Entity> list = CurrentTarget.getNearbyEntities(HitRadius, HitRadius, HitRadius);
		if(list.size() > 0)
		{
			for(Entity e : list)
			{
				if(e instanceof Arrow && ((Arrow)e).getShooter() instanceof Player 
						&& getPlayers().contains(((Arrow)e).getShooter()))
				{
					Player winner = (Player)((Arrow)e).getShooter();
					setScoreForPlayer(winner, getScoreForPlayer(winner) + 1);
					getCurrentTarget().getWorld().playSound(
							getCurrentTarget().getLocation(), Sound.GLASS, 5, 5);
					removeTarget();
					if(getScoreForPlayer(winner) == getMaxScore())
					{
						for(Player player : getPlayers())
						{
							player.sendMessage(ChatColor.AQUA + "The winner is " + player.getDisplayName() 
								+ "!!! Congratulations!!!");
							player.teleport(getLobby());
							player.getInventory().clear();
						}
						givePrize(winner.getDisplayName());
						clearPlayerList();
						CurrentState = GameState.NOTSTARTED;
						CurrentTask = null;
					}
					else
					{
						Random r = new Random();
						setSecondsToNextRound(r.nextInt(6) + 3);
						for(Player player : getPlayers())
						{
							player.sendMessage("The winner of this round is " + ChatColor.BLUE 
									+ winner.getDisplayName() + ChatColor.RESET + "! He has " 
									+ getScoreForPlayer(winner) + " points!");
							player.sendMessage("Next round will start in 3 to 8 seconds");
						}
						
						CurrentState = GameState.WAITING;
						CurrentTask = new Thread(new WaitThread(Name));
						CurrentTask.start();
					}
					return;
				}
			}			
		}
	}
	
	public void removeTarget()
	{
		CurrentTarget.remove();
		CurrentTarget = null;
	}
	
	public Location getPlayersPosition(Player player)
	{
		if(Positions.containsKey(player))
			return Positions.get(player);
		else
			return null;
	}
	
	public void clearPlayerList()
	{
		Players = new LinkedList<Player>();
	}
	
	public void restoreInventories()
	{
		for(Player player : Players)
		{
			player.getInventory().clear();
			for(int i : Invs.get(player).keySet())
				player.getInventory().setItem(i, Invs.get(player).get(i));
		}
	}	
	
	public void saveInventory(Player player)
	{
		HashMap<Integer,ItemStack> inv = new HashMap<Integer,ItemStack>();
		for(int i = 0; i < player.getInventory().getSize(); i++)
			if(!player.getInventory().getItem(i).getType().equals(Material.AIR))
				inv.put(i, player.getInventory().getItem(i));
		Invs.put(player, inv);
	}
	
	public void givePrize(String playername)
	{
		String command = Prize.substring(0, Prize.indexOf("%winner%")) + playername 
				+ Prize.substring(Prize.indexOf("%winner%")+8);
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Spawn point: " + Spawn.getBlockX() + ", " + Spawn.getBlockY() + ", " + Spawn.getBlockZ());
		sb.append("Lobby: " + Lobby.getBlockX() + ", " + Lobby.getBlockY() + ", " + Lobby.getBlockZ());
		for(Player player : Players)
			sb.append(player.getDisplayName());
		return sb.toString();		
	}
	
	public void createVote()
	{
		voteforstart = new VoteForStart(Name);
		CurrentTask = new VoteThread(Name);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof DSGame && Name.equals(((DSGame)obj).Name);		
	}
	
	
	
	

	public Location getSpawn() {
		return Spawn;
	}

	public void setSpawn(Location spawn) {
		Spawn = spawn;
	}

	public List<Player> getPlayers() {
		return Players;
	}

	public void setPlayers(List<Player> players) {
		Players = players;
	}

	public HashMap<Player,Integer> getScore() {
		return Score;
	}

	public void setScore(HashMap<Player,Integer> score) {
		Score = score;
	}

	public int getMaxScore() {
		return MaxScore;
	}

	public void setMaxScore(int maxScore) {
		MaxScore = maxScore;
	}

	public Item getCurrentTarget() {
		return CurrentTarget;
	}

	public void setCurrentTarget(Item currentTarget) {
		CurrentTarget = currentTarget;
	}
	public int getSecondsToNextRound() {
		return SecondsToNextRound;
	}

	public void setSecondsToNextRound(int secondsToNextRound) {
		SecondsToNextRound = secondsToNextRound;
	}
	
	public int getArenaRadius() {
		return ArenaRadius;
	}

	public void setArenaRadius(int arenaradius) {
		ArenaRadius = arenaradius;
	}

	public Location getLobby() {
		return Lobby;
	}

	public void setLobby(Location lobby) {
		Lobby = lobby;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPrize() {
		return Prize;
	}

	public void setPrize(String prize) {
		Prize = prize;
	}

	public EnumParticle getParticle() {
		return Particle;
	}

	public void setParticle(EnumParticle particle) {
		Particle = particle;
	}

	public double getHitRadius() {
		return HitRadius;
	}

	public void setHitRadius(double hitRadius) {
		HitRadius = hitRadius;
	}
}
