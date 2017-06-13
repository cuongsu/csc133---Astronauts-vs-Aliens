package com.mycompany.a3;

import java.util.ArrayList;

//collection class that will hold all the game objects, along with the iterator needed to get 
//the different objects inside the collection
public class GameObjectsCollection implements ICollection {
	private ArrayList theCollection;
	
	//collection of arraylist objects that will be in the game world
	public GameObjectsCollection() {
		theCollection = new ArrayList();
	}
	
	//adds an object into the collection
	public void add(Object newObject) {
		theCollection.add(newObject);
	}
	
	//returns the iterator
	public IIterator getIterator() {
		return new ArrayListIterator();
	}
	
	//removes an object from the collection
	public void removeObject(int index) {
		theCollection.remove(index);
	}
	
	//allows "getting" an object inside the collection
	public Object get(int index) {
		return theCollection.get(index);
	}
	
	//returns the size of the collection
	public int size() {
		return theCollection.size();
	}
	
//anon class needed to implement iterator design pattern
	
	private class ArrayListIterator implements IIterator {
		private int currIndex;
		private boolean removeOK = false;
			
		public ArrayListIterator() {
				currIndex = -1;
		}
			
		public boolean hasNext() {
			if (theCollection.size() <= 0) return false;
			if (currIndex == theCollection.size() - 1) return false;
				removeOK = true;
				return true;
		}
			
		public Object getNext() {
			currIndex++;
			return(get(currIndex));
		}
			
		public void remove() {
			if (removeOK = true) {
				theCollection.remove(currIndex);
				currIndex--;
			}
		}
	}
}
