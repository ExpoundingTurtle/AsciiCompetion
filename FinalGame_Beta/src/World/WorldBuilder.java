package World;

import World.Tile;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import Creature.Creature;
public class WorldBuilder {
	private int height;
	private int width;
	private int numberOfFloors;
	private RoomRepository roomRep;

	private Tile[][][] tiles;
	private List<Creature> creatures;
	
	public WorldBuilder(int w, int h, int z){
		
		this.width = w;
		this.height = h;
		this.numberOfFloors = z;
		this.tiles = new Tile[w][h][z];
		this.roomRep = new RoomRepository();
		this.creatures = new ArrayList<Creature>();
		}
	/*
	public WorldBuilder randomizeTile(){
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				if(Math.random() < 0.5){
	     tiles[x][y] = Tile.FLOOR;
				}else{tiles[x][y] = Tile.WALL;}
				
			}}
		return this;
		}
		*/
	//constructs and outputs the final world
	public World buildWorld(){return new World(tiles);}
	
	/*public WorldBuilder makeBaseRoom(){
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				for(int z = 0;  )
			if(x == 0 ||y==0 || x == width-1 || y == height-1){tiles[x][y] = Tile.WALL;
			
			}else{tiles[x][y] = Tile.FLOOR;}
				
			}}
		
		return this;
	}*/
	/**adds trees at random places */
	/*
	public WorldBuilder addTrees(float chance){
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
			
				if(Math.random()<= chance && tiles[x][y]!= Tile.WALL){
					tiles[x][y] = Tile.TREE;
				}
			}}
		
		
		return this;
	}
	*/
	
