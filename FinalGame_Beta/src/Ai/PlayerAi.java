package Ai;

import java.util.ArrayList;
import java.util.List;

import World.Tile;
import asciiPanel.AsciiPanel;
import Vision.FieldofView;
import Creature.*;
import Screens.LoseScreen;

public class PlayerAi extends CreatureAi {

	private FieldofView fov;
	
	//The number of turns it takes the player to regenerate the amount of health specified by healPower
	private int healDelay;
	private int turnsTillHeal;
	public int healDelay(){ return this.healDelay; }
	public void modifyHealDelay(int amount){ this.healDelay += amount; }
	
	//The amount of health regenerated every time the healDelay hits 0
	private int healPower;
	public int healPower(){ return this.healPower; }
	public void modifyHealPower(int amount){ this.healPower += amount; }
	
	public PlayerAi(Creature creature, FieldofView fov) {
		super(creature);
		this.fov = fov;
		this.healDelay = 15;
		this.turnsTillHeal = this.healDelay * 2;
		this.healPower = 5;
	}
	
	
	public void onEnterSpace(int x, int y, int z, Tile tile) { 
		 
		if(tile.getWalkable()){
			this.creature.x = x;
		 	this.creature.y = y;
		 	this.creature.z = z;
		 	
		}else {
		 	
		}
	}
	 
		public boolean canSee(int wx, int wy, int wz){
			return fov.isVisible(wx, wy, wz);
		}
		
		public Tile rememberedTile(int wx, int wy, int wz){
			return fov.tile(wx, wy, wz);
		}

		public void onUpdate(){
			if (this.turnsTillHeal <= 0){
				this.creature.modifyHp(this.healPower);
				this.turnsTillHeal = this.healDelay * 2;
			} else {
				this.turnsTillHeal -= 1;
			}
		}
}
