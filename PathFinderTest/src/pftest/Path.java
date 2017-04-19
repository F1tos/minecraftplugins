package pftest;

import org.bukkit.Location;

public class Path 
{
	private Location Start;
	private Location[] PathPoint;
	private Location End;
	
	public Path(Location start, Location[] path, Location end)
	{
		Start = start;
		PathPoint = path;
		End = end;
	}
	
	public Location getStart() {
		return Start;
	}
	public void setStart(Location start) {
		Start = start;
	}
	public Location[] getPathPoint() {
		return PathPoint;
	}
	public void setPathPoint(Location[] pathPoint) {
		PathPoint = pathPoint;
	}
	public Location getEnd() {
		return End;
	}
	public void setEnd(Location end) {
		End = end;
	}
}