	/** Constructs an entire world from one room */
	/*
	public WorldBuilder MakeFloorFromRoom(Room room, int z){
		
		tiles = room.roomTiles;
		
		return this;
	}
	*/
	/**Adds a room to the world at a point */
	public WorldBuilder AddRoomToWorld(Room room, int x, int y, int z){
		if(x + room.getRoomWidth() < width && y +room.getRoomHeight()<height && z<numberOfFloors && x>0 && y>0){
		for(int ix = 0; ix<room.getRoomWidth(); ix++){
			for(int iy = 0; iy<room.getRoomHeight(); iy++){
				
				tiles[ix +x][iy+y][z] = room.roomTiles[ix][iy];
				
			}
			
		}
		}
		return this;
		
	}
	/**Fills the world with a specific tile */
	public WorldBuilder fillWorldWithTile(Tile t){
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < width; y++){
				for(int z = 0; z<numberOfFloors; z++){
				
				tiles[x][y][z] = t;
				}	
			}
		}
		
		
		return this;}
	
	//----------------This ungodly function generates a random world of random rooms, good luck------------------------
	public WorldBuilder RoomBasedRandom(int numberOfRooms){
		//used to store which spaces cant be written over
		 boolean[][][] notOpen = new boolean[width][height][numberOfFloors];
		 this.fillWorldWithTile(Tile.VOID);
		 for(int x = 0; x<width; x++){
			for(int y = 0; y<height; y++){
				for(int z = 0; z<numberOfFloors; z++)
				notOpen[x][y][z] = false;
				
			}
			 
		 }
		 
		 
		//variable that will be used to store points
		int randomX = 0;
		//var that will be used to store points
		int randomY = 0;
		//count of number of times this funtion has iterated
		int count = 0;
		
		//short of "continue" used to determine whether or not the do While  loop continues
		boolean con = true;
		
		//room to add
		Room room;
		
		
		for(int wz = 0; wz<numberOfFloors; wz++){
		
			
		//creates the core of the world
		this.AddRoomToWorld(roomRep.Core, (width/2)-roomRep.Core.getRoomWidth()/2, (height/2) - roomRep.Core.getRoomHeight()/2, wz);
		for(int x = (width/2)-roomRep.Core.getRoomWidth()/2; x<(width/2-1)+roomRep.Core.getRoomWidth()/2; x++){
			for(int y = (height/2)- roomRep.Core.getRoomHeight()/2; y < (height/2-1)+ roomRep.Core.getRoomHeight()/2; y++){
				if(tiles[x][y][wz].getIsWall() == false){
				notOpen[x][y][wz] = true;
				}
			}
		}
		
		//Main generation loop
		for(int k = 0; k <numberOfRooms; k++){
			con = true;
			count = 0;
			
			//generates the room
			
		do{
			//number of times this loop has ran
			count++;
		//Next two lines generates to random points	
		randomX = (int) Math.round(Math.random()*width); 
		randomY = (int) Math.round(Math.random()*height);
		
		
		//Take the two random points we got in the last step and makes sure non of them are out of range
		if(randomX == 0 || randomY == 0 || randomX-1 == 0 || randomY-1==0
				|| randomX == width || randomY == height || randomX + 1 == width
				|| randomY+1 == height){
			
			//if they are out of range restart the do while loop
			continue;
		}
		
		//checks if that random point is empty
		if(tiles[randomX][randomY][wz]== Tile.VOID){
			//if it is sets room to the room varible
			room = roomRep.randomTestRoom();
		
			if(tiles[randomX+1][randomY][wz].getIsWall() && tileViable(randomX+1, randomY,wz)
					&& roomSpaceFree(randomX-room.getRoomWidth()+2,randomY-((room.getRoomHeight()/2)+(int)Math.round(Math.random()*(room.getRoomHeight()-(room.getRoomHeight()/2)))),
							wz, room.getRoomWidth(), room.getRoomHeight(), notOpen)){
				
				break;
				
			}
			
			
			//all of these if statments check to see if the random point is directly next to a wall in any direction
			//and if it is the funtion does the nessisary actions to place the room in that space.
			if(tiles[randomX+1][randomY][wz].getIsWall() && tileViable(randomX+1, randomY,wz)
					&& roomSpaceFree(randomX-room.getRoomWidth()+2,randomY-((room.getRoomHeight()/2)+(int)Math.round(Math.random()*(room.getRoomHeight()-(room.getRoomHeight()/2)))),
							wz, room.getRoomWidth(), room.getRoomHeight(), notOpen)){
			
				con = false;//do not continue the loop
				
				
				//adds the room
				this.AddRoomToWorld(room, randomX-room.getRoomWidth()+2
						,randomY-((room.getRoomHeight()/2)+(int)Math.round(Math.random()
								*(room.getRoomHeight()-(room.getRoomHeight()/2)))), wz);
				
				//carves out and protects an entry way
				tiles[randomX+1][randomY][wz] = Tile.DIRT;
				tiles[randomX][randomY][wz] = Tile.DIRT;
				notOpen[randomX+1][randomY][wz] = true;
				notOpen[randomX][randomY][wz] = true;
				
				
				
				//breaks the loop
				break;
			}
			
			//all of these if statments check to see if the random point is directly next to a wall in any direction
			//and if it is the funtion does the nessisary actions to place the room in that space.
			else if(tiles[randomX-1][randomY][wz].getIsWall()&& tileViable(randomX-1, randomY, wz)
					&& roomSpaceFree( randomX -1,randomY-(room.getRoomHeight()/2),wz,
							room.getRoomWidth(), room.getRoomHeight(), notOpen))
			{
			
				con = false;
				
				
				this.AddRoomToWorld(room, randomX -1,randomY-(room.getRoomHeight()/2),wz);
				
				tiles[randomX-1][randomY][wz] = Tile.DIRT;
				tiles[randomX-2][randomY][wz] = Tile.DIRT;
				notOpen[randomX-1][randomY][wz] = true;
				notOpen[randomX-2][randomY][wz] = true;
				break;
		}
			
			//all of these if statments check to see if the random point is directly next to a wall in any direction
			//and if it is the funtion does the nessisary actions to place the room in that space.
			else if(tiles[randomX][randomY+1][wz].getIsWall()&& tileViable(randomX, randomY+1,wz)
					&& roomSpaceFree( randomX-(room.getRoomWidth()/2),randomY-room.getRoomHeight()+2,wz,
							room.getRoomWidth(), room.getRoomHeight(), notOpen)){
			
				con = false; 
				
				
				this.AddRoomToWorld(room, randomX-(room.getRoomWidth()/2),randomY-room.getRoomHeight()+2, wz);
				tiles[randomX][randomY+1][wz] = Tile.DIRT;
				tiles[randomX][randomY][wz] = Tile.DIRT;
				notOpen[randomX][randomY+1][wz] = true;
				notOpen[randomX][randomY][wz] = true;	
				
			}
			
			//all of these if statments check to see if the random point is directly next to a wall in any direction
			//and if it is the funtion does the nessisary actions to place the room in that space.
			else if(tiles[randomX][randomY-1][wz].getIsWall()&& tileViable(randomX, randomY-1,wz)
					&& roomSpaceFree( randomX-(room.getRoomWidth()/2),randomY-1,wz,
							room.getRoomWidth(), room.getRoomHeight(), notOpen)){
				
				con = false;
				
			
				this.AddRoomToWorld(room, randomX-(room.getRoomWidth()/2),randomY-1, wz);
				tiles[randomX][randomY-1][wz] = Tile.DIRT;
				tiles[randomX][randomY][wz] = Tile.DIRT;
				notOpen[randomX][randomY-1][wz] = true;
				notOpen[randomX][randomY][wz] = true;
			}
		
		}
		}while(con);
		
		}
		
		//this funtion help get rid of random go nowwehre indentions
		smoothFloor(1, wz);
			
		}
		ReplaceAllVoid(Tile.WALL);
		addStairs(1);
		return this;
	}
	//---------------------------End of room based generation code----------------------------
	
	/**returns if the area provided intersects
	 *  with any protected spaces*/
	public boolean roomSpaceFree(int x, int y, int z, int w, int h, boolean[][][] p){
		
		for(int i = x; i<(x+w);i++){
			for(int j = y; j<(h+y); j++){
				
				if(i<width && j<width && i>=0 && j>=0){
				if(p[i][j][z] == true){
					

					return false;
				}
				}else{return false;}
			}}
	
		

		return true;
		
	}
	
	/**used in world based random map genration to determine if a wall has two walls next to it*/
	public boolean tileViable(int x, int y, int z){
		int count = 0;
		
		
		
		if(tiles[x+1][y][z].getIsWall()){count++;}
		if(tiles[x-1][y][z].getIsWall()){count++;}
		if(tiles[x][y+1][z].getIsWall()){count++;}
		if(tiles[x][y-1][z].getIsWall()){count++;}
		
		if(count==2){return true;}else{return false;}
		
	}
	
	/**Smooths out the map a bit */
	public void smoothFloor(int iterations, int z){
		
		int count = 0;
		for(int i = 0; i<iterations;i++){
		for(int x = 0; x < width; x++){
			for(int y = 0; y<height;y++){
				
	if(tiles[x][y][z].getIsWall() == false && x > 0 && y > 0 && x < width-1 && y < height-1){
		count=0;
		if(tiles[x+1][y][z].getIsWall()){count++;}
		if(tiles[x-1][y][z].getIsWall()){count++;}
		if(tiles[x][y+1][z].getIsWall()){count++;}
		if(tiles[x][y-1][z].getIsWall()){count++;}
		
		if(count >=3){
			if(tiles[x+1][y][z].getIsWall()){tiles[x][y]=tiles[x+1][y];}
			else if(tiles[x-1][y][z].getIsWall()){tiles[x][y]= tiles[x-1][y];}
			else if(tiles[x][y+1][z].getIsWall()){tiles[x][y] = tiles[x][y+1];}
			else if(tiles[x][y-1][z].getIsWall()){tiles[x][y] = tiles[x][y-1];}
			
					}
	    		}
			}}
		}
	}
	
	
	//Adds stairs the the world
