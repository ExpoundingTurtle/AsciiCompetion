package Creature;
import java.awt.Color;

import World.World;
import Items.Item;
import Items.ItemFactory;
import Items.Inventory;
import Items.InventorySlot;
import World.Tile;
import Ai.*;
import Combat.AbilityHandler;

public class Creature {
	public World world;
	public int x;
	public int y;
	public int z;
	public String name;
	
	public char symbol;
	public Color symbolColor; 
	
	private int level;
	public int level(){ return level; }
	public void setLevel(int level){ this.level = level; } 
	public void levelUp(){ this.level++; }
	public int requiredXp(){ return (490 + (this.level*20)); }
	
	private int xp;
	public int xp(){ return xp; }
	public void modifyXp(int amount){ this.xp += amount; if (xp >= requiredXp()){ this.levelUp(); this.xp = 0; } }
	public double xpRatio(){ return (double)this.xp() / (double)this.requiredXp(); }
	
	private int maxHp;
	public int maxHp(){ return maxHp; }
	private int hp;
	public int hp(){ return hp; }
	public void modifyHp(int amount){ this.hp += amount; if (hp < 1){ world.remove(this); } }
	public void modifyHp(int amount, Creature attacker){ this.hp += amount; if (hp < 1){ world.remove(this); attacker.modifyXp(10 * this.level); } }
	
	//A creatures currently equiped weapon
	private Item weapon;
	public Item weapon() { return weapon; }
	
	//A creatures currently equiped set of armor
	private Item armor;
	public Item armor() { return armor; }
	
	private int attack;
	public int attack(){ return attack; }
	
	private int defense;
	public int defense(){ return defense; }
	
	private AbilityHandler ability;
	private int activeAbility;
	public int activeAbility(){ return activeAbility; }
	public void modifyActiveAbility(int newId){ this.activeAbility = newId; }
	
	public CreatureAi ai;
	
	public Inventory inv;
	public Inventory inventory(){ return inv; }
	
	private int visionRadius;
	public int visionRadius() { return visionRadius; }
	
	private boolean isNPC;
	public boolean isNPC(){ return isNPC; }
	
	public Creature(String name, World world, char symbol, Color symbolColor, int hp, int attack, Boolean isNPC){
		this.world = world;
		this.symbol = symbol;
		this.symbolColor = symbolColor;
		this.name = name;
		this.isNPC = isNPC;
		
		this.maxHp = hp;
		this.hp = maxHp;
		this.attack = attack;
		this.defense = 10;
		this.activeAbility = 0;
		
		this.xp = 101;
		this.level = (int)Math.floor(Math.random() * 10 * (this.z + 1));
		
		this.visionRadius = 9;
		
		this.ability = new AbilityHandler();
		this.inv = new Inventory();
		
	}
	
	public String details() {
        return String.format("     level:%d     attack:%d     defense:%d     hp:%d", level, attack, defense, hp);
    }
	
	/** Sets the artificial intelligence that controls the creature */
	public void setCreatureAi(CreatureAi ai){
		this.ai = ai;
	}
	
	/** Moves this creature to a particular space within the world */
	public void moveToSpace(int x, int y, int z){
		ai.onEnterSpace(x, y, z, world.getSpaceTile(x, y, z));
	}
	
	/** Moves the Creature by the specified amount in a specific direction */
	public void moveBy(int mx, int my, int mz){
		if (mx==0 && my==0 && mz==0)
			return;
		
		Tile tile = world.tile(x+mx, y+my, z+mz);
		
		if (mz == -1){
			if (tile == Tile.UpSTAIR) {
				//doAction("walk up the stairs to level %d", z+mz+1);
			} else {
				//doAction("try to go up but are stopped by the cave ceiling");
				return;
			}
		} else if (mz == 1){
			if (tile == Tile.DOWNSTAIR) {
				//doAction("walk down the stairs to level %d", z+mz+1);
			} else {
				//doAction("try to go down but are stopped by the cave floor");
				return;
			}
		}
		
		Creature other = world.creature(x+mx, y+my, z+mz);
		
		if (other == null){
			ai.onEnterSpace(x+mx, y+my, z+mz, tile);
		}else if(!other.isNPC){
			useAbility(other);
		}else{
			interactWith(other);
		}
	}
	
	public void useAbility(Creature other){
		ability.handle(this.activeAbility, this, other);
	}
	
	public void interactWith(Creature other){
		other.ai.onInteract();
	}
	
	public boolean canSee(int wx, int wy, int wz){
		return ai.canSee(wx, wy, wz);
	}
	
	public void update(){
		
		ai.onUpdate();
	}
	
	public Tile tile(int wx, int wy, int wz){
		return world.tile(wx, wy, wz);
	}
	
	public Tile realTile(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz);
	}
	
	public boolean canEnter(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz).isGround() && world.creature(wx, wy, wz) == null;
	}
	
	public Creature creature(int wx, int wy, int wz) {
		return world.creature(wx, wy, wz);
	
	}
	
	public Item item(int wx, int wy, int wz) {
		if (canSee(wx, wy, wz))
			return world.item(wx, wy, wz);
		else
			return null;
	}
	
	public void pickup(){
		Item item = world.item(x,  y,  z);
		
		if (item == null){
		}else{
			world.remove(x, y, z);
			inv.addItem(item, item.stackSize());
		}
	}
	
	public void equip(Item item){
		if (!inv.contains(item)){
			if(inv.isFull()) {
				return;
			} else {
				world.remove(item);;
				inv.addItem(item, item.stackSize());
			}
		}
		if (item.attackValue() == 0 && item.defenseValue() == 0)
			return;
		
	
		if(item.attackValue() >= item.defenseValue()){
			unequip(weapon);
			weapon = item;
		} else { 
			unequip(armor);
			armor = item;
		}
	}
	
	public void unequip(Item item) {
		if (item == null)
			return;
		if (item == armor){
			armor = null;
			
		} else if (item == weapon) {
			weapon = null;
		}
	}
	
	public void drop (InventorySlot item){
		if (world.addAtEmptySpace(item.item,  x,  y,  z)){
			//doAction("drop a " + nameOf(item))
				inv.removeItem(item);
				unequip(item.item);
		} else {
			//notify("There's nowhere to drop the %s. ", nameOf(item));
			System.out.println("theres nowhere to drop that");
		}
	}
	
	public void remove(Creature creature){
		world.remove(creature);
	}
}

