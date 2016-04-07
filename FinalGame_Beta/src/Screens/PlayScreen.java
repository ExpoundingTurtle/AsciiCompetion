package Screens;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

import Combat.AbilityHandler;
import World.*;
import asciiPanel.AsciiPanel;
import Creature.*;
import Items.ItemFactory;
import Main.ApplicationMain;
import Vision.FieldofView;

public class PlayScreen implements Screen{// this screen is a test screen im using to debug map
											//generation and stuff

	private Creature player;
	
	private CreatureFactory cf;
	private ItemFactory itemFactory;
	
	private AbilityHandler ability;
		
	//the width of the screen which helps determine how much of the actual window it will take up
	private int screenWidth;
	
	//the height of the screen which helps determine how much of the actual window it will take up
	private int screenHeight;
	
	//the width of the world
	private int worldWidth;
	
	//the height of the world
	private int worldHeight;
	
	private int numberOfFloors;
	
	//a variable of type World: World is a class that I made that holds all the information about an area
	private World world;
	
	//a variable of the the worldBuilder class which generates and stores worlds.
	private WorldBuilder worldBuilder;
	
	private int top;
	private int left;
	
	private ApplicationMain main;
	
	//subscreens that appear on the main screen such as look and inventory
	private Screen subscreen;
	
	//creates a room repository
	public RoomRepository roomRep;
	
	//handels dialoug box
	public PopupHandeler popUp;
	
	public int mouseX;
	public int mouseY;
	
	public int currentZ;
	
	private FieldofView fov;
	
	private int xp;
		
	//The constructor of the Map Screen class
		public PlayScreen(){
		
			ability = new AbilityHandler();
			main = new ApplicationMain();
			worldWidth = 200;
			worldHeight = 200;
			numberOfFloors = 15;
			
			//set width
			screenWidth = main.terminalWidth() - 1;
			//set height
			screenHeight = main.terminalHeight() - 6;
			
			//set left
			left = 0;
			//set top
			top = 0;
			
			currentZ = 0;
			
			//make a new WorldBuilder and set the variable of this class to it
			worldBuilder = new WorldBuilder(worldWidth, worldHeight, numberOfFloors);
			
			//Initialize roomRep
			roomRep = new RoomRepository();
			
			//uses the world builder to generate a  world to display
			world = worldBuilder.RoomBasedRandom(50)
					.buildWorld();
		
			cf = new CreatureFactory(world);
			itemFactory = new ItemFactory(world);
		
			//player = cf.createPlayer(100,100,currentZ, fov);
			
			fov = new FieldofView(world);
			
			createCreature(cf);
			createItems(itemFactory);
			
			//initalize popuphandeler
			popUp = new PopupHandeler(0, screenHeight-1, screenWidth, 6);
			 popUp.text = "Beware the Jabberwock, my son! The jaws that bite, the claws that catch! Beware the Jubjub bird, and shun The frumious Bandersnatch! He took his vorpal sword in hand; Long time the manxome foe he sought- So rested he by the Tumtum tree And stood awhile in thought And, as in uffish thought he stood, The Jabberwock, with eyes of flame, Came whiffling through the tulgey wood, And burbled as it came! One, two! One, two! And through and through The vorpal blade went snicker-snack! He left it dead, and with its head He went galumphing back. And hast thou slain the Jabberwock? Come to my arms, my beamish boy! O frabjous day! Callooh! Callay! He chortled in his joy. Twas brillig, and the slithy toves Did gyre and gimble in the wabe: All mimsy were the borogoves, And the mome raths outgrabe.";
			//debug
			
		}
		
		private void createCreature(CreatureFactory creatureFactory) {	
			player = creatureFactory.createPlayer(fov);
			player.setLevel(1);
			creatureFactory.createLVL1ShopKeeper(player.x + 2, player.y, player.z);
			
			for (int z = 0; z < world.getWorldFloors(); z++){
				for (int i = 0; i < 20; i++) { //Change < value to change number of Fungus
					creatureFactory.createPage(z, player);
				}
			}
		}
		
		private void createItems(ItemFactory factory){
			for (int z = 0; z < world.getWorldFloors(); z++){
				for (int i = 0; i < world.getWorldWidth() * world.getWorldHeight() / 50; i++){
					factory.newArrow(z);
				}
				for (int i = 0; i < world.getWorldWidth() * world.getWorldHeight() / 50; i++){
					factory.newSword(z);
				}
			}
		}
		
