package Ai;

import World.Tile;
import World.World;
import Creature.Creature;
import Vision.Line;
import Vision.Point;

import java.util.List;

import Creature.*;

public class CreatureAi {
	public World world;
	protected Creature creature;
	
	public CreatureAi(Creature creature){
		
		this.creature = creature;
		this.world = creature.world;
		this.creature.setCreatureAi(this);
	}
	
	 public void onEnterSpace(int x, int y, int z, Tile tile) {
		if (tile.getWalkable()){
			creature.x = x;
			creature.y = y;
			creature.z = z;
		} else {
			//creature.doAction("bump into a wall");
		} 
	}

	 public void onUpdate(){
		 if (creature.hp() < 1){ 
			 world.remove(creature);
		 }
	}
		 
	 public void onInteract(){
		 System.out.println("ERROR");
	 }
		public boolean canSee(int wx, int wy, int wz) {
	        if (creature.z != wz)
	            return false;
	    
	        if ((creature.x-wx)*(creature.x-wx) + (creature.y-wy)*(creature.y-wy) > creature.visionRadius()*creature.visionRadius())
	            return false;
	    
	        for (Point p : new Line(creature.x, creature.y, wx, wy)){
	            if (creature.tile(p.x, p.y, wz).isGround() || p.x == wx && p.y == wy)
	                continue;
	        
	            return false;
	        }
	    
	        return true;
	    }
		
//----------------------------------------Begin available subroutines-----------------------------------------------------------
		
		public void wander(){
			int mx = (int)(Math.random() * 3) - 1;
			int my = (int)(Math.random() * 3) - 1;
			
			Creature other = creature.creature(creature.x + mx,  creature.y + my,  creature.z);
			
			if (other != null && other.symbol == creature.symbol)
				return;
			else
				creature.moveBy(mx,  my,  0);

		}
		
		public void hunt(Creature target){
			List<Point> points = new Path(creature, target.x, target.y).points();
			
			int mx = points.get(0).x - creature.x;
			int my = points.get(0).y - creature.y;
			
			creature.moveBy(mx,  my,  0);
		}
}