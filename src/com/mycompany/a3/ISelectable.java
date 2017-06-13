package com.mycompany.a3;

import com.codename1.charts.models.Point;

//interface that shows the functions for a selectable object
public interface ISelectable {
	public void setSelected(boolean bool);
	public boolean isSelected();
	public boolean contains(Point pPtrRelprnt, Point pCmpRelPrnt);
}
