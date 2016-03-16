package World;
import World.Room;

public class RoomRepository {
	
	public static Room StartingRoom; 
	public static Room TestRoom;
	public static Room BlueRoom;
	public static Room ShrineRoom;
	public static Room Core;
	public static Room StairRoom;
	public static Room HorizontalPassage;
	public static Room VerticalPassage;
	
	public RoomRepository(){
		
		StartingRoom = new Room(20,20).fillRoom(Tile.FLOOR).createOuterWalls().placeTile(15, 0, Tile.FLOOR);
		
		TestRoom = new Room(15, 15).fillRoom(Tile.DIRT).createOuterWalls().placeTile(4, 4, Tile.TREE).placeTile(4, 15, Tile.DIRT);
		
		BlueRoom = new Room(25, 7).fillRoom(Tile.BLUE).createOuterWalls().placeTile(0, 3, Tile.BLUE);
		
		ShrineRoom = new Room(14, 14).fillRoom(Tile.FLOOR).createOuterWalls().placeTile(7, 7, Tile.SHRINE);
		
		Core = new Room(20, 20).fillRoom(Tile.DIRT).createOuterWalls().placeTile(0, 5, Tile.DIRT)
				.placeTile(5, 0, Tile.DIRT).placeTile(10, 5, Tile.BLUE).placeTile(5, 10, Tile.DIRT);
		
		StairRoom = new Room(5,5).fillRoom(Tile.FLOOR).createOuterWalls()
				.placeTile(2, 0, Tile.FLOOR).placeTile(0, 2, Tile.FLOOR).placeTile(2, 2, Tile.FLOOR)
				.placeTile(2,5, Tile.FLOOR);
		
		HorizontalPassage = new Room(20,5).fillRoom(Tile.FLOOR).createOuterWalls().placeTile(10, 0, Tile.FLOOR)
				.placeTile(0, 2, Tile.FLOOR).placeTile(10, 5, Tile.FLOOR).placeTile(20, 2, Tile.FLOOR);
		
		VerticalPassage = new Room(5,20).fillRoom(Tile.FLOOR).createOuterWalls().placeTile(0, 10, Tile.FLOOR)
				.placeTile(2, 0, Tile.FLOOR).placeTile(5, 10, Tile.FLOOR).placeTile(2, 20, Tile.FLOOR);
	}
		

	public Room randomTestRoom(){
		Room r = StartingRoom;
		if(Math.random()>.1){
	switch((int)Math.round(Math.random()*3)){
	
	case 0:  break;
	case 1:r = TestRoom; break;
	case 2:r = BlueRoom; break;
	case 3:r=ShrineRoom; break;

	
	}
		}else{
			
			if(Math.random()>.5){
				return HorizontalPassage;
			}else{
				return VerticalPassage;}
			
		}
	return r;}
}
