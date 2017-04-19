package pftest;

import net.minecraft.server.v1_10_R1.EntitySnowball;
import net.minecraft.server.v1_10_R1.EnumParticle;
import net.minecraft.server.v1_10_R1.World;

public class ExtendedProjectile extends EntitySnowball
{
	private EffectReapeter effect;
		
	public ExtendedProjectile(World world, EnumParticle effect)
	{
		super(world);
	}
}
