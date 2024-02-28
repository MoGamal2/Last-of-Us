package model.characters;

import java.awt.Point;


import model.world.Cell;
import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;


public class Explorer extends Hero {
	

	public Explorer(String name,int maxHp, int attackDmg, int maxActions) {
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
		this.setActionsAvailable(actionsAvailable);
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
		if(target.getCurrentHp()==0)
			target.onCharacterDeath();
		
	}
	
	public void useSpecial() throws NoAvailableResourcesException,InvalidTargetException, NotEnoughActionsException{
		super.useSpecial();
		for (int i=0;i<15 ;i++) {
			for(int j=0;j<15;j++) {
				Cell currentCell=Game.map[i][j];
				currentCell.setVisible(true);
				
			}
		}
		
		
		
		
	}
	
	

	
}
