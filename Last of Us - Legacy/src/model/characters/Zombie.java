package model.characters;

import java.awt.Point;
import java.util.Random;

import engine.Game;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

public class Zombie extends Character {
	static int ZOMBIES_COUNT = 1;
	
	public Zombie() {
		super("Zombie " + ZOMBIES_COUNT, 40, 10);
		ZOMBIES_COUNT++;
	}
	public void attack(){
		Point thisLocation = this.getLocation();
		boolean flag=true;
		for (int i=0;i<15 && flag;i++) {
			for(int j=0;j<15;j++) {
				Cell currentCell=Game.map[i][j];
				//bnshoof lw el cell gowaha character
				if(currentCell instanceof CharacterCell && ((CharacterCell) currentCell).getCharacter()!= null ) {
					Character target=((CharacterCell) currentCell).getCharacter();
					//bnt2kd eno hero
					if(target instanceof Hero) {
						Point targetLocation=target.getLocation();
						double distance = thisLocation.distance(targetLocation.getX(),targetLocation.getY());
						if(distance==1 || distance==Math.sqrt(2)){
							this.setTarget(target);
							int hp = target.getCurrentHp();
							hp = hp-10;
							target.setCurrentHp(hp);
							target.defend(this);
							if(target.getCurrentHp()==0)
								target.onCharacterDeath();
							flag=false;
						}
					}
					
				}
			}
		}
		
	}
	public void helperZombieTarget(){
		Point thisLocation = this.getLocation();
		boolean flag=true;
		for (int i=0;i<15 && flag;i++) {
			for(int j=0;j<15;j++) {
				Cell currentCell=Game.map[i][j];
				//bnshoof lw el cell gowaha character
				if(currentCell instanceof CharacterCell) {
					Character target=((CharacterCell) currentCell).getCharacter();
					//bnt2kd eno hero
					if(target instanceof Hero) {
						Point targetLocation=target.getLocation();
						double distance=thisLocation.distance(targetLocation.getX(),targetLocation.getY());
						if(distance==1 || distance==Math.sqrt(2)){
							this.setTarget(target);
							/*int hp = target.getCurrentHp();
							hp = hp-10;
							target.setCurrentHp(hp);
							target.defend(this);
							if(target.getCurrentHp()==0)
								target.onCharacterDeath();*/
							flag=false;
						}
					}
					
				}
			}
		}
	}
	
		
		
	
	public void onCharacterDeath(){
		super.onCharacterDeath();
		Random rand = new Random();
		int x = rand.nextInt(15);
		int y = rand.nextInt(15);
		Cell randCell =Game.map[x][y];
		while(((randCell instanceof CharacterCell) && ((CharacterCell)randCell).getCharacter()!=null)||
				(randCell instanceof CollectibleCell) ||
				(randCell instanceof TrapCell)){
			x = rand.nextInt(15);
			y = rand.nextInt(15);
			randCell =Game.map[x][y];
		}
		/*Zombie zombie=new Zombie();
		Game.map[x][y]=new CharacterCell(zombie);
		Game.zombies.add(zombie);*/
		Zombie zombie=new Zombie();
		Point zombieLocation=new Point(x,y);
		zombie.setLocation(zombieLocation);
		Game.map[x][y]=new CharacterCell(zombie);
		Game.zombies.add(zombie);
	}
}
	




