package com.mycompany.a3;

import com.mycompany.a3.IIterator;

//interface that allows adding an object into a collection and returning the iterator
public interface ICollection {
	public void add(Object newObject);
	public IIterator getIterator();
}
