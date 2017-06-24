package model.data.items;

import java.util.HashMap;

/**
* The Class UnmovableFactory - The Class where we hold the unmovable items creator list. 
*/
public class UnmovableFactory
{
	private HashMap<Character, FactoryCreator> hashMap;

	/**
	* The interface FactoryCreator - An interface that create a unmovable item by his position.
	*/
	private interface FactoryCreator
	{
		public iUnmoveable Creator(Position position);
	}

	/**
	* The Class WallCreator - The Class that create a new wall and sets it's position.
	*/
	private class WallCreator implements FactoryCreator
	{
		@Override
		public iUnmoveable Creator(Position position)
		{
			return new Wall(position);
		}
	}

	/**
	* The Class FloorCreator - The Class that create a new floor and sets it's position.
	*/
	private class FloorCreator implements FactoryCreator
	{
		@Override
		public iUnmoveable Creator(Position position)
		{
			return new Floor(position);
		}
	}

	/**
	* The Class TargetCreator - The Class that create a new target and sets it's position.
	*/
	private class TargetCreator implements FactoryCreator
	{
		@Override
		public iUnmoveable Creator(Position position)
		{
			return new Target(position);
		}
	}

	/**
	* Initializes the create unmovable items list.
	*/
	public UnmovableFactory()
	{
		this.hashMap = new HashMap<Character, UnmovableFactory.FactoryCreator>();
		this.hashMap.put('#', new WallCreator());
		this.hashMap.put(' ', new FloorCreator());
		this.hashMap.put('o', new TargetCreator());
	}

	/**
	 * Gets char and position.
	 * @param c
	 * 			a representative char of an item
	 * @param position
	 * 			item's position
	 * @return the fitting create unmovable item by the fitting char (with a position).
	 */
	public iUnmoveable CreateUnmovable(char c, Position position)
	{
		FactoryCreator factory = hashMap.get(c);
		if (factory != null)
			return factory.Creator(position);
		return null;
	}
}