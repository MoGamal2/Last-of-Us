package model.collectibles;

import exceptions.NoAvailableResourcesException;
import model.characters.Hero;


public class Supply implements Collectible  {

	
	
	
	public Supply() {
		
	}

	
	public void pickUp(Hero h) { //fiha moshkela fl type cast mn collectible le character
		
		
		h.addToSupplyInventory(this);
		
	}

	
	public void use(Hero h) throws NoAvailableResourcesException { //milestone 2
		h.removeFromSupplyInventory(this);
		
	}


	
		
		

}
