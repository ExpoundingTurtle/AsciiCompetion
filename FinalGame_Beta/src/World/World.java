package World;

import java.util.ArrayList;
import java.util.List;

import Creature.Creature;
import Items.Item;
import Vision.Point;

import java.awt.Color;
import java.awt.color.*;

public class World {
	
	//the worlds name
	public String name;
	
	//the worlds tiles that make it up
	private Tile[][][] spaces;
	private Item[][][] items;
	
	//the creatures in the world
	private List<Creature> creatures;
	
	//the worlds width
	private int worldWidth;
	public int getWorldWidth(){return worldWidth;}
	
	//the worlds height
	private int worldHeight;
	public int getWorldHeight(){return worldHeight;}
	
	private int floors;
	public int getWorldFloors() { return floors; }
	
	//this is the construtor for the program
	public World(Tile[][][] t){
		this.spaces = t;
		this.worldWidth = t.length;
		this.worldHeight = t[0].length;
		this.floors = t[0][0].length;
		this.creatures = new ArrayList<Creature>();
		this.items = new Item[worldWidth][worldHeight][floors];
	}
	
	//aquires the character of a certain tile
	public char getSpaceGlyph(int x, int y, int z){
		Creature creature = creature(x, y, z);
		if (creature != null)
			return creature.symbol;
		
		if (item(x,y,z) != null)
			return item(x, y, z).glyph();
		
		return tile(x, y, z).getSymbol();
	}
	
	public Color color(int x, int y, int z){
		Creature creature = creature(x, y, z);
		if(creature != null)
			return creature.symbolColor;
		
		if (item(x,y,z) != null)
			return item(x,y,z).color();
		
		return tile(x, y, z).getSymbolColor();
	}
	
	// gets the color of a certain tile at a certain space
	public Color getSpaceSymbolColor(int x, int y, int z){
	
		
		
		
		if(x<= worldWidth && y<=worldHeight){return spaces[x][y][z].getSymbolColor();}
		else{return null;}
	
		
	}
	
	public Color getSpacesBackgroundColor(int x, int y, int z){
		if(x<= worldWidth && y<=worldHeight){return spaces[x][y][z].getBackgroundColor();}
		else{return null;}
		
		
	}
	
	//Returnes the tile at a certain space
	public Tile getSpaceTile(int x, int y, int z){
		if(x<= worldWidth && y<=worldHeight){return spaces[x][y][z];}
		else{return null;}
		
	}
	
	public Item item(int x, int y, int z){
		return items[x][y][z];
	}
	
	public void addAtEmptyLocation(Creature creature, int z) {
		int x;
		int y;
		
		do {
			x = (int)(Math.random() * worldWidth);
			y = (int)(Math.random() * worldHeight);
		} 
		while (!tile(x,y,z).getWalkable() || creature(x,y,z) != null || tile(x, y, z).isStairs());
		
		creature.x = x;
		creature.y = y;
		creature.z = z;
		creatures.add(creature);
	}	
	
	public void addAtEmptyLocation(Item item, int depth) {
		int x;
		int y;
		
		do {
			x = (int)(Math.random() * worldWidth);
			y = (int)(Math.random() * worldHeight);
		}
		while (!tile(x,y,depth).getWalkable() || item(x,y,depth) != null || tile(x,y,depth).isStairs());
		
		items[x][y][depth] = item;
	}
	
	public void addAtLocation(Creature creature, int x, int y, int z){
		creature.x = x;
		creature.y = y;
		creature.z = z;
		creatures.add(creature);
	}
	
	public Tile tile(int x, int y, int z){
		if (x < 0 || x >= worldWidth || y < 0 || y >= worldHeight || z < 0 || z >= floors)
			return Tile.VOID;
		else
			return spaces[x][y][z];
	}
	
	public Creature creature(int x, int y, int z){
		for (Creature c : creatures){
			if (c.x == x && c.y == y && c.z == z)
				return c;
		}
		return null;
	}
	
	//finds a random location that can be walked on
	public int[] findEmptyLocation(int z){
		int[] space = new int[2];
		int x = 0;
		int y  = 0;
		
		do{
			x = (int)(Math.random() * worldWidth);
			y =(int)(Math.random() * worldHeight);
			
		}while(!spaces[x][y][z].getWalkable());
		
		space[0] = x;
		space[1] = y;
		
		return space;
	
		
	}
	
	//adds a creature to the world
	public void registerCreature(Creature c){
		
		creatures.add(c);
		
	}
	
	//gets the creature list for the world
	public List<Creature> getCreatureList(){
		
		return creatures;
	}
	
	//the world update funtion updating the world and all of the creatures
	public void update(){
		
		List<Creature> toUpdate = new ArrayList<Creature>(creatures);
		
		for(Creature creature: toUpdate){
			
			creature.update();
		
		}
		
		
	}
	
	//returns the creature at a certain space
	public Creature creatureAtSpace(int x, int y){
		
		List<Creature> listCopy = new ArrayList<Creature>(creatures);
		
		for(Creature c: listCopy){
			
			if(c.x == x && c.y == y){
				
				return c;
			}
			
		}
		
		return null;
	}
	
	//Removes a creature from the world
	public void remove (Creature other){
		
		creatures.remove(other);

	}
	
	public void remove(Item item) {
		for (int x =0; x < worldWidth; x++){
			for (int y = 0; y < worldHeight; y++){
				for (int z = 0; z < floors; z++){
					if (items[x][y][z] == item) {
						items[x][y][z] = null;
						return;
					}
				}
			}
		}
	}
	
	public void remove(int x, int y, int z) {
		items[x][y][z] = null;
	}

	public boolean addAtEmptySpace(Item item, int x, int y, int z){
		if (item == null)
			return true;
		
		List<Point> points = new ArrayList<Point>();
		List<Point> checked = new ArrayList<Point>();
		
		points.add(new Point(x, y, z));
		
		while (!points.isEmpty()){
			Point p = points.remove(0);
			checked.add(p);
			
			if (!tile(p.x, p.y, p.z).isGround())
				continue;
			
			if (items[p.x][p.y][p.z] == null){
				items[p.x][p.y][p.z] = item;
				Creature c = this.creature(p.z,  p.y,  p.z);
				if (c != null){}
					//c.notify("A%s lands between your feet.", c.nameOf(item));
					//System.out.println("butts");
				return true;
			} else {
				List<Point> neighbors = p.neighbors8();
				neighbors.removeAll(checked);points.addAll(neighbors);
			}
		}
		return false;
	}
}