public void addStairs(int stairDensity){
		
		//these are the 4 zones of the war
		Tile[][][] sectorOne= new Tile[width/2][height/2][numberOfFloors];
		Tile[][][] sectorTwo= new Tile[width/2][height/2][numberOfFloors];
		Tile[][][] sectorThree= new Tile[width/2][height/2][numberOfFloors];
		Tile[][][] sectorFour= new Tile[width/2][height/2][numberOfFloors];
		
		for(int x = 0; x<width; x++){
			for(int y = 0; y<height; y++){
				for(int z = 0; z<numberOfFloors; z++){
					
				if(x<width/2 && y<height/2){sectorOne[x][y][z] = tiles[x][y][z];}
				else if(x>=width/2 && y<height/2){sectorTwo[x-width/2][y][z] = tiles[x][y][z];}
				else if(x<width/2 && y>=height/2){sectorThree[x][y-height/2][z] = tiles[x][y][z];}
				else if(x>=width/2 && y>=height/2){sectorFour[x-width/2][y-height/2][z]=tiles[x][y][z];}
					
				}}}
		
		//handel sector one
		for(int k = 0; k<numberOfFloors; k++){
			int newX = 0;
			int newY = 0;
			int test = 0;
			
			while(stairCount(sectorOne, k)<stairDensity){
	
				test++;
				
				for(int x = 0; x<width; x++){
					for(int y = 0; y<height; y++){
						for(int z = 0; z<numberOfFloors; z++){
							
						if(x<width/2 && y<height/2){sectorOne[x][y][z] = tiles[x][y][z];}
						else if(x>=width/2 && y<height/2){sectorTwo[x-width/2][y][z] = tiles[x][y][z];}
						else if(x<width/2 && y>=height/2){sectorThree[x][y-height/2][z] = tiles[x][y][z];}
						else if(x>=width/2 && y>=height/2){sectorFour[x-width/2][y-height/2][z]=tiles[x][y][z];}
							
						}}}
				
				//finds random points 
				newX = ThreadLocalRandom.current().nextInt(0, (width/2));
				newY = ThreadLocalRandom.current().nextInt(0, height/2);
				
				if(k<numberOfFloors-1){
					//.out.println(k+"/"+numberOfFloors);
					
				if(sectorOne[newX][newY][k].isGround()&&sectorOne[newX][newY][k+1].isGround()){addStair(tiles, newX, newY, k);}
				}else{
					if(sectorOne[newX][newY][k].isGround()){addStair(tiles, newX, newY, k);}
				}
				//System.out.println("one");
					
				//if(test>1000){break;}
			}
			
			while(stairCount(sectorTwo, k)<stairDensity){
			
				test++;
				
				for(int x = 0; x<width; x++){
					for(int y = 0; y<height; y++){
						for(int z = 0; z<numberOfFloors; z++){
							
						if(x<width/2 && y<height/2){sectorOne[x][y][z] = tiles[x][y][z];}
						else if(x>=width/2 && y<height/2){sectorTwo[x-width/2][y][z] = tiles[x][y][z];}
						else if(x<width/2 && y>=height/2){sectorThree[x][y-height/2][z] = tiles[x][y][z];}
						else if(x>=width/2 && y>=height/2){sectorFour[x-width/2][y-height/2][z]=tiles[x][y][z];}
							
						}}}
				
				//finds random points 
				newX = ThreadLocalRandom.current().nextInt(0, (width/2));
				newY = ThreadLocalRandom.current().nextInt(0, height/2);
				if(k<numberOfFloors-1){
				if(sectorTwo[newX][newY][k].isGround()&& sectorTwo[newX][newY][k+1].isGround()){addStair(tiles, newX+width/2, newY, k);}
				}else{
					if(sectorTwo[newX][newY][k].isGround()){
						addStair(tiles, newX+width/2, newY, k);
					System.out.println("pladed in sec 2");
				}
				
				//if(test>1000){break;}
			}
				
			}
			while(stairCount(sectorThree, k)<stairDensity){
				
				test++;
				
				for(int x = 0; x<width; x++){
					for(int y = 0; y<height; y++){
						for(int z = 0; z<numberOfFloors; z++){
							
						if(x<width/2 && y<height/2){sectorOne[x][y][z] = tiles[x][y][z];}
						else if(x>=width/2 && y<height/2){sectorTwo[x-width/2][y][z] = tiles[x][y][z];}
						else if(x<width/2 && y>=height/2){sectorThree[x][y-height/2][z] = tiles[x][y][z];}
						else if(x>=width/2 && y>=height/2){sectorFour[x-width/2][y-height/2][z]=tiles[x][y][z];}
							
						}}}
				
				//finds random points 
				newX = ThreadLocalRandom.current().nextInt(0, (width/2));
				newY = ThreadLocalRandom.current().nextInt(0, height/2);
				
				if(k<numberOfFloors-1){
				if(sectorThree[newX][newY][k].isGround()&&sectorThree[newX][newY][k+1].isGround()){addStair(tiles, newX, newY+height/2, k);}
				}else{
					if(sectorThree[newX][newY][k].isGround()){addStair(tiles, newX, newY+height/2, k);}
				}
				//System.out.println("three");
				//if(test>1000){break;}
			}
			
while(stairCount(sectorFour, k)<stairDensity){
				
				test++;
				
				for(int x = 0; x<width; x++){
					for(int y = 0; y<height; y++){
						for(int z = 0; z<numberOfFloors; z++){
							
						if(x<width/2 && y<height/2){sectorOne[x][y][z] = tiles[x][y][z];}
						else if(x>=width/2 && y<height/2){sectorTwo[x-width/2][y][z] = tiles[x][y][z];}
						else if(x<width/2 && y>=height/2){sectorThree[x][y-height/2][z] = tiles[x][y][z];}
						else if(x>=width/2 && y>=height/2){sectorFour[x-width/2][y-height/2][z]=tiles[x][y][z];}
							
						}}}
				
				//finds random points 
				newX = ThreadLocalRandom.current().nextInt(0, (width/2));
				newY = ThreadLocalRandom.current().nextInt(0, height/2);
				
				if(k<numberOfFloors-1){
				if(sectorFour[newX][newY][k].isGround()&&sectorFour[newX][newY][k+1].isGround()){addStair(tiles, newX+width/2, newY+height/2, k);}
				}else{if(sectorFour[newX][newY][k].isGround()){addStair(tiles, newX+width/2, newY+height/2, k);}
				}
			
				//if(test>1000){break;}
				//System.out.println("four");
			}
			
		
			}
		
	}

	
