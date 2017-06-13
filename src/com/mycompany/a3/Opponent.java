package com.mycompany.a3;

import java.util.Random;
import java.util.Timer;

//An abstract class holding the attributes included in GameObjects
//class, as well as attributes specifically for opponents such as 
//direction and speed.
public abstract class Opponent extends GameObjects implements IMoveable{
	
	private Random rand = new Random();
	private int direction, speed;
	private double x, y;
	private double deltaX; 
	private double deltaY;
	
	public Opponent(int size, double x, double y, int color, int direction, int speed) {
		super(size, x, y, color);
		this.direction = direction;
		this.speed = speed;
	}

	public void move(int time) {
		x = getX();
		y = getY();
		
		//change the opponent's direction to be a new direction that is 
		//within -5 to +5 of its original direction.
		if(rand.nextInt(2) == 1) {
			if(rand.nextInt(2) == 1) {
				if(rand.nextInt(2) == 1) {
					if(rand.nextInt(2) == 1) {
						if(rand.nextInt(2) == 1) {
							if(rand.nextInt(2) == 1) {
								direction = direction + (rand.nextInt(11) - 5);
							}
						}
					}
				}
			}
		}
		
		//recalculate a new location for the opponent using
		//the new direction, given above, and speed.
		double dist = speed * (time/100);
		deltaX = Math.cos(90 - direction) * dist;
		deltaY = Math.sin(90- direction) * dist;
		double newX = Math.round((x + deltaX) * 10.0) / 10.0;
		double newY = Math.round((y + deltaY) * 10.0) / 10.0;
		
		//If an opponent hits the side of the world, it will change
		//direction and recalculate its location in order
		//to avoid being out of bounds.
		while(newX < 0 || newX > GameWorld.getWidth() || newY < 0 || newY > GameWorld.getHeight()) {
			direction = rand.nextInt(360);
			deltaX = Math.cos(90 - direction);
			deltaY = Math.sin(90 - direction);
			newX = Math.round((x + deltaX) * 10.0) / 10.0;
			newY = Math.round((y + deltaY) * 10.0) / 10.0;
		}
		
		super.setX(newX);
		super.setY(newY);
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int newSpeed) {
		speed = newSpeed;
	}
	
	//Allows printing of all attributes of opponents.
	public String toString() {
		String generalInfo = super.toString();
		String output = generalInfo + " --- speed = " + speed + " --- dir = " + direction;
		return output;
	}
}
