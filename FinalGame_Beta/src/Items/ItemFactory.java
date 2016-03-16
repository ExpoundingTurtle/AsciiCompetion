package Items;

import World.World;
import asciiPanel.AsciiPanel;
import Tools.*;

public class ItemFactory {
	private World world;
	private Tools tool;
	
	public ItemFactory(World world){
		this.world = world;
		this.tool = new Tools();
	}
	 
	 public Item newArrow(int depth){
		 int max = 5; int min = 1;
		 int stack = tool.randomRange(min, max);
		 Item arrow = new Item(0, (char) 27, AsciiPanel.white, "Arrows", 5, true, stack);
		 world.addAtEmptyLocation(arrow, depth);
		 return arrow;
	 }
	 
	 public Item newSword(int depth){
		 Item sword = new Item(1, (char)231, AsciiPanel.white, "Steel Sword", false, 10);
		 world.addAtEmptyLocation(sword, depth);
		 return sword;
	 }

}
