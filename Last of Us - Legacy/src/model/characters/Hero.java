package model.characters;

import java.awt.Point;
import java.util.ArrayList;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.*;
import exceptions.*;


public abstract class Hero extends Character {
	

		private int actionsAvailable;
		private int maxActions;
		private ArrayList<Vaccine> vaccineInventory;
		private ArrayList<Supply> supplyInventory;
		private boolean specialAction;
	
		
		public Hero(String name,int maxHp, int attackDmg, int maxActions) {
			super(name,maxHp, attackDmg);
			this.maxActions = maxActions;
			this.actionsAvailable = maxActions;
			this.vaccineInventory = new ArrayList<Vaccine>();
			this.supplyInventory=new ArrayList<Supply>();
			this.specialAction=false;
		
		}

		
	


		public boolean isSpecialAction() {
			return specialAction;
		}



		public void setSpecialAction(boolean specialAction) {
			this.specialAction = specialAction;
		}



		public int getActionsAvailable() {
			return actionsAvailable;
		}



		public void setActionsAvailable(int actionsAvailable) {
			this.actionsAvailable = actionsAvailable;
		}



		public int getMaxActions() {
			return maxActions;
		}



		public ArrayList<Vaccine> getVaccineInventory() {
			return vaccineInventory;
		}


		public ArrayList<Supply> getSupplyInventory() {
			return supplyInventory;
		}





		public void addToVaccineInventory(Vaccine vaccine) {
			vaccineInventory.add(vaccine);
		}





		public void addToSupplyInventory(Supply supply) {
			supplyInventory.add(supply);
		}

		public void removeFromVaccineInventory(Vaccine vaccine) throws NoAvailableResourcesException{
				if(vaccineInventory.isEmpty())
					throw new NoAvailableResourcesException("No remaining vaccines");
				vaccineInventory.remove(0);
		}
		
		public void removeFromSupplyInventory(Supply supply) throws NoAvailableResourcesException{
				if(supplyInventory.isEmpty())
					throw new NoAvailableResourcesException("No remaining supplies");
				supplyInventory.remove(0);
				this.setSpecialAction(true);

		}
		public abstract void attack() throws NotEnoughActionsException, InvalidTargetException;
		
