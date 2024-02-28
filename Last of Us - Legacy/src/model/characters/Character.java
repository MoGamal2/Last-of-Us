package model.characters;

import java.awt.Point;


//import javafx.scene.control.Cell;
import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import model.world.CharacterCell;


public abstract class Character {
	private String name;
	private Point location;
	private int maxHp;
	private int currentHp;
	private int attackDmg;
	private Character target;

	
	public Character() {
	}
	

	public Character(String name, int maxHp, int attackDmg) {
		this.name=name;
		this.maxHp = maxHp;
		this.currentHp = maxHp;
		this.attackDmg = attackDmg;
	}
		
	public Character getTarget() {
		return target;
	}

	public void setTarget(Character target) {
		this.target = target;
	}
	
	public String getName() {
		return name;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		if(currentHp < 0) 
			this.currentHp = 0;
		else if(currentHp > maxHp) 
			this.currentHp = maxHp;
		else 
			this.currentHp = currentHp;
	}

	public int getAttackDmg() {
		return attackDmg;
	}
	public abstract void attack()throws NotEnoughActionsException , InvalidTargetException;
	
	
	public void defend(Character c){
		int dmg = this.getAttackDmg();
		this.setTarget(c);
		int hp = c.getCurrentHp();
		hp = hp-(int)(dmg/2);
		c.setCurrentHp(hp);
		if(c.getCurrentHp()==0)
			c.onCharacterDeath();
	}
	public void onCharacterDeath() {//3mlt fiha haga 2ol llshbb
		if(this instanceof Hero){
			Game.heroes.remove(this);
			//Game.availableHeroes.remove(this);//msh mot2kdeen mnha brdo
		}
		else{
			Game.zombies.remove(this);//ana hna mynf3sh spawn wl zomb fnfs el hta el kan fiha?
			/*Random rand = new Random();
			int x = rand.nextInt(15);
			int y = rand.nextInt(15);
			model.world.Cell randCell =Game.map[x][y];
			while(((randCell instanceof CharacterCell) && ((CharacterCell)randCell).getCharacter()!=null)||
					(randCell instanceof CollectibleCell) ||
					(randCell instanceof TrapCell)){
				x = rand.nextInt(15);
				y = rand.nextInt(15);
				randCell =Game.map[x][y];
			}
			Zombie zombie=new Zombie();
			Point zombieLocation=new Point(x,y);
			zombie.setLocation(zombieLocation);
			Game.map[x][y]=new CharacterCell(zombie);
			Game.zombies.add(zombie);*/
		}
		Point thisLocation=this.getLocation();
		((CharacterCell)(Game.map[thisLocation.x][thisLocation.y])).setCharacter(null);//di brdo mmkn n3mlha bl setters 3ady
	}
	
	

}
