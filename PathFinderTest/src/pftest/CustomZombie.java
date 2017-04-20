package pftest;

import org.bukkit.Location;

import net.minecraft.server.v1_10_R1.EntityZombie;
import net.minecraft.server.v1_10_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_10_R1.World;

public class CustomZombie extends EntityZombie 
{
	private Location[] Path;

	public CustomZombie(World world) {
		super(world); 
		this.goalSelector = new PathfinderGoalSelector((world != null) && (world.methodProfiler != null) ? world.methodProfiler : null);
	    this.targetSelector = new PathfinderGoalSelector((world != null) && (world.methodProfiler != null) ? world.methodProfiler : null);
	    setSize(0.6F, 1.95F);
	}

	public Location[] getPath() {
		return Path;
	}

	public void setPath(Location[] path) {
		Path = path;
	}
}
