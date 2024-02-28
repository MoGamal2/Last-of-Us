package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.*;
import model.world.*;

public class Game {
	
	public static Cell [][] map=new Cell[15][15];
	public static ArrayList <Hero> availableHeroes = new ArrayList<Hero>();
	public static ArrayList <Hero> heroes =  new ArrayList<Hero>();
	public static ArrayList <Zombie> zombies =  new ArrayList<Zombie>();
	
	
	
		
	public static void loadHeroes(String filePath)  throws IOException {
		
		
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = br.readLine();
		while (line != null) {
			String[] content = line.split(",");
			Hero hero=null;
			switch (content[1]) {
			case "FIGH":
				hero = new Fighter(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3]));
				break;
			case "MED":  
				hero = new Medic(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3])) ;
				break;
			case "EXP":  
				hero = new Explorer(content[0], Integer.parseInt(content[2]), Integer.parseInt(content[4]), Integer.parseInt(content[3]));
				break;
			}
			availableHeroes.add(hero);
			line = br.readLine();
			
			
		}
		br.close();

		
		
	}
	public static void startGame(Hero h) throws IOException{
		//map = new Cell[15][15];
		for(int i=0;i<15;i++){
			for(int j=0;j<15;j++){
				/*if(!(map[i][j] instanceof CharacterCell)||
				!(map[i][j] instanceof CollectibleCell) ||
				!(map[i][j] instanceof TrapCell)){*/
					map[i][j]=new CharacterCell(null);
				}
		}
		availableHeroes.remove(h);
		heroes.add(h);
		Point location=new Point(0,0);
		h.setLocation(location);
		((CharacterCell)map[0][0]).setCharacter(h);//bn7ot el hero
		
		
		
		Random rand = new Random();
		int x;
		int y;
		Cell randCell;
		for(int i=0;i<5;i++){
			x = rand.nextInt(15);
			y = rand.nextInt(15);
			randCell =map[x][y];
			while(((randCell instanceof CharacterCell) && ((CharacterCell)randCell).getCharacter()!=null)||
					(randCell instanceof CollectibleCell) ||
					(randCell instanceof TrapCell)){
				x = rand.nextInt(15);
				y = rand.nextInt(15);
				randCell =map[x][y];
			}
			Vaccine vaccine = new Vaccine();
			map[x][y]=new CollectibleCell(vaccine);
			
			x = rand.nextInt(15);
			y = rand.nextInt(15);
			randCell =map[x][y];
			while(((randCell instanceof CharacterCell) && ((CharacterCell)randCell).getCharacter()!=null)||
					(randCell instanceof CollectibleCell) ||
					(randCell instanceof TrapCell)){
				x = rand.nextInt(15);
				y = rand.nextInt(15);
				randCell =map[x][y];
			}
			Supply supply = new Supply();
			map[x][y]=new CollectibleCell(supply);
			
			x = rand.nextInt(15);
			y = rand.nextInt(15);
			randCell =map[x][y];
			while(((randCell instanceof CharacterCell) && ((CharacterCell)randCell).getCharacter()!=null)||
					(randCell instanceof CollectibleCell) ||
					(randCell instanceof TrapCell)){
				x = rand.nextInt(15);
				y = rand.nextInt(15);
				randCell =map[x][y];
			}
			map[x][y]=new TrapCell();
			
			x = rand.nextInt(15);
			y = rand.nextInt(15);
			randCell =map[x][y];
			while(((randCell instanceof CharacterCell) && ((CharacterCell)randCell).getCharacter()!=null)||
					(randCell instanceof CollectibleCell) ||
					(randCell instanceof TrapCell)){
				x = rand.nextInt(15);
				y = rand.nextInt(15);
				randCell =map[x][y];
			}
			Zombie zombie=new Zombie();
			Point zombie1Location=new Point(x,y);
			zombie.setLocation(zombie1Location);
			map[x][y]=new CharacterCell(zombie);
			zombies.add(zombie);
			
			x = rand.nextInt(15);
			y = rand.nextInt(15);
			randCell =map[x][y];
			while(((randCell instanceof CharacterCell) && ((CharacterCell)randCell).getCharacter()!=null)||
					(randCell instanceof CollectibleCell) ||
					(randCell instanceof TrapCell)){
				x = rand.nextInt(15);
				y = rand.nextInt(15);
				randCell =map[x][y];
			}
			zombie=new Zombie();
			Point zombie2Location=new Point(x,y);
			zombie.setLocation(zombie2Location);
			map[x][y]=new CharacterCell(zombie);
			zombies.add(zombie);
			
			
		}
		h.visibleAdjacentCells();
		
	}
	public static boolean checkWin(){
		for (int i=0;i<15 ;i++) {
			for(int j=0;j<15;j++) {
				Cell currentCell=Game.map[i][j];
				if(currentCell instanceof CollectibleCell && 
						((CollectibleCell)currentCell).getCollectible() instanceof Vaccine){
					return false;
				}
			}
		}
		
		
		for(int i=0;i<heroes.size();i++){
			if(!heroes.get(i).getVaccineInventory().isEmpty())
				return false;
		}
			if(heroes.size()<5)
				return false;
			return true;
	}
	public static boolean checkGameOver(){
		boolean vaccineGround=true;
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				if(map[i][j] instanceof CollectibleCell) {
					if(((CollectibleCell)map[i][j]).getCollectible() instanceof Vaccine) {
						vaccineGround=false;
						return vaccineGround;
					}
				}
			}
		}
		boolean vaccineCollected=true;
		for(int i=0;i<heroes.size();i++) {
			if(heroes.get(i).getVaccineInventory().size()!=0) {
				vaccineCollected=false;
				return vaccineCollected;
			}
		}
		
		return (vaccineGround && vaccineCollected)|| heroes.isEmpty() || availableHeroes.isEmpty();
		}
	
	public static void endTurn(){
		for(int i=0;i<Game.zombies.size();i++){
			//zombies.get(i).helperZombieTarget();
			zombies.get(i).attack();
			zombies.get(i).setTarget(null);
		}
		for(int i=0;i<Game.heroes.size();i++){
			heroes.get(i).setActionsAvailable(heroes.get(i).getMaxActions());
			heroes.get(i).setTarget(null);
			heroes.get(i).setSpecialAction(false);
			
		}
		for(int i=0;i<15;i++){
			for(int j=0;j<15;j++){
				map[i][j].setVisible(false);
			}
		}
		Random rand = new Random();
		int x = rand.nextInt(15);
		int y = rand.nextInt(15);
		Cell randCell =Game.map[x][y];
		while(((randCell instanceof CharacterCell) && ((CharacterCell)randCell).getCharacter()!=null)||
				(randCell instanceof CollectibleCell) ||
				(randCell instanceof TrapCell)){
//			x=(int)Math.random()*15;
//			y=(int)Math.random()*15;
			x = rand.nextInt(15);
			y = rand.nextInt(15);
			randCell =Game.map[x][y];
		}
		Zombie zombie=new Zombie();
		Point zombie2Location=new Point(x,y);
		zombie.setLocation(zombie2Location);
		map[x][y]=new CharacterCell(zombie);
		zombies.add(zombie);
		for(int i=0;i<heroes.size();i++){
			heroes.get(i).visibleAdjacentCells();
		}
		
	}
	
	
}
	



