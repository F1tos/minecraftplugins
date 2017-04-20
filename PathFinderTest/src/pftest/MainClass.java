package pftest;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

//import java.lang.reflect.Field;

import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_10_R1.EntityInsentient;
import net.minecraft.server.v1_10_R1.EntityTypes;

public class MainClass extends JavaPlugin
{
	public static Location[] Path;
	public static List<Creep> Creeps;
	public static JavaPlugin Instance;
	
	@Override
	public void onEnable()
	{
		Path = new Location[] {
								new Location(getServer().getWorld("world"), 103.5, 78, 371.5),
								new Location(getServer().getWorld("world"), 91, 78, 371.5),
								new Location(getServer().getWorld("world"), 91.5, 78, 361),
								new Location(getServer().getWorld("world"), 83, 78, 361.5)
						};
		Creeps = new ArrayList<Creep>();
		Instance = this;
		getServer().getPluginManager().registerEvents(new ClickListener(this), this);
		getServer().getPluginManager().registerEvents(new HitListener(), this);
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new PathGoalRepeater(), 0, 4);		
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	@SuppressWarnings("rawtypes")
	public static Object getPrivateField(String fieldName, Class clazz, Object object)
	{
		Field field;
        Object o = null;
        try
        {
        	field = clazz.getDeclaredField(fieldName);
    		field.setAccessible(true);
            o = field.get(object);
        }
        catch(NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch(IllegalAccessException e)
        {
            e.printStackTrace();
        }
        
        return o;
	}
	
	public void registerCreeps(String name, int id, 
			Class<? extends EntityInsentient> nmsClass, 
			Class<? extends EntityInsentient> customClass)
	{
	    try {
			Method a = EntityTypes.class.getDeclaredMethod("a", new Class<?>[]{Class.class, String.class, int.class});
			a.setAccessible(true);
			try {
				a.invoke(null, customClass, name, 1000);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    } catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
