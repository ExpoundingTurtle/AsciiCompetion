package Creature;

import java.awt.Color;

import asciiPanel.AsciiPanel;
import World.*;
import Ai.*;
import Items.Item;
import Vision.FieldofView;

public class CreatureFactory {
	
	private World world;
	
	public CreatureFactory(World world ){
		this.world = world;
	}
	
	public Creature createPlayer(FieldofView fov){
		Creature p = new Creature("Player", world, (char)64, AsciiPanel.brightWhite, 100, 10, false);
		world.addAtEmptyLocation(p,  0);
		p.setCreatureAi(new PlayerAi(p, fov));
		world.registerCreature(p);
		p.inv.addItem(new Item(5,'S', AsciiPanel.blue, "Nice", true), 1);
		p.inv.addItem(new Item(5,'S', AsciiPanel.blue, "Woop Woop", false), 25);
		return p;
	}
	
	public Creature createFungus(int z){
		Creature p = new Creature("Fungus", world, (char)102, AsciiPanel.green, 5, 0, false);
		world.addAtEmptyLocation(p, z);
		p.setCreatureAi(new FungusAi(p,this));
		world.registerCreature(p);
		return p;
	}
	
	public Creature createPage(int z, Creature player){
		Creature p = new Creature("Page", world, (char)20, AsciiPanel.yellow, 10, 5, false);
		world.addAtEmptyLocation(p, z);
		p.setCreatureAi(new WanderAi(p,player));
		world.registerCreature(p);
		return p;
	}
	
	public Creature createLVL1ShopKeeper(int x, int y, int z){
		Creature p = new Creature("Bill The Shopkeep", world, '$', AsciiPanel.brightGreen, 10, 5, true);
		world.addAtLocation(p, x, y, z);
		p.setCreatureAi(new BlankAi(p, this));
		world.registerCreature(p);
		return p;
	}
}
