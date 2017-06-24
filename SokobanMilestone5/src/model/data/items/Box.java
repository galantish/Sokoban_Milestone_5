package model.data.items;

/**
 * The Class Box - an item in the game. 
 */
public class Box extends CommonItems implements iMoveable
{
	private Boolean isBoxInTarget;

	public Box() 
	{
		super.setRepChar('@');
		this.isBoxInTarget = false;
	}
	
	public Box(Position position)
	{
		super.setRepChar('@');
		this.isBoxInTarget = false;
		super.setPosition(position);	
	}
		
	public Boolean getIsBoxInTarget() 
	{
		return isBoxInTarget;
	}

	public void setIsBoxInTarget(Boolean isBoxInTarget) 
	{
		this.isBoxInTarget = isBoxInTarget;
	}

	@Override
	public char getTypeOfObject() 
	{
		return '@';
	}
	
	@Override
	public void Move(Position newPos) 
	{
		this.setPosition(newPos);
		
	}
}
