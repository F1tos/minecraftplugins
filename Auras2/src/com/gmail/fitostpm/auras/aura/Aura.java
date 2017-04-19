package com.gmail.fitostpm.auras.aura;

import org.bukkit.Location;

import net.minecraft.server.v1_8_R3.EnumParticle;

public abstract class Aura 
{
	protected EnumParticle Particle;
	protected float Red;
	protected float Green;
	protected float Blue;
	protected boolean IsColored;
	protected int TaskId;
	
	public abstract void show(Location location);
	public abstract String toString();
	
	public EnumParticle getParticle(){
		return Particle;
	}
	
	public void setParticle(EnumParticle particle){
		Particle = particle;
	}
	
	public float getRed(){
		return Red;
	}
	
	public void setRed(float red){
		Red = red;
	}
	
	public float getGreen(){
		return Green;
	}
	
	public void setGreen(float green){
		Green = green;
	}
	
	public float getBlue(){
		return Blue;
	}
	
	public void setBlue(float blue){
		Blue = blue;
	}
	
	public int getTaskId(){
		return TaskId;
	}
	
	public void setTaskId(int id){
		TaskId = id;
	}
	
	public boolean isColored(){
		return IsColored;
	}	
	
	public void setColored(boolean colored){
		IsColored = colored;		
	}
}
