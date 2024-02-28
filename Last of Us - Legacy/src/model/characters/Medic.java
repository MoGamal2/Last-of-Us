package model.characters;

import java.awt.Point;

import exceptions.*;

public class Medic extends Hero {
	//Heal amount  attribute - quiz idea
	

	public Medic(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
		
	}
	public void attack() throws NotEnoughActionsException, InvalidTargetException{
		if(this.getTarget()==null)
			throw new InvalidTargetException("Attack a target");
		int actionsAvailable = this.getActionsAvailable();
		if(actionsAvailable==0){
			throw new NotEnoughActionsException("Not enough actions available");
		}
		actionsAvailable--;
		Character target=this.getTarget();
		
		if(target instanceof Hero ) {
			throw new InvalidTargetException("Cannot attack a hero");
		}
		
		Point targetLocation=target.getLocation();
		Point thisLocation  = this.getLocation();
		double distance=thisLocation.distance(targetLocation.getX(),targetLocation.getY());
		if(distance!=1  && distance!=Math.sqrt(2)) {
			throw new InvalidTargetException("Target too far");
		}
		
		int hp = target.getCurrentHp();
		int dmg = this.getAttackDmg();
		hp = hp-dmg;
		target.setCurrentHp(hp);
		target.defend(this);
		this.setActionsAvailable(actionsAvailable);
		if(target.getCurrentHp()==0)
			target.onCharacterDeath();
	}
	public void useSpecial ()throws NoAvailableResourcesException, InvalidTargetException, NotEnoughActionsException{
		
		
		Point thisLocation=this.getLocation();
		Character target=this.getTarget();
		if(target instanceof Zombie){
			throw new InvalidTargetException("Cannot heal a zombie");
		}
		if(target==null){
			throw new InvalidTargetException("Select an ally to heal");
		}
		super.useSpecial();
		Point targetLocation=target.getLocation();
		double distance=thisLocation.distance(targetLocation);
	
		if(distance!=1 && distance!=Math.sqrt(2)&& distance!=0){
			throw new InvalidTargetException("Ally is too far");
		}
		
		target.setCurrentHp(target.getMaxHp());
	}
	


}
