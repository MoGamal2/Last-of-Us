package model.collectibles;


import java.awt.Point;
import java.util.Random;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.characters.*;
import model.characters.Character;
import model.world.CharacterCell;


public class Vaccine implements Collectible {

	
	public Vaccine() {
		
	}

	
	public void pickUp(Hero h)  {
		h.addToVaccineInventory(this);
	}

	
	public void use(Hero h) throws NoAvailableResourcesException, InvalidTargetException, NotEnoughActionsException {
		if(h.getActionsAvailable()==0)
			throw new NotEnoughActionsException("not enough action points");
		if(h.getTarget()==null){
			throw new InvalidTargetException("Select a zombie to cure");
		}
		if(h.getTarget().getLocation()!=null && h.getLocation()!=null){
		Character target=h.getTarget();
		Point hLocation=h.getLocation();
		Point targetLocation=target.getLocation();
		
		if(target instanceof Hero){
			throw new InvalidTargetException("Cannot cure a hero");
		}
		double distance=hLocation.distance(targetLocation);
		if(distance!=1 && distance!=Math.sqrt(2)&& distance!=0){
			throw new InvalidTargetException("Zombie is too far");
		}
		Game.zombies.remove(target);//bnsheel el zomb ml game
		h.removeFromVaccineInventory(this);
		Random rand = new Random();
		Hero hero=Game.availableHeroes.get(rand.nextInt(Game.availableHeroes.size()));
		int actionsAvailable=h.getActionsAvailable()-1;
		h.setActionsAvailable(actionsAvailable);
		
		hero.setLocation(target.getLocation());
		((CharacterCell)Game.map[target.getLocation().x][target.getLocation().y]).setCharacter(hero);
		Game.availableHeroes.remove(hero);//bnsheel el hero el 7tenah ml roster
		Game.heroes.add(hero);
		//hero.visibleAdjacentCells(); keep in our thoughts and prayers
		h.setTarget(null);
		}
		else{//dh hbl gedan
			Character target=h.getTarget();
			Game.zombies.remove(target);//bnsheel el zomb ml game
			h.removeFromVaccineInventory(this);
			Random rand = new Random();
			Hero hero=Game.availableHeroes.get(rand.nextInt(Game.availableHeroes.size()));
			int actionsAvailable=h.getActionsAvailable()-1;
			h.setActionsAvailable(actionsAvailable);
			
			hero.setLocation(target.getLocation());
			((CharacterCell)Game.map[target.getLocation().x][target.getLocation().y]).setCharacter(hero);
			Game.availableHeroes.remove(hero);//bnsheel el hero el 7tenah ml roster
			Game.heroes.add(hero);
			//hero.visibleAdjacentCells(); keep in our thoughts and prayers
			h.setTarget(null);
		}
	}

}
