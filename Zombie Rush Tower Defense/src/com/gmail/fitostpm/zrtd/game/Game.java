package com.gmail.fitostpm.zrtd.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.fitostpm.zrtd.tasks.Vote;

import net.minecraft.server.v1_10_R1.Material;

public class Game 
{
	private String Name;
	private List<Player> Players = new ArrayList<Player>();
	private List<Location[]> Paths = new ArrayList<Location[]>();
	private List<Integer> TeamA = new ArrayList<Integer>();
	private List<Integer> TeamB = new ArrayList<Integer>();
	private GameMode Mode;
	public int CountOfPlayers;
	private int MaxPlayers = 8;
	private List<Material> DeniedBlocks = new ArrayList<Material>();
	private Vote vote;
	private GameState State;
	
	public Game()
	{
		
	}
	
	public void start()
	{
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<Player> getPlayers() {
		return Players;
	}
	public void addPlayer(Player player) {
		Players.add(player);
	}
	
	public List<Location[]> getPaths() {
		return Paths;
	}
	
	public void addPath(Location[] path){
		Paths.add(path);
	}
	public List<Integer> getTeamA() {
		return TeamA;
	}
	public List<Integer> getTeamB() {
		return TeamB;
	}
	public void setTeams(List<Integer> numbers) {
		for(int i=1; i <= CountOfPlayers; i++)
		{
			if(numbers.contains(i))
				TeamA.add(i);
			else
				TeamB.add(i);
		}
	}

	public GameMode getMode() {
		return Mode;
	}

	public void setMode(GameMode mode) {
		Mode = mode;
	}

	public List<Material> getDeniedBlocks() {
		return DeniedBlocks;
	}

	public void addDeniedBlocks(Material deniedBlock) {
		DeniedBlocks.add(deniedBlock);
	}

	public Vote getVote() {
		return vote;
	}

	public void setVote(Vote vote) {
		this.vote = vote;
	}

	public GameState getState() {
		return State;
	}

	public void setState(GameState state) {
		State = state;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getMaxPlayers() {
		return MaxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		MaxPlayers = maxPlayers;
	}
	
}