		public int getScrollX() { return Math.max(0, Math.min(player.x - screenWidth / 2, world.getWorldWidth() - screenWidth)); }
		
		public int getScrollY() { return Math.max(0, Math.min(player.y - screenHeight / 2, world.getWorldHeight() - screenHeight)); }
		
	@Override

	//cycles through all the tiles of a world and displays the 2d array of tiles found in the world class
	public void displayOutput(AsciiPanel terminal) {
		left = getScrollX();
		top = getScrollY();
		
		DisplayTiles(terminal, left, top);
		
		terminal.writeCenter(repeatString('-', screenWidth), 18);
		terminal.write("Floor: " + (player.z + 1), 0, 19);
		terminal.write("level: " + player.level(), 0, 20);
		terminal.write("HP: " + player.hp() + "/" + player.maxHp(), 0, 21);
		
		displayXpBar(terminal);
		
		//------- Menu
	   if (subscreen != null)
		   subscreen.displayOutput(terminal);
	  
	popUp.display(terminal);
	}
	
	//Function to Display the tiles of the world
	private void DisplayTiles(AsciiPanel terminal, int left, int top){
		fov.update(player.x, player.y, player.z, player.visionRadius());
		
		for (int x = 0; x < screenWidth; x++) {
			for (int y = 0; y < screenHeight; y++) {
				int wx = x + left;
				int wy = y + top;
				
				
				
				if (player.canSee(wx,  wy,  player.z))
					terminal.write(world.getSpaceGlyph(wx, wy, player.z), x, y, world.color(wx, wy, player.z));
				else
					terminal.write(fov.tile(wx,  wy,  player.z).getSymbol(), x, y, Color.darkGray);
			}
		}
	}
	
	public static String repeatString(char s, int n) { 
		   StringBuilder sb = new StringBuilder(); 
		   for (int i = 0; i < n; i++) { 
		      sb.append(s); 
		   } 
		    
		   return sb.toString(); 
		}
	
	public void displayXpBar(AsciiPanel terminal){
		int xpsegments = (int)Math.floor((double)player.xp() / (double)player.requiredXp() * screenWidth);
		
		if (xpsegments < screenWidth){
			String original = "";
			char c = (char)219;
			int number = xpsegments;
				
			char[] repeat = new char[number];
			Arrays.fill(repeat, c);
			original += new String(repeat);
			terminal.write(original, 0, 23, AsciiPanel.brightYellow);
		}
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if (subscreen != null) {
			subscreen = subscreen.respondToUserInput(key);
		}else{
		switch(key.getKeyCode()){
		case KeyEvent.VK_S: player.moveBy(0, 1, 0); 
		break;
		case KeyEvent.VK_W: player.moveBy(0, -1, 0);
		break;
		case KeyEvent.VK_A: player.moveBy(-1, 0, 0);
		break;
		case KeyEvent.VK_D: player.moveBy(1, 0, 0);
		break;
		case KeyEvent.VK_Z: player.moveBy(0, 0, 1);
		break;
		case KeyEvent.VK_X: player.moveBy(0, 0, -1);
		break;
		case KeyEvent.VK_V: player.modifyXp(5);
		break;
		case KeyEvent.VK_R: new PlayScreen();
		break;
		case KeyEvent.VK_E: player.pickup();
		break;
		case KeyEvent.VK_SEMICOLON: subscreen = new LookScreen(player, "Looking", 
				player.x - getScrollX(), 
				player.y - getScrollY()); break;
		case KeyEvent.VK_I: subscreen = new InventoryScreen(0, 0, screenWidth, screenHeight + 5, player);
		break;
		
		//Debug Hp lowering code
		case KeyEvent.VK_K: player.modifyHp(-10);
		break;
		
		case KeyEvent.VK_U: if (player.z < (world.getWorldFloors() - 1)){ player.z++; }
		break;
		case KeyEvent.VK_J: if (player.z >= 1){ player.z--; }
		break;
		default: break;
		case KeyEvent.VK_T: popUp.text = "Hello"; break;
		case KeyEvent.VK_ENTER: popUp.next(); break;
		}
		
	}
		world.update();
		
		if (player.hp() < 1){
			return new LoseScreen();
		}
		
		return this;
	}

}
