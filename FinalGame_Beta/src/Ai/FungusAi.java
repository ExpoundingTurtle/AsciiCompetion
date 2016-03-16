package Ai;

import Creature.*;

public class FungusAi extends CreatureAi{
	
	private CreatureFactory factory;
	private int children;
	
	public FungusAi(Creature creature, CreatureFactory factory) {
		super(creature);
		this.factory = factory;
	}
	
	public void onUpdate(){
		if(children < 5 && Math.random() < .02){spread();}
	}
	
	private void spread(){
		
		int x = creature.x + (int)(Math.random() * 11) - 5;
		int y = creature.y + (int)(Math.random() * 11) - 5;
		int z = creature.z + (int)(Math.random() * 11) - 5;
	
		if(!world.tile(x, y, z).isGround()){
		
			return;
		}
		
		Creature child = factory.createFungus(z);
		children++;
		
		
	}

}
