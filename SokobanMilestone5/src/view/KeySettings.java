package view;

import javafx.scene.input.KeyCode;

public class KeySettings
{
	private KeyCode moveUp;
	private KeyCode moveDown;
	private KeyCode moveRight;
	private KeyCode moveLeft;
	
	public KeySettings() 
	{
		this.moveUp = KeyCode.UP;
		this.moveDown = KeyCode.DOWN;
		this.moveRight = KeyCode.RIGHT;
		this.moveLeft = KeyCode.LEFT;
	}

	public KeyCode getMoveUp() 
	{
		return moveUp;
	}

	public void setMoveUp(KeyCode moveUp) 
	{
		this.moveUp = moveUp;
	}

	public KeyCode getMoveDown() 
	{
		return moveDown;
	}

	public void setMoveDown(KeyCode moveDown) 
	{
		this.moveDown = moveDown;
	}

	public KeyCode getMoveRight() 
	{
		return moveRight;
	}

	public void setMoveRight(KeyCode moveRight) 
	{
		this.moveRight = moveRight;
	}

	public KeyCode getMoveLeft() 
	{
		return moveLeft;
	}

	public void setMoveLeft(KeyCode moveLeft) 
	{
		this.moveLeft = moveLeft;
	}
}
