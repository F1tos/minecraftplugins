package com.gmail.fitostpm.spellbook.spells;

import java.util.HashMap;

public enum EnumSpell 
{
	FIRE_RESISTANCE(1, "fireres", "Fire Resistance", new FireResistantSpell()),
	HASTE(2, "haste", "Haste", new HasteSpell()),
	HARM(3, "harm", "Harm", new InstantHarmSpell()),
	HEAL(4, "heal", "Heal", new InstantHealSpell()),
	INVISIBILITY(5, "invis", "Invisibility", new InvisibilitySpell()),
	JUMP_BOOST(6, "jump", "Jump Boost", new JumpBoostSpell()),
	LIGHTNING_STRIKE(7, "lightning", "Lightning Strike", new LightningStrikeSpell()),
	POWER(8, "power", "Power", new PowerSpell()),
	REGENERATION(9, "regen", "Regeneration", new RegenerationSpell()),
	FIREBALL(10, "fireball", "Fireball", new FireballSpell()),
	FIREWORK(11, "firework", "Magic Firework", new MagicFireworkSpell());

	private final int Id;
	private final String TagName;
	private final String SpellName;
	private final Spell Spell;
	
	private static final HashMap<Integer, EnumSpell> a = new HashMap<Integer, EnumSpell>();
	private static final HashMap<String, EnumSpell> b = new HashMap<String, EnumSpell>();
	private static final HashMap<String, EnumSpell> c = new HashMap<String, EnumSpell>();
	
	static 
	{
		for(EnumSpell spell : values())
		{
			a.put(spell.Id, spell);
			b.put(spell.TagName, spell);
			c.put(spell.SpellName, spell);
		}
	}

	private EnumSpell(int id, String tagName, String spellName, Spell spell)
	{
		Id = id;
		TagName = tagName;
		SpellName = spellName;
		Spell = spell;
	}
	
	public static EnumSpell getById(int id)
	{
		if(a.containsKey(id))
			return a.get(id);
		return null;
	}
	
	public static EnumSpell getByTag(String tag)
	{
		if(b.containsKey(tag))
			return b.get(tag);
		return null;
	}
	
	public static EnumSpell getByName(String name)
	{
		if(c.containsKey(name))
			return c.get(name);
		return null;
	}
	
	public Spell getSpell() {
		return Spell;
	}
	
	public String getTag() {
		return TagName;
	}
	
	public String getName() {
		return SpellName;
	}
}
