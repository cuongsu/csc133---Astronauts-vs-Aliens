package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Rectangle;

//This class defines all attributes and methods 
//that all game objects (opponents, rescuers) 
//will come with.
//Attributes included:
//	Size, location (x,y), color
//Also includes a method to allow printing of these attributes.
public abstract class GameObjects {
	
	private int size, color;
	private double x, y;
	private Rectangle bounds;
	
	public GameObjects(int size, double x, double y, int color) {
		this.size = size;
		this.x = x;
		this.y = y;
		this.color = color;;
	}
	
	public int getSize() {
		return size;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getColor() {
		return color;
	}
	
	public void setSize(int newSize) {
		size = newSize;
	}
	
	public void setX(double newX) {
		x = Math.abs(newX);
	}
		
	public void setY(double newY) {
		y = Math.abs(newY);
	}
	
	public void setColor(int r, int g, int b) {
		color = ColorUtil.rgb(r, g, b);
	}
	
    public void setBounds(Rectangle rect) {
        this.bounds = rect;
    } 
    
    public Rectangle getBounds() {
        return bounds;
    }
	
	public String toString() {
		String output = "location = (" + x + "," + y + ") --- color = [" + ColorUtil.red(color) + ","
				        + ColorUtil.green(color) + "," + ColorUtil.blue(color) + "] --- " +
				        "size = " + size;
		return output;
	}
}
