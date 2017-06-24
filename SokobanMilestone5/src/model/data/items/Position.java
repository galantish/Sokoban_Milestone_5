package model.data.items;

import java.io.Serializable;

/**
 * The Class Position - an item in the game. 
 */
public class Position implements Serializable
{
	private int x;
	private int y;
	
	public Position() 
	{
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * Gets 2 points and initializes the local x and y points.
	 * @param x
	 * 			point x
	 * @param y
	 * 			point y
	 */
	public Position(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}

	public Position(Position pos) 
	{
		this.x = pos.getX();
		this.y = pos.getY();
	}

	public int getX() 
	{
		return x;
	}

	public void setX(int x) 
	{
		this.x = x;
	}

	public int getY() 
	{
		return y;
	}

	public void setY(int y) 
	{
		this.y = y;
	}
	
	@Override
	public String toString()
	{
		return ("(" + x + "," + y + ")");
	}

	public Position getPosition()
	{
		return this;
	}

	/**
	 * IsEqualPosition - checking if 2 positions are equals.
	 * @param position
	 * 			a position to compare
	 * @return true/false if the local position is equal to a position.
	 */
	public Boolean isEqualPosition(Position position)
	{
		if((this.getX() == position.getX()) && (this.getY() == position.getY()))
			return true;
		return false;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		Position pos = (Position) obj;
		return isEqualPosition(pos);
	}
	
	@Override
	public int hashCode() 
	{
		return toString().hashCode();
	}
}
