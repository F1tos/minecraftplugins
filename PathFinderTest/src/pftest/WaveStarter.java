package pftest;



import org.bukkit.Bukkit;
import org.bukkit.Location;

public class WaveStarter implements Runnable 
{
	private int Total;
	private int Current = 0;
	private Location[] Path;
	private String CreepType;
	private int ItsId;
	
	public WaveStarter(String creepType, Location[] path, int total)
	{
		CreepType = creepType;
		Path = path;
		Total = total;
	}
	
	public void setId(int id){
		ItsId = id;
	}
	

	@Override
	public void run() 
	{
		if(Current < Total)
		{
			if(CreepType.equals("Zombie"))
			{
				ZombieCreep c = new ZombieCreep(Path);
				MainClass.Creeps.add(c);
				ClickListener.c = c;
			}
			Current++;
		}
		else
			Bukkit.getServer().getScheduler().cancelTask(ItsId);
	}

}
