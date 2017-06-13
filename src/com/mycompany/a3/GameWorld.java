package com.mycompany.a3;

import java.util.Random;
import com.codename1.charts.util.ColorUtil;
import java.util.Observable;
import com.mycompany.a3.Opponent;
import com.mycompany.a3.IIterator;
import com.mycompany.a3.GameObjectsCollection;

public class GameWorld extends Observable {
	private final int constant = 1;
	private static int width;
	private static int height;
	private Random rand = new Random();
	private int score = 0;
	private int astroRescued = 0;
	private int astroCount = 4;
	private int aliensCaught = 0;
	private int alienCount = 3;
	
	private GameObjectsCollection objectList;
	
	public GameWorld() {
		objectList = new GameObjectsCollection();
	}
		
	//Adds in one spaceship, two astronauts, and 3 aliens into the game objects collection
	//The game objects will have the following attributes:
	//Spaceship: 
	//	size 100, random location within game world size
	//Astronauts/Aliens: 
	//	random size between 20-50, random location within 1024x768 game world size,
	//	random direction within 360 degrees, speed initially set at 5
	//	NOTE: astronauts will also have an HP value, cats will have a speed of 5*constant
	public void initLayout(){
		add(new Spaceship(100, Math.round(rand.nextInt(height+1)*rand.nextDouble()*10.0) / 10.0, 
									Math.round(rand.nextInt(width+1)*rand.nextDouble()*10.0) / 10.0, 
									ColorUtil.rgb(0, 0, 0)));
		for(int i = 0; i < 4; i++) {
			add(new Astronaut(rand.nextInt(31) + 20, 
										Math.round(rand.nextInt(height+1)*rand.nextDouble()*10.0) / 10.0, 
										Math.round(rand.nextInt(width+1)*rand.nextDouble()*10.0) / 10.0,
										ColorUtil.rgb(255, 0, 0), rand.nextInt(360), 5, 5));
		}
		for(int j = 0; j < 3; j++) {
			add(new Alien(rand.nextInt(31) + 20, 
									Math.round(rand.nextInt(height+1)*rand.nextDouble()*10.0) / 10.0, 
									Math.round(rand.nextInt(width+1)*rand.nextDouble()*10.0) / 10.0,
									ColorUtil.rgb(0, 0, 255), rand.nextInt(360), 5*constant));
		}
	}
	
	//method used by mapView to print the updated collection of objects
	public String printView() {
		StringBuilder out = new StringBuilder();
		IIterator itr = objectList.getIterator();
		out.append("---------------------------------------------------------------------------");
		out.append("\n");
		while (itr.hasNext()) {
			out.append(itr.getNext());
			out.append("\n");
		}
		out.append("---------------------------------------------------------------------------");
		return out.toString();
	}
	
	//Allows addition of new GameObjects into the collection
	public void add(GameObjects newObject) {
		objectList.add(newObject);
	}
	
	//allows retrieval of the whole object list
	public GameObjectsCollection getObjectList() {
		return objectList;
	}

	//Allows retrieval of objects inside the array list
	public Object getObject(int index) {
		return objectList.get(index);
	}
	
	//Allows removal of objects in the array list
	public void removeObject (int index) {
		objectList.removeObject(index);
	}
	
	//Send dimensions of width and height of the center container to the game world
	public void setWidth(int width) {
		this.width = width;
	}
	
	public static double getWidth() {
		return width;
	}	
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public static double getHeight() {
		return height;
	}
	
	//Allows the labels to be updated in ScoreView
	public int getScore() {
		return score;
	}
	
	//returns the amount of astronauts
	public int getAstroCount() {
		return astroCount;
	}
	
	//returns the amount of aliens 
	public int getAlienCount() {
		return alienCount;
	}
	
	public void incrementAlienCount() {
		alienCount++;
	}
	
	//returns the amount of astronauts that have been rescued
	public int getAstroRescued() {
		return astroRescued;
	}
	
	//returns the amount of aliens that have been caught
	public int getAliensCaught() {
		return aliensCaught;
	}

