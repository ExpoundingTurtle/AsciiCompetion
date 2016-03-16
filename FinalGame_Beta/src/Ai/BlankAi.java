package Ai;

import Creature.*;

public class BlankAi extends CreatureAi{
	private CreatureFactory factory;
	
	public BlankAi (Creature creature, CreatureFactory factory) {
		super(creature);
		this.factory = factory;
	}
	
	@Override
	public void onInteract(){
		System.out.println("You Interact with the shopkeeper");
	}

}
