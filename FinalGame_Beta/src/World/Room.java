package World;
 import asciiPanel.AsciiPanel;
import Creature.Creature;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
public class Room {
	
	public Tile[][] roomTiles;
	
	private int roomWidth;
	public int getRoomWidth(){return this.roomWidth;}
	private int roomHeight;
	public int getRoomHeight(){return this.roomHeight;}
	public List<Creature> roomCreatures;
	
	
	public Room(int RW, int RH){
		
		
		roomWidth = RW;
		roomHeight = RH;
		roomTiles = new Tile[roomWidth][roomHeight];
		
		roomCreatures = new ArrayList<Creature>();
		
		
		
	}

	//creates walls around the outside edge of a room
	public Room createOuterWalls(){
		
		for(int x=0; x<roomWidth;x++){
			
			for(int y = 0; y<roomHeight; y++){
				
				if(x == 0|| y == 0|| y==roomHeight-1 || x == roomWidth-1){
					
					roomTiles[x][y] = Tile.WALL;
				}
				
			}}
		
		return this;
	}
	
	//fills a room with a specific tile
	public Room fillRoom(Tile t){
		
		for(int x = 0; x < roomWidth; x++){
			for(int y = 0; y < roomHeight; y++){
				
				roomTiles[x][y] = t;
				
			}
			
			
			
		}
		return this;
	}
	
	/**places a specific tile withen a room*/
	public Room placeTile(int x, int y, Tile t){
		
		if(x < roomWidth && y < roomHeight){
			
			roomTiles[x][y] = t;
		}
		
		
		return this;
	}
	
	/** Adds a creature to the room at a location */
	public void addCreature(Creature c, int x, int y){
		c.x = x;
		c.y = y;
		
		roomCreatures.add(c);
	}
}
