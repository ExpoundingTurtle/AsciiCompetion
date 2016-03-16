package Combat;

import Creature.Creature;

public class AbilityHandler {
	int abilityId;
	Creature user;
	Creature target;
	
	public AbilityHandler(){
	}
	
	public void handle(int abilityId, Creature user, Creature target){
		switch (abilityId){
		case 0: basicAttack(user, target); break;
		}
	}
	
	/** A creatures basic attack */
	public void basicAttack(Creature user, Creature target){
		target.modifyHp(-user.attack(), user);
	}
}
