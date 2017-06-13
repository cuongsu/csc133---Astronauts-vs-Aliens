package com.mycompany.a3;

import java.util.Set;
import java.util.HashSet;
import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.geom.Rectangle2D;

//A concrete class for astronauts
public class Astronaut extends Opponent implements IDrawable, ICollider, ISelectable {
	private int hp;
	private boolean selected;
	int limitCollisionCntr;
	private Set<Object> colSet;
	//private Vector<Object> colVector;
	
	public Astronaut(int size, double x, double y, int color, int direction, int speed, int hp) {
		super(size, x, y, color, direction, speed);
		this.hp = hp;
		colSet = new HashSet();
		limitCollisionCntr = 0;
		selected = false;
	}
	
	public int getHp() {
		return hp;
	}
	
	public void decreaseHP() {
		hp--;
	}
	
	public void heal() {
		setSpeed(5);
		setColor(255, 0, 0);
		this.hp = 5;
	}
	
	//allows printing of all attributes of astronauts.
	public String toString() {
		String generalInfo = super.toString();
		String output = "Astronaut: " + generalInfo + " --- HP = " + hp;
		return output;
	}

	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		float px = pPtrRelPrnt.getX();
		float py = pPtrRelPrnt.getY();
		float xLoc = pCmpRelPrnt.getX() + (float)getX();
		float yLoc = pCmpRelPrnt.getY() + (float)getY();
		if ( (px >= xLoc) && (px <= xLoc + getSize())
				&& (py >= yLoc) && (py <= yLoc + getSize()) )
				return true; else return false;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean bool) {
		this.selected = bool;
	}

	//draw unselected astronauts as unfilled isosceles triangles
	//draw selected astronauts as filled isosceles triangles
	public void draw(Graphics g, Point pCmpRelPrnt) {		
		g.setColor(getColor());
		if(isSelected()) {
			g.fillPolygon(new int [] { ((int)getX()) + (int)pCmpRelPrnt.getX(), 
					(int)getX() - (getSize()/2) + (int)pCmpRelPrnt.getX(), 
					(int)getX() + (getSize()/2) + (int)pCmpRelPrnt.getX()},
					
					new int [] { (int)getY() + (getSize()/2) + (int)pCmpRelPrnt.getY(), 
							(int)getY() - (getSize()) + (int)pCmpRelPrnt.getY(), 
							(int)getY() - (getSize()) + (int)pCmpRelPrnt.getY()}, 3);
		} else {
			g.drawPolygon(new int [] { ((int)getX()) + (int)pCmpRelPrnt.getX(), 
					(int)getX() - (getSize()/2) + (int)pCmpRelPrnt.getX(), 
					(int)getX() + (getSize()/2) + (int)pCmpRelPrnt.getX()},
					
					new int [] { (int)getY() + (getSize()/2) + (int)pCmpRelPrnt.getY(), 
							(int)getY() - (getSize()) + (int)pCmpRelPrnt.getY(), 
							(int)getY() - (getSize()) + (int)pCmpRelPrnt.getY()}, 3);
		}
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

	//astronaut-alien fight
	public void handleCollision(ICollider otherObject, GameObjectsCollection gwList, GameWorld gw, Game game) {
		if(super.getSpeed() > 0) {
			decreaseHP();
			int tempColor = getColor();
			int newHP = getHp();
			int newRed = ColorUtil.red(tempColor) - 50;
			setColor(newRed, 0, 0);
			setSpeed(newHP);
			game.playAttackSound();
			System.out.println("An astronaut has been attacked!");
		}
	}
	
	public int getCounter() {
		return limitCollisionCntr;
	}
	
	public void incrementCounter() {
		limitCollisionCntr++;
	}
	
	public void clearCounter() {
		limitCollisionCntr = 0;		
	}

	public Set<Object> getColSet() {
		return colSet;
	}
	
	public void addToColSet(Object obj) {
		colSet.add(obj);
	}
	
	public void clearColSet() {
		colSet.clear();
	}



}
