package model.data.items;

import java.util.HashMap;

/**
* The Class MovableFactory - The Class where we hold the movable items creator list. 
*/
public class MovableFactory
{
	private HashMap<Character, FactoryCreator> hashMap;

	/**
	* The interface FactoryCreator - An interface that create a movable item by his position.
	*/
	private interface FactoryCreator
	{
		public iMoveable Creator(Position position);
	}

	/**
	* The Class PlayerCreator - The Class that create a new player and sets it's position.
	*/
	private class PlayerCreator implements FactoryCreator
	{
		@Override
		public iMoveable Creator(Position position)
		{
			return new Player(position);
		}
	}

	/**
	* The Class BoxCreator - The Class that create a new box and sets it's position.
	*/
	private class BoxCreator implements FactoryCreator
	{
		@Override
		public iMoveable Creator(Position position)
		{
			return new Box(position);
		}
	}
	
	/**
	* Initializes the create movable items list.
	*/
	public MovableFactory()
	{
		this.hashMap = new HashMap<Character, FactoryCreator>();
		this.hashMap.put('A', new PlayerCreator());
		this.hashMap.put('@', new BoxCreator());
	}

	/**
	 * Gets char and position.
	 * @param c
	 * 			a representative char of an item
	 * @param position
	 * 			item's position
	 * @return the fitting create movable item by the fitting char (with a position).
	 */
	public iMoveable CreateMovable(char c, Position position)
	{
		FactoryCreator factory = hashMap.get(c);
		if (factory != null)
			return factory.Creator(position);
		return null;
	}
}