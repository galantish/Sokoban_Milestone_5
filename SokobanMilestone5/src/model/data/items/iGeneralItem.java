package model.data.items;

/**
* The Interface iGeneralItem - Interface that holds all the function that an item should implement.
*/
public interface iGeneralItem 
{
	public char getTypeOfObject();
	public Position getPosition();
	public void setPosition(Position newPos);
}

