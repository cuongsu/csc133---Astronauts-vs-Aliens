package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;
import com.mycompany.a3.Rescuer;

//A concrete class for spaceships
public class Spaceship extends Rescuer implements IDrawable{

	public Spaceship(int size, double x, double y, int color) {
		super(size, x, y, color);
	}
	
	//allows printing of all attributes of spaceship.
	public String toString() {
		String generalInfo = super.toString();
		String output = "Spaceship: " + generalInfo;
		return output;
	}

	//draw the spaceship as a black square/rectangle
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(getColor());
		g.drawRect((int)getX() - (getSize() / 2) + (int)pCmpRelPrnt.getX(), 
				(int)getY() - (getSize()/2) + (int)pCmpRelPrnt.getY(), getSize(), getSize());
	}
}