public void ReplaceAllVoid(Tile t){
	for(int x = 0; x < width; x++){
		for(int y = 0; y < width; y++){
			for(int z = 0; z<numberOfFloors; z++){
				
				if(tiles[x][y][z] == Tile.VOID){tiles[x][y][z] = t;}
	
}
}}}

public int stairCount(Tile[][][] t, int z){
	int stairCount = 0;
	
	for(int x = 0; x < t.length; x++){
		for(int y = 0; y < t[0].length; y++){
			
				
				if(t[x][y][z] == Tile.UpSTAIR){
					stairCount++;
				}
				
				
			}}
	
	
	return stairCount;
}

public int stairCount(Tile[][][] t, int width, int height, int z){
	int stairCount = 0;
	
	for(int x = 0; x < width; x++){
		for(int y = 0; y < height; y++){
			
				
				if(t[x][y][z] == Tile.UpSTAIR){
					
					stairCount++;
				}
				
				
			}}
	
	
	return stairCount;
}

public int countAllStairs(Tile[][][] t){
int stairCount = 0;
	
	for(int x = 0; x < width; x++){
		for(int y = 0; y < height; y++){
			for(int z = 0; z<numberOfFloors; z++){
				
				if(t[x][y][z] == Tile.UpSTAIR){
					
					stairCount++;
				}
				
				
			}}}
	
	
	return stairCount;
	
}

public void addStair(Tile[][][] t, int x, int y, int z){

	
	t[x][y][z] = Tile.UpSTAIR;
	
	if(z <numberOfFloors-1){
	if(t[x][y][z+1].getWalkable()){
		
		t[x][y][z+1]=Tile.DOWNSTAIR;
	} else{
			AddRoomToWorld(roomRep.StairRoom, x-(roomRep.StairRoom.getRoomWidth()/2), 
			y-(roomRep.StairRoom.getRoomHeight()/2), z+1);
			t[x][y][z+1]=Tile.DOWNSTAIR;
	}
	
}

}
}
	
