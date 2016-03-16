package Items;
import java.awt.Color;
import java.io.Serializable;

//import asciiPanel.AsciiPanel;
public class Item implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1060623638149583738L;
		private char glyph;
	    public char glyph() { return glyph; }

	    private Color color;
	    public Color color() { return color; }
	    
	    //begig stats
	    private int rangedAttackValue = 0;
		public int rangedAttackValue() { return rangedAttackValue; }
		public void modifyRangedAttackValue(int amount) { rangedAttackValue += amount; }
		
		private int attackValue = 0;
		public int attackValue() { return attackValue; }
		public void modifyAttackValue(int amount) { attackValue += amount; }
	
		private int defenseValue = 0;
		public int defenseValue() { return defenseValue; }
		public void modifyDefenseValue(int amount) { defenseValue += amount; }
		
		private boolean ranged;
		public boolean ranged(){ return ranged; }
		
		private boolean armor = false;
		public boolean armor(){ return armor; }
		//End stats
		
	    private int id;
	    
	    //returns the id 
	    public int getId(){return id;}
	    private String displayName;
	    
	    //returns the display name of the item
	    public String getDisplayName(){return this.displayName;}
	    
	    private boolean stackable;
	    
	    //return id 
	    public boolean getStackable(){return this.stackable;}
	    
	    private int stackSize = 1;
	    public int stackSize(){ return this.stackSize; }
	   
	    /** for non-stackable non-weapon items */
	    public Item(int id, char glyph, Color color, String displayName){
	        this.glyph = glyph;
	        this.color = color;
	        this.id = id;
	        this.displayName = displayName;
	        this.stackable = false;
	        this.ranged = false;
	    }
	   
	    /** for stackable non-ammo items */
	    public Item(int id, char glyph, Color color, String displayName, boolean stackable){
	        this.glyph = glyph;
	        this.color = color;
	        this.id = id;
	        this.displayName = displayName;
	        this.stackable = stackable;
	        this.ranged = false;
	    }
	    
	    /** for ammo type items, such as arrows */
	    public Item(int id, char glyph, Color color, String displayName, int damage, boolean stackable, int stackSize){
	        this.glyph = glyph;
	        this.color = color;
	        this.id = id;
	        this.displayName = displayName;
	        this.stackable = true;
	        this.ranged = false;
	        this.stackSize = stackSize;
	    }
	    
	    /** For ranged weaponry */
	    public Item(int id, char glyph, Color color, String displayName, int rangedDamage){
	        this.glyph = glyph;
	        this.color = color;
	        this.id = id;
	        this.displayName = displayName;
	        this.stackable = false;
	        this.ranged = true;
	        this.rangedAttackValue = rangedDamage;
	    }
	    
	    /** for armor and weapons */
	    public Item(int id, char glyph, Color color, String displayName, boolean armor, int stat){
	        this.glyph = glyph;
	        this.color = color;
	        this.id = id;
	        this.displayName = displayName;
	        this.stackable = false;
	        this.ranged = false;
	        
	        if (armor){
	        	this.defenseValue = stat;
	        } else {
	        	this.attackValue = stat;
	        }
	        	
	    }

	    public String details() {
	        if (this.armor == false){
	    		if (this.ranged){
	    			return String.format("     Ranged Damage:%d", rangedAttackValue);
	    		}else if(this.stackable == true){
	    			return String.format("     Attack:%d     Stack of %d", attackValue, stackSize);
	    		}else{
	    			return String.format("     Attack:%d", attackValue);
	    		}
	        }else{
	        	return String.format("     Defense%d", defenseValue);
	        }
	    }
}
