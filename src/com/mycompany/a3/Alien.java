package com.mycompany.a3;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.mycompany.a3.Opponent;

//A concrete class for Aliens
public class Alien extends Opponent implements IDrawable, ICollider {
	int limitCollisionCntr;
	private Set<Object> colSet;
	private Random rand = new Random();
	private int birth;
	
	public Alien(int size, double x, double y, int color, int direction, int speed) {
		super(size, x, y, color, direction, speed);
		colSet = new HashSet();
		limitCollisionCntr = 0;
		this.birth = 16;
	}
	
	//allows printing of all attributes of aliens.
	public String toString() {
		String generalInfo = super.toString();
		String output = "Alien    : " + generalInfo;
		return output;
	}

	//draw aliens as unfilled circles
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(getColor());		
		g.drawArc((int)getX() - (getSize() /2) + (int)pCmpRelPrnt.getX(), 
				(int)getY() - (getSize() / 2) + (int)pCmpRelPrnt.getY(), getSize(), getSize(), 0, 360);
	}

	public void incrementCounter() {
		limitCollisionCntr++;
	}

	public int getCounter() {
		return limitCollisionCntr;
	}

	public void clearColSet() {
		colSet.clear();		
	}

	public Set<Object> getColSet() {
		return colSet;
	}

	public void clearCounter() {
		limitCollisionCntr = 0;		
	}

	public void addToColSet(Object obj) {
		colSet.add(obj);
	}
	
	public boolean collidesWith(ICollider obj) {
		boolean collide = false;

		Alien tempObj = (Alien)obj;

		int halfSize = getSize() / 2;
		double xDiff = Math.abs(this.getX() - tempObj.getX());
		double yDiff = Math.abs(this.getY() - tempObj.getY());
		if((xDiff < halfSize) && (yDiff < halfSize)) {
			collide = true;
		}
		return collide;
	}
	
    public int getBirthed() {
        return birth;
    } 
    
    public int incBirth(){
    	birth++;
    	return birth;
    }

    //create new alien
	public void handleCollision(ICollider otherObject, GameObjectsCollection gwList, GameWorld gw, Game game) {		
        if(otherObject instanceof Alien){
            Alien tempAlien = (Alien)otherObject;
            if( tempAlien.getBirthed()>10 && this.getBirthed()>10){
        		if(gw.getAlienCount() < 30) {
        			boolean alienCheck = false;
        			while(alienCheck == false) {
        				double randomX = rand.nextInt(100) - 50;
        				double randomY = rand.nextInt(100) - 50;
        				double alienX = getX() + randomX;
        				double alienY = getY() + randomY;
        				
        				while(alienX < 0 || alienX > GameWorld.getWidth() || alienY < 0 || alienY > GameWorld.getHeight()) {
        					randomX = rand.nextInt(100) - 50;
        					randomY = rand.nextInt(100) - 50;
        					alienX = getX() + randomX;
        					alienY = getY() + randomY;
        				}
        				
        				gw.add(new Alien(rand.nextInt(31) + 20, Math.round(alienX*10.0) / 10.0, 
        											Math.round(alienY*10.0) / 10.0,	ColorUtil.rgb(0, 0, 255), 
        											rand.nextInt(360), 5));
        				System.out.println("A new alien has appeared!");
        				gw.incrementAlienCount();	
        				alienCheck = true;
        				game.playAlienSound();
        				//set birth at -1 to not allow creating of new aliens by this alien
        				this.birth = -1;
        			}
        		}
			}
		}
	}
}