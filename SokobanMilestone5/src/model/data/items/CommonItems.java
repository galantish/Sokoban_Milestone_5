package model.data.items;

import java.io.Serializable;

/**
* The Class CommonItems - The abstract class that implements all the functions for an item of a level game.
*/
public abstract class CommonItems implements iGeneralItem, Serializable
{
	private Position position;
	private char repChar;

	public CommonItems() 
	{
		this.position = new Position();
		this.repChar = ' ';
	}
	
	public CommonItems(Position position, char repChar)
	{
		this.position = position;
		this.repChar = repChar;
	}
	
	public Position getPosition()
	{
		return position;
	}

	public void setPosition(Position position)
	{
		this.position = position;
	}

	public char getRepChar() 
	{
		return repChar;
	}
	
	public void setRepChar(char repChar) 
	{
		this.repChar = repChar;
	}
	
	@Override
	public String toString()
	{
		String s = String.valueOf(this.repChar);
		return s;
	}
}
