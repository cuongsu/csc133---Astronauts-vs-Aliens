package com.mycompany.a3;

//interface that shows the functions collidable objects have
public interface ICollider {
	public boolean collidesWith(ICollider otherObject);
	public void handleCollision(ICollider otherObject, GameObjectsCollection gwList, GameWorld gw, Game game);
}
