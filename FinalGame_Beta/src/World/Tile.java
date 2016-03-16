package World;
import asciiPanel.AsciiPanel;
import java.awt.Color;

public enum Tile {//The tile enumeration is bascicaly a list of all the envirmental tiles avaible for the world
	FLOOR("Floor", (char)46, AsciiPanel.red, AsciiPanel.black, true),
	DIRT("Dirt",(char)46, AsciiPanel.yellow, AsciiPanel.black, true),
	WALL("Stone Wall",(char)177, AsciiPanel.white, AsciiPanel.black, false, true),
	TREE("Strange Tree", (char)6, AsciiPanel.brightGreen, AsciiPanel.black, false),
	BLUE("Blue", (char)46, AsciiPanel.blue, AsciiPanel.black, true),
	SHRINE("It is a shrine",(char)143, AsciiPanel.green, AsciiPanel.black, true),
	UpSTAIR("Just some stairs",(char)24, AsciiPanel.brightMagenta,AsciiPanel.black,true),
	DOWNSTAIR("Just some stairs", (char)25, AsciiPanel.brightMagenta, AsciiPanel.black, true),
	VOID("Nothing", (char)7, AsciiPanel.yellow, AsciiPanel.yellow, false),
	UNKNOWN("Unknown", ' ', AsciiPanel.white, AsciiPanel.white, true);
	
	//The name displayed to the player if it ever is
	private String displayName;
	//symbol stores the ascii character that represents the tile
	private char symbol;
	
	//symbolColor stores the color that the symbol is drawn in
	private Color symbolColor;
	
	//symbolBackgroundColor determine the background color of the symbol
	private Color backgroundColor;
	
	private boolean walkable;
	
	private boolean isWall;
	
	public String details(){
		return String.format("    IsWalkable:%b", walkable);
	}
	
	//a constructor for creating new entries in the enum
	Tile(String displayName, char s, Color c1, Color c2, boolean walkable){
		this.displayName = displayName;
		this.symbol = s;
		this.symbolColor = c1;
		this.backgroundColor = c2;
		this.walkable = walkable;
		
		this.isWall = false;
		
		}
	
	Tile(String displayName, char s, Color c1, Color c2, boolean walkable, boolean isWall){
		this.displayName = displayName;
		this.symbol = s;
		this.symbolColor = c1;
		this.backgroundColor = c2;
		this.walkable = walkable;
		
		this.isWall = isWall;
		
		}
	
	//a constructor for creating new entries in the enum
	Tile(char s){
		this.symbol = s;
	}
	
	//Get symbol returns the symbol of a tile
	public char getSymbol(){
		
		
		return this.symbol;
	}
	
	//getSymbolColor returns the color of a tile
	public Color getSymbolColor(){
		
		
		return this.symbolColor;
	}
	
	public boolean getWalkable(){
		
		
		return this.walkable;
	}
	
	public boolean isGround() {
		return this != WALL && this != VOID;
	}
	
	public String getDisplayName(){
		
		return this.displayName;
	}
	
	public Color getBackgroundColor(){return this.backgroundColor;}
	
	public boolean isStairs(){ 
		return this == UpSTAIR || this == DOWNSTAIR;
	}

	
	public boolean getIsWall(){return this.isWall;}
}


