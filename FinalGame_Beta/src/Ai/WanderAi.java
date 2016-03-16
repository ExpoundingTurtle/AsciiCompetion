package Ai;

import java.util.List;
import Creature.*;
import Vision.Point;

public class WanderAi extends CreatureAi {
		private Creature player;
		
		public WanderAi(Creature creature, Creature player){
			super(creature);
			this.player = player;
		}
		
		public void onUpdate(){
			if (Math.random() < 0.2)
				return;
			
			if (creature.canSee(player.x,  player.y,  player.z))
				hunt(player);
			else
				wander();
			
			if(creature.hp() < 1){
				creature.remove(creature);
			}
		}
		
		
}