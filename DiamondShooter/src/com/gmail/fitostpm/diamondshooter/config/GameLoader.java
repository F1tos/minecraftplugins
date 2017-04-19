package com.gmail.fitostpm.diamondshooter.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.fitostpm.diamondshooter.DSGame;
import com.gmail.fitostpm.diamondshooter.MainClass;
import com.gmail.fitostpm.diamondshooter.util.TryParse;

import net.minecraft.server.v1_8_R3.EnumParticle;

public class GameLoader
{
	public static void GetConfig(JavaPlugin plugin) throws IOException
	{
		File dir = new File(plugin.getDataFolder(), "Games");
		dir.mkdirs();
		for(File f : dir.listFiles())
		{
			String name = f.getName().substring(0, f.getName().indexOf(".yml"));
			BufferedReader br = new BufferedReader(new FileReader(f));
			List<String> lines = new ArrayList<String>();
			String line;
			while((line = br.readLine()) != null)
				lines.add(line);
			br.close();
			DSGame game = CreateGame(plugin, name, lines);
			MainClass.SavedGames.put(name,game);
		}
	}
	
	public static DSGame CreateGame(JavaPlugin plugin, String name, List<String> lines)
	{
		DSGame game = new DSGame(name);
		int sx = 0, sy = 0, sz = 0, lx = 0, ly = 0, lz = 0, score = 10, ar = 6; 
		String prize = "";
		double hr = 0.7;
		World spawnworld = null;
		World lobbyworld = null;
		EnumParticle particle = EnumParticle.FLAME;
		for(String s : lines)
		{
			if(s.indexOf("spawnx:") != -1 && TryParse.toInt(s.substring(s.indexOf(": ")+2)))
				sx = Integer.parseInt(s.substring(s.indexOf(": ")+2));
			else if (s.indexOf("spawny:") != -1 && TryParse.toInt(s.substring(s.indexOf(": ")+2)))
				sy = Integer.parseInt(s.substring(s.indexOf(": ")+2));
			else if (s.indexOf("spawnz:") != -1 && TryParse.toInt(s.substring(s.indexOf(": ")+2)))
				sz = Integer.parseInt(s.substring(s.indexOf(": ")+2));
			else if (s.indexOf("lobbyx:") != -1 && TryParse.toInt(s.substring(s.indexOf(": ")+2)))
				lx = Integer.parseInt(s.substring(s.indexOf(": ")+2));
			else if (s.indexOf("lobbyy:") != -1 && TryParse.toInt(s.substring(s.indexOf(": ")+2)))
				ly = Integer.parseInt(s.substring(s.indexOf(": ")+2));
			else if (s.indexOf("lobbyz:") != -1 && TryParse.toInt(s.substring(s.indexOf(": ")+2)))
				lz = Integer.parseInt(s.substring(s.indexOf(": ")+2));
			else if (s.indexOf("score:") != -1 && TryParse.toInt(s.substring(s.indexOf(": ")+2)))
				score = Integer.parseInt(s.substring(s.indexOf(": ")+2));
			else if (s.indexOf("arenaradius:") != -1 && TryParse.toInt(s.substring(s.indexOf(": ")+2)))
				ar = Integer.parseInt(s.substring(s.indexOf(": ")+2));
			else if (s.indexOf("hitradius:") != -1 && TryParse.toDouble(s.substring(s.indexOf(": ")+2)))
				hr = Double.parseDouble(s.substring(s.indexOf(": ")+2));
			else if (s.indexOf("particle:") != -1 
					&& TryParse.toEnumParticle(s.substring(s.indexOf('\'')+1,s.lastIndexOf('\''))))
				particle = EnumParticle.valueOf(s.substring(s.indexOf('\'')+1,s.lastIndexOf('\'')));
			else if (s.indexOf("prize:") != -1)
				prize = s.substring(s.indexOf('\'')+1,s.lastIndexOf('\''));
			else if (s.indexOf("spawnworldname:") != -1 
					&& TryParse.toWorld(s.substring(s.indexOf('\'')+1,s.lastIndexOf('\''))))
				spawnworld = plugin.getServer().getWorld(s.substring(s.indexOf('\'')+1,s.lastIndexOf('\'')));
			else if (s.indexOf("lobbyworldname:") != -1 
					&& TryParse.toWorld(s.substring(s.indexOf('\'')+1,s.lastIndexOf('\''))))
				lobbyworld = plugin.getServer().getWorld(s.substring(s.indexOf('\'')+1,s.lastIndexOf('\'')));
			
		}
		game.setSpawn(new Location(spawnworld, sx, sy, sz));
		game.setLobby(new Location(lobbyworld, lx, ly, lz));
		game.setMaxScore(score);
		game.setArenaRadius(ar);
		game.setHitRadius(hr);
		game.setParticle(particle);
		game.setPrize(prize);
		return game;
	}
	
	public static void SaveGame(JavaPlugin plugin, DSGame game) throws IOException
	{
		File dir = new File(plugin.getDataFolder(), "Games");
		dir.mkdirs();
		File file = new File(dir, game.getName() + ".yml");
		file.createNewFile();
		PrintWriter pw = new PrintWriter(new FileOutputStream(file));
		pw.println("spawnworldname: '" + game.getSpawn().getWorld().getName() + "'" );
		pw.println("spawnx: " + game.getSpawn().getBlockX());
		pw.println("spawny: " + game.getSpawn().getBlockY());
		pw.println("spawnz: " + game.getSpawn().getBlockZ());
		pw.println("lobbyworldname: '" + game.getSpawn().getWorld().getName() + "'" );
		pw.println("lobbyx: " + game.getLobby().getBlockX());
		pw.println("lobbyy: " + game.getLobby().getBlockY());
		pw.println("lobbyz: " + game.getLobby().getBlockZ());
		pw.println("score: " + game.getMaxScore());
		pw.println("arenaradius: " + game.getArenaRadius());
		pw.println("hitradius: " + game.getHitRadius());
		pw.println("particle: '" + game.getParticle().toString() + "'");
		pw.println("prize: '" + game.getPrize() + "'");
		pw.close();		
	}

}
