package com.mycompany.a3;

import com.mycompany.a3.GameObjects;
import com.mycompany.a3.IGuidable;

//An abstract class holding the general attributes for 
//rescuers (i.e. spaceships)
public abstract class Rescuer extends GameObjects implements IGuidable { 
		
	public Rescuer(int size, double x, double y, int color) {
		super(size, x, y, color);
		
	}
	
	//Allows the rescuer to move left by 10, with a minimum of 0.0.
	public void moveLeft() {
		if(super.getX() > 0.0) {
			if(super.getX() < 50.0) {
				setX(0.0);
			} else {
				setX(Math.round((super.getX() - 50.0)*10.0) / 10.0);
			}
		} else {
			System.out.println("ERROR: The rescuer cannot move left any further (spaceship is at location x=0.0).");
		}
	}

	//Allows the rescuer to move right by 10, with a maximum of 1024.0.
	public void moveRight() {
		if(super.getX() < GameWorld.getWidth()) {
			if(super.getX() > GameWorld.getWidth()-50) {
				setX(GameWorld.getWidth());
			} else {
				setX(Math.round((super.getX() + 50.0)*10.0) / 10.0);
			}
		} else {
			System.out.println("ERROR: The rescuer cannot move right any further.");
		}		
	}

	//Allows the rescuer to move up by 10, with a maximum of 768.0.
	public void moveUp() {
		if(super.getY() < GameWorld.getHeight()) {
			if(super.getY() > GameWorld.getHeight() - 50) {
				setY(GameWorld.getHeight());
			} else {
				setY(Math.round((super.getY() + 50.0)*10.0) / 10.0);
			}
		} else {
			System.out.println("ERROR: The rescuer cannot move up any further.");
		}			
	}

	//Allows the rescuer to move down by 10, with a minimum of 0.0.
	public void moveDown() {
		if(super.getY() > 0.0) {
			if(super.getY() < 50.0) {
				setY(0.0);
			} else {
				setY(Math.round((super.getY() - 50.0)*10.0) / 10.0);
			}
		} else {
			System.out.println("ERROR: The rescuer cannot move down any further (spaceship is at location y=0.0).");
		}	
	}
	
	//sets the location of the rescuer to the same location of another object
	public void jumpToLocation(double locX, double locY) {
		setX(locX);
		setY(locY);
	}
}