		public void move(Direction d) throws MovementException, NotEnoughActionsException{
			Point thisLocation=this.getLocation();
			if(this.getActionsAvailable()==0){
				throw new NotEnoughActionsException("Not enough actions available");
			}
			switch(d){
			case UP:
				if(thisLocation.x==14){
					throw new MovementException("Cannot move up");
				}
				if((Game.map[thisLocation.x+1][thisLocation.y]) instanceof CharacterCell){
					if(((CharacterCell) (Game.map[thisLocation.x+1][thisLocation.y])).getCharacter()!=null)
						throw new MovementException("Cell occupied");
				}
				if((Game.map[thisLocation.x+1][thisLocation.y]) instanceof CollectibleCell){
					((CollectibleCell)(Game.map[thisLocation.x+1][thisLocation.y])).getCollectible().pickUp(this);
					}
				if((Game.map[thisLocation.x+1][thisLocation.y]) instanceof TrapCell){
					this.setCurrentHp(this.getCurrentHp()-((TrapCell)(Game.map[thisLocation.x+1][thisLocation.y])).getTrapDamage());
					
				}
				((CharacterCell)(Game.map[thisLocation.x][thisLocation.y])).setCharacter(null);
				thisLocation.x++;
				
				;break;
			case DOWN:
				if(thisLocation.x==0){
					throw new MovementException("Cannot move down");
			}
			if((Game.map[thisLocation.x-1][thisLocation.y]) instanceof CharacterCell){
				if(((CharacterCell) (Game.map[thisLocation.x-1][thisLocation.y])).getCharacter()!=null)
					throw new MovementException("Cell occupied");
			}
			if((Game.map[thisLocation.x-1][thisLocation.y]) instanceof CollectibleCell){
				((CollectibleCell)(Game.map[thisLocation.x-1][thisLocation.y])).getCollectible().pickUp(this);
				}
			if((Game.map[thisLocation.x-1][thisLocation.y]) instanceof TrapCell){
				this.setCurrentHp(this.getCurrentHp()-((TrapCell)(Game.map[thisLocation.x-1][thisLocation.y])).getTrapDamage());
				
			}
			((CharacterCell)(Game.map[thisLocation.x][thisLocation.y])).setCharacter(null);
			thisLocation.x--;
			
			;break;
			
			case RIGHT: if(thisLocation.y==14){
				throw new MovementException("Cannot move right");
			}
			if((Game.map[thisLocation.x][thisLocation.y+1]) instanceof CharacterCell){
				if(((CharacterCell) (Game.map[thisLocation.x][thisLocation.y+1])).getCharacter()!=null)
					throw new MovementException("Cell occupied");
			}
			if((Game.map[thisLocation.x][thisLocation.y+1]) instanceof CollectibleCell){
				((CollectibleCell)(Game.map[thisLocation.x][thisLocation.y+1])).getCollectible().pickUp(this);
				}
			if((Game.map[thisLocation.x][thisLocation.y+1]) instanceof TrapCell){
				this.setCurrentHp(this.getCurrentHp()-((TrapCell)(Game.map[thisLocation.x][thisLocation.y+1])).getTrapDamage());
				
			}
			((CharacterCell)(Game.map[thisLocation.x][thisLocation.y])).setCharacter(null);
			thisLocation.y++;
			
			;break;
			
			case LEFT: if(thisLocation.y==0){
				throw new MovementException("Cannot move left");
			}
			if((Game.map[thisLocation.x][thisLocation.y-1]) instanceof CharacterCell){
				if(((CharacterCell) (Game.map[thisLocation.x][thisLocation.y-1])).getCharacter()!=null)
					throw new MovementException("Cell occupied");
			}
			if((Game.map[thisLocation.x][thisLocation.y-1]) instanceof CollectibleCell){
				((CollectibleCell)(Game.map[thisLocation.x][thisLocation.y-1])).getCollectible().pickUp(this);
				}
			if((Game.map[thisLocation.x][thisLocation.y-1]) instanceof TrapCell){
				this.setCurrentHp(this.getCurrentHp()-((TrapCell)(Game.map[thisLocation.x][thisLocation.y-1])).getTrapDamage());
				
			}
			((CharacterCell)(Game.map[thisLocation.x][thisLocation.y])).setCharacter(null);
			thisLocation.y--;
			//han7otaha fel a5r 
			;break;
			}
			if(this.getCurrentHp()==0){
				this.onCharacterDeath();
				Game.map[thisLocation.x][thisLocation.y]=new CharacterCell(null);
			}
			
			else{
				this.setActionsAvailable(this.getActionsAvailable()-1);
				this.setLocation(thisLocation);
				this.visibleAdjacentCells();
				Game.map[thisLocation.x][thisLocation.y]=new CharacterCell(this);
			}
			
		}
		public void useSpecial ()throws NoAvailableResourcesException,InvalidTargetException,NotEnoughActionsException{
			if(supplyInventory.isEmpty()){
				throw new NoAvailableResourcesException("No supplies");
			}
			Supply supply=supplyInventory.get(supplyInventory.size()-1);
			supply.use(this);
			this.setSpecialAction(true);//neb2a nemsa7a
			
			
		}
		public void cure() throws InvalidTargetException, NoAvailableResourcesException, NotEnoughActionsException{//lsa m5lstsh
			
			vaccineInventory.get(0).use(this);
		}
		public void visibleAdjacentCells(){
			Point thisLocation=this.getLocation();
			for (int i=0;i<15 ;i++) {
				for(int j=0;j<15;j++) {
					Cell currentCell=Game.map[i][j];
					double distance=thisLocation.distance(i,j);
					if(distance==1 || distance==Math.sqrt(2)||distance==0){
						currentCell.setVisible(true);
					}
				}
			}
		}
		

	
}