	//Checks to see if an opponent is within the spaceship's range, using its size.
	//If there is an opponent on the spaceship's edge, it WILL be regarded as caught.
	public boolean rangeCheck (Object spaceship, Object opponent) {
		if(((Math.abs(((Spaceship)spaceship).getX() - ((Opponent)opponent).getX())) <= ((Spaceship)spaceship).getSize() / 2) &&
				((Math.abs(((Spaceship)spaceship).getY() - ((Opponent)opponent).getY())) <= (((Spaceship)spaceship).getSize() / 2))) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	* 			--User inputs 'e' or 'c'--
	* 	
	* Changes the size of the spaceship to be 50 bigger
	* or 50 smaller. Max size: width, Min size: 50.
	* 
	*/	
	
	public void expand() {	
		if(((Spaceship)objectList.get(0)).getSize() < width) {
			((Spaceship)objectList.get(0)).setSize(((Spaceship)objectList.get(0)).getSize() + 50);
			setChanged();
			notifyObservers();
		} else {
			System.out.println("ERROR: The spaceship has reached its maximum size.");
		}
	}
	
	public void contract() {
		if(((Spaceship)objectList.get(0)).getSize() > 50) {
			((Spaceship)objectList.get(0)).setSize(((Spaceship)objectList.get(0)).getSize() - 50);
			setChanged();
			notifyObservers();
		} else {
			System.out.println("ERROR: The spaceship has reached its minimum size of 50.");
		}
	}
		
	/**
	* 			--User inputs 's'--
	* 	
	* Opens the spaceship door and updates the score
	* depending on what opponent, if any, was let in. Aliens give 
	* -10 points to the score and astronauts give 
	* (10 - HP lost) to the score. If an object is let in,
	* it will be removed from the game world.
	* 
	*/	
		
	public void openDoor() {
		int count = 0;
		for(int i = objectList.size() - 1; i >= 1; i--) {
			if(rangeCheck(objectList.get(0), objectList.get(i)) == true) {
				if(objectList.get(i) instanceof Astronaut) {
					System.out.println("An astronaut has been rescued! Nice job!");
					Astronaut tempAstro = (Astronaut)getObject(i);
					int tempVal = tempAstro.getHp() - 5;
					score = score + 10 + tempVal;
					astroRescued++;
					astroCount--;
				}
				
				if(objectList.get(i) instanceof Alien) {
					System.out.println("An alien has sneaked onto the ship!");
					score = score - 10;
					aliensCaught++;
					alienCount--;
				}
				count++;
				objectList.removeObject(i);
			}
		}
		
		if(count > 0) {
			setChanged();
			notifyObservers();
		}

		if(count == 0) {
			System.out.println("There's nothing nearby to capture!");
		}
		
	}	
	
	/**
	* 			--User inputs 'u' or 'd'--
	* 	
	* Spaceship will move up or down by 10.
	* If spaceship has hit the maximum height (768.0) or
	* the minimum height (0.0), an error message will display.
	* 
	*/
	
	public void moveShipUp() {
		((Spaceship)getObject(0)).moveUp();
		setChanged();
		notifyObservers();
	}
	
	public void moveShipDown() {
		((Spaceship)getObject(0)).moveDown();
		setChanged();
		notifyObservers();
	}
	
	/**
	* 			--User inputs 'r' or 'l'--
	* 	
	* Spaceship will move right or left by 10.
	* If spaceship has hit the maximum width (1024.0) or
	* the minimum width (0.0), an error message will display.
	* 
	*/	
	
	public void moveShipLeft() {
		((Spaceship)getObject(0)).moveLeft();
		setChanged();
		notifyObservers();
	}

	public void moveShipRight() {
		((Spaceship)getObject(0)).moveRight();
		setChanged();
		notifyObservers();
	}
	
	/**
	* 			--User inputs 'a' or 'o'--
	* 	
	* Teleport the spaceship to a location of a randomly selected
	* alien or astronaut. If there are no aliens, print an error message.
	* If there are no astronauts, print an error message.
	* 
	*/
	
	public void jumpToAstro() {
		if(astroCount == 0) {
			System.out.println("There are no astronauts to teleport to!");
		} else {
			boolean shipToAstronaut = false;
			while(shipToAstronaut == false) {
				int jumpAstro = rand.nextInt(objectList.size() - 1) + 1;
				if (getObject(jumpAstro) instanceof Astronaut) {
					double tempAstroX = (((GameObjects) getObject(jumpAstro)).getX());
					double tempAstroY = (((GameObjects) getObject(jumpAstro)).getY());
					((Rescuer)getObject(0)).jumpToLocation(tempAstroX, tempAstroY);
					System.out.println("The spaceship has been teleported to a random astronaut!");
					shipToAstronaut = true;
					setChanged();
					notifyObservers();
					}
				}
		}
	}
	
	public void jumpToAlien() {
		if(alienCount == 0) {
			System.out.println("ERROR: There are no aliens to teleport to!");
		} else {
			boolean shipToAlien = false;
			while(shipToAlien == false) {
				int jumpAlien = rand.nextInt(objectList.size() - 1) + 1;
				if (getObject(jumpAlien) instanceof Alien) {
					double tempAlienX = (((GameObjects) getObject(jumpAlien)).getX());
					double tempAlienY = (((GameObjects) getObject(jumpAlien)).getY());
					((Rescuer)getObject(0)).jumpToLocation(tempAlienX, tempAlienY);
					System.out.println("The spaceship has been teleported to a random alien!");
					shipToAlien = true;
					setChanged();
					notifyObservers();
				}
			}
		}
	}
	
	/**
	* 			--User inputs 'w'--
	* 	
	* PRETEND that a collision has occurred between 2 aliens.
	* A new alien will be generated within -50 to +50 units of 
	* a random alien that is already in the game world.
	* 
	*/
	
	/*
	public void createAlien() {
		if(alienCount < 2) {
			System.out.println("ERROR: There are not enough aliens to produce a new alien.");
		} else {
			
			//make a boolean to check whether the object grabbed in the array list 
			//is indeed an alien.
			boolean alienCheck = false;
			
			//repeat the while loop until the boolean is set to TRUE
			while(alienCheck == false) {
				//Choose a random object to have a NEW alien spawned near
				int newAlien = rand.nextInt(objectList.size() - 1) + 1;
				
				//check if the object selected is actually an alien
				//if not, then skip the code and go back to the while loop
				if (getObject(newAlien) instanceof Alien) {					
					//Give the new alien a location that is within -50 to +50 of the random
					//alien selected above.
					double randomX = rand.nextInt(100) - 50;
					double randomY = rand.nextInt(100) - 50;
					double alienX = ((Alien)getObject(newAlien)).getX() + randomX;
					double alienY = ((Alien)getObject(newAlien)).getY() + randomY;
			
					//Check to see if the new alien's location is out of bounds. 
					//If so, keep generating new locations until a location,
					//that is in bounds, is generated.
					while(alienX < 0 || alienX > width || alienY < 0 || alienY > height) {
						randomX = rand.nextInt(100) - 50;
						randomY = rand.nextInt(100) - 50;
						alienX = ((Alien)getObject(newAlien)).getX() + randomX;
						alienY = ((Alien)getObject(newAlien)).getY() + randomY;
					}
					
					//Add the new alien to the array list using the new location made above.
					objectList.add(new Alien(rand.nextInt(31) + 20, Math.round(alienX*10.0) / 10.0, 
												Math.round(alienY*10.0) / 10.0,	ColorUtil.rgb(0, 0, 255), 
												rand.nextInt(360), 5*constant));
					System.out.println("A new alien has appeared!");
					alienCount++;	
					alienCheck = true;
					setChanged();
					notifyObservers();
				}
			}
		}
	}
	*/
	
	/**
	 * 			--User inputs 'f'--
	 * 	
	 * PRETEND that a fight has occurred between an
	 * alien and an astronaut. The astronaut's will have 
	 * their HP decremented by 1, their speed set to equal 
	 * their HP, and their color will fade. An astronaut cannot
	 * go below 0 HP or speed and will not be chosen again if
	 * their speed is 0. 
	 * 
	 */
	
	/*public void fight() {
		if(alienCount == 0) {
			System.out.println("ERROR: There are no more aliens!");
		} else {
			//boolean to check if the random game object chosen is an astronaut
			//as well as above 0 speed
			boolean astroCheck = false;
			
			//repeat the while loop until the boolean is set to TRUE
			while(astroCheck == false) {
				
				//choose a random game object to be attacked
				int attackedAstro = rand.nextInt(objectList.size() - 1) + 1;
				
				//check if the random object is an astronaut.
				if(getObject(attackedAstro) instanceof Astronaut) {																																
					//Check if the astronaut's speed is greater than 0 otherwise do nothing
					if(((Opponent)getObject(attackedAstro)).getSpeed() > 0) {
						
						//decrement the astronaut's HP by 1
						((Astronaut)getObject(attackedAstro)).decreaseHP();
						
						//set the astronaut's speed equal to their decremented HP
						int newSpeed = ((Astronaut)getObject(attackedAstro)).getHp();
						((Opponent)getObject(attackedAstro)).setSpeed(newSpeed); 
						
						//decrement the red aspect of an astronaut by 50
						int tempColor = ((Opponent)getObject(attackedAstro)).getColor(); 
						int newRed = ColorUtil.red(tempColor) - 50; 
						((Opponent)getObject(attackedAstro)).setColor(newRed, 0, 0); 							 
						
						System.out.println("An astronaut has been attacked!");
						astroCheck = true;
						setChanged();
						notifyObservers();
					}
				}
			}
		}
	}*/
	
	/**
	* 			--User inputs 't'--
	* 	
	* Ticks the clock, so that all moving objects will
	* update their locations according to their current
	* direction and speed.
	* 
	*/	
	
	public void tick(int time) {
		int idx = 0;
	    IIterator itr = objectList.getIterator();
	    GameObjects o;
	    while(itr.hasNext()) {
	    	if(getObject(idx) instanceof Opponent) {
				((Opponent)objectList.get(idx)).move(time);  
	    	}
	    	o = (GameObjects)itr.getNext();
	    	idx++;
	    }
	    setChanged();
	    notifyObservers();
	}
}
