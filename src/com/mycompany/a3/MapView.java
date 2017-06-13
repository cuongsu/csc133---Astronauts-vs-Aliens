package com.mycompany.a3;

import java.util.Observer;
import java.util.Observable;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.plaf.Border;

//One of the two Views
//located in the center container in the primary BorderLayout
//Helps print updated values for score and astronaut/alien data in the console
public class MapView extends Container implements Observer {

	private GameWorld updateMap;
	private GameWorld mapGw;
	private GameObjectsCollection mapObjectList;
	
	public MapView (GameWorld gameWorld) {
		getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK));
		mapGw = gameWorld;
		mapObjectList = gameWorld.getObjectList();
	}
	
	//paint all the objects
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Point pCmpRelPrnt = new Point(getX(), getY());
		
		for (int i = 0; i <= mapObjectList.size() - 1; i++) {
			if (mapObjectList.get(i) instanceof Astronaut) {
				((Astronaut) mapObjectList.get(i)).draw(g, pCmpRelPrnt);
			}
			else if (mapObjectList.get(i) instanceof Alien) {
				((Alien) mapObjectList.get(i)).draw(g, pCmpRelPrnt);
			}
			else if (mapObjectList.get(i) instanceof Spaceship) {
				((Spaceship) mapObjectList.get(i)).draw(g, pCmpRelPrnt);
			}
		}
	}
	
	//check to see if the cursor has pressed/selected within an astronaut's bounds
	public void pointerPressed(int x, int y) {
		if(Game.isPaused()) {
			x = x - getParent().getAbsoluteX();
			y = y - getParent().getAbsoluteY();
			Point pPtrRelPrnt = new Point(x, y);
			Point pCmpRelPrnt = new Point(getX(), getY());
			IIterator it = mapObjectList.getIterator();
			while(it.hasNext()){
				Object obj = it.getNext();
				if(obj instanceof ISelectable){
					ISelectable tempAstro= (ISelectable) obj;
					if(tempAstro.contains(pPtrRelPrnt, pCmpRelPrnt)){
						tempAstro.setSelected(true);
					}else{
						tempAstro.setSelected(false);
					}
				}
			}
			repaint();
		}
	}
	
	//Print updated information/data to the console
	public void update (Observable o, Object arg) {
		repaint();
		updateMap = (GameWorld) o;
		System.out.println(updateMap.printView());
	}
}
