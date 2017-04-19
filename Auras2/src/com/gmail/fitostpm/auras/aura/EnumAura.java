package com.gmail.fitostpm.auras.aura;

import java.util.HashMap;

public enum EnumAura 
{	
	CIRCLE,
	SPIRIT;	

	private static final HashMap<EnumAura, Class<? extends Aura>> a 
		= new HashMap<EnumAura, Class<? extends Aura>>();
	
	static
	{
		a.put(CIRCLE, AuraCircle.class);
		a.put(SPIRIT, AuraSpirit.class);		
	}
	
	public Class<? extends Aura> getclass()
	{
		return a.get(this);
	}
}
