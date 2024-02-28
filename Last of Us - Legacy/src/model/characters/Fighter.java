package model.characters;

import java.awt.Point;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;

public class Fighter extends Hero{

	
	public Fighter(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
	}

	
	public void attack() throws NotEnoughActionsException, InvalidTargetException {
		Character target=this.getTarget();
		if(target instanceof Hero ) {
			throw new InvalidTargetException("Cannot attack a hero");
		}
		if(target==null)
			throw new InvalidTargetException("Attack a target");
		if(!this.isSpecialAction()) {
			int actionsAvailable = this.getActionsAvailable();
			if(actionsAvailable==0){
				throw new NotEnoughActionsException("Not enough actions available");
			}
			else {
				actionsAvailable--;
				this.setActionsAvailable(actionsAvailable);
			}	
		}
		
		Point targetLocation=target.getLocation();
		Point thisLocation  = this.getLocation();
		double distance=thisLocation.distance(targetLocation.getX(),targetLocation.getY());
		if(distance!=1 && distance!=Math.sqrt(2)) {
			throw new InvalidTargetException("Target too far");
		}
		int hp = target.getCurrentHp();
		int dmg = this.getAttackDmg();
		hp = hp-dmg;
		target.setCurrentHp(hp);
		target.defend(this);
		if(target.getCurrentHp()==0)
			target.onCharacterDeath();
		
	}

	

	
	
	
	

}
