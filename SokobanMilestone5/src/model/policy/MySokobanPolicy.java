package model.policy;

import java.io.Serializable;

import commons.Level;
import model.data.items.Box;
import model.data.items.Floor;
import model.data.items.Player;
import model.data.items.Position;
import model.data.items.Target;
import model.data.items.Wall;
import model.data.items.iGeneralItem;
import model.data.items.iMoveable;

/**
 * The Class MySokobanPolicy - defines the policy of SOKOBAN game.
 */
public class MySokobanPolicy implements Serializable, iSokobanPolicy
{
	private boolean isPlayerCanWalkOnWall;
	private boolean isPlayerCanPullBox;
	private boolean isBoxCanWalkOnWall;
	private boolean isBoxCanPushBox;
		
	public MySokobanPolicy()
	{
		this.isBoxCanPushBox = false;
		this.isBoxCanWalkOnWall = false;
		this.isPlayerCanPullBox = false;
		this.isPlayerCanWalkOnWall = false;
	}

	/*
	 * MoveInDirection - move some movable item to a new position if it's allowed by the policy.
	 */
	private void moveInDirection(Level level, iMoveable item, Position newPos, Position nextBoxPos) throws Exception
	{			
		//Saving the original item's position
		Position backupItemPos = new Position(item.getPosition());
		//Discover who is the item that is located in the new position.
		iGeneralItem itemInNewPos = level.getItemInPosition(newPos);

		//Checking if the item we found is a type of floor
		if(itemInNewPos instanceof Floor)
		{
			//Sets the item we found in new position.
			level.getItemsOnBoard()[newPos.getX()][newPos.getY()] = item;
			if(item instanceof Player)
				((Player) item).setPosition(newPos);
			else
				((Box) item).setPosition(newPos);
		}
		
		//Checking if the item we found is a type of box.
		else if(itemInNewPos instanceof Box)
		{					
			//Discover who is the item that is located next to box.
			iGeneralItem itemForBox = level.getItemInPosition(nextBoxPos);
		
			//Sets new position for the item next to box,
			((Box) itemInNewPos).setPosition(nextBoxPos);
			level.getItemsOnBoard()[nextBoxPos.getX()][nextBoxPos.getY()] = (Box) itemInNewPos;
			
			//Checking if the next items of box is type of target.
			if(itemForBox instanceof Target)
				((Box) itemInNewPos).setIsBoxInTarget(true);
			else
				((Box) itemInNewPos).setIsBoxInTarget(false);
		}
		
		//Sets new position for a player and modify the 2D array.
		level.getItemsOnBoard()[newPos.getX()][newPos.getY()] = (Player) item;
		((Player) item).Move(newPos);
		level.getPlayers().get(0).setPosition(newPos);
		
		/*if(item instanceof Player)
		{
			level.getItemsOnBoard()[newPos.getX()][newPos.getY()] = (Player) item;
			((Player) item).Move(newPos);
			level.getPlayers().get(0).setPosition(newPos);
		}
		
		else
		{
			level.getItemsOnBoard()[newPos.getX()][newPos.getY()] = (Box) item;
			((Box) item).Move(newPos);
		}*/
	
		level.getItemsOnBoard()[backupItemPos.getX()][backupItemPos.getY()] = null;
		level.setPlayersSteps(level.getPlayersSteps() + 1);

	}
	
	/*
	 * Move - return true/false if a movable item can move to the new position by checking the directions.
	 */
	@Override
	public boolean move(Level level, String moveType) 
	{
		Position playerPos = level.getPlayers().get(0).getPosition();
		try
		{			
			switch (moveType.toLowerCase()) 	
			{
			case "up":
				if (isMovePossible(level, level.getPlayers().get(0), new Position(playerPos.getX()-1, playerPos.getY()), new Position (playerPos.getX()-2, playerPos.getY())))
					moveInDirection(level, level.getPlayers().get(0), new Position(playerPos.getX()-1, playerPos.getY()), new Position (playerPos.getX()-2, playerPos.getY()));
				else
					return false;
			break;
			case "down":
				if (isMovePossible(level, level.getPlayers().get(0), new Position(playerPos.getX()+1, playerPos.getY()), new Position (playerPos.getX()+2, playerPos.getY())))
					moveInDirection(level, level.getPlayers().get(0), new Position(playerPos.getX()+1, playerPos.getY()), new Position (playerPos.getX()+2, playerPos.getY()));
				else
					return false;
			break;
			case "left":
				if (isMovePossible(level, level.getPlayers().get(0), new Position(playerPos.getX(), playerPos.getY()-1), new Position (playerPos.getX(), playerPos.getY()-2)))
					moveInDirection(level, level.getPlayers().get(0), new Position(playerPos.getX(), playerPos.getY()-1), new Position (playerPos.getX(), playerPos.getY()-2));
				else
					return false;
			break;
			case "right":
				if (isMovePossible(level, level.getPlayers().get(0), new Position(playerPos.getX(), playerPos.getY()+1), new Position (playerPos.getX(), playerPos.getY()+2)))
					moveInDirection(level, level.getPlayers().get(0), new Position(playerPos.getX(), playerPos.getY()+1), new Position (playerPos.getX(), playerPos.getY()+2));
				else
					return false;
			break;
			}
		}
		catch (Exception e) 
		{
			
			return false;
		}
		return true;
	}
	
	/*
	 * IsMovePossible - return true/false if a movable item can move to the new position by checking the boolean variables.
	 */
	private boolean isMovePossible(Level level, iMoveable item, Position newPos, Position nextToBox) 
	{
		if(level.isValidPosition(newPos) == false)
			return false;

		//Creating a general item by copy the current item on the new player position
		iGeneralItem newItemInPosition = level.getItemInPosition(newPos);
		
		if((item instanceof Player) && (newItemInPosition instanceof Wall) && (this.getIsPlayerCanWalkOnWall() == false))
			return false;
		
		//Checking if the item is a type of box, so we will need to check another positions
		if((item instanceof Box) && (newItemInPosition instanceof Box) && (this.getIsBoxCanPushBox() == false))
			return false;
		
		if((item instanceof Player) && (newItemInPosition instanceof Box))
		{
			if(level.isValidPosition(nextToBox) == false)
				return false;
			
			if(level.getItemInPosition(nextToBox) instanceof Box)
				return false;
			
			if(level.getItemInPosition(nextToBox) instanceof Wall && this.getIsBoxCanWalkInWall() == false)
				return false;
			
			else
				return true;		
		}

		return true;
	}

	/**
	 * GetIsPlayerCanWalkOnWall.
	 * @return true/false if the player can walk on walls
	 */
	public boolean getIsPlayerCanWalkOnWall()
	{
		return isPlayerCanWalkOnWall;
	}

	/**
	 * SetPlayerCanWalkOnWall - gets a policy and initializes it to the local policy.
	 * @param isPlayerCanWalkOnWall
	 * 			a policy
	 */
	public void setPlayerCanWalkOnWall(boolean isPlayerCanWalkOnWall)
	{
		this.isPlayerCanWalkOnWall = isPlayerCanWalkOnWall;
	}

	/**
	 * GetIsPlayerCanPullBox.
	 * @return true/false if the player can pull boxes
	 */
	public boolean getIsPlayerCanPullBox()
	{
		return isPlayerCanPullBox;
	}

	/**
	 * SetPlayerCanPullBox - gets a policy and initializes it to the local policy.
	 * @param isPlayerCanPullBox
	 * 			a policy
	 */
	public void setPlayerCanPullBox(boolean isPlayerCanPullBox)
	{
		this.isPlayerCanPullBox = isPlayerCanPullBox;
	}

	/**
	 * GetIsBoxCanWalkInWall.
	 * @return true/false if a box can walk on walls
	 */
	public boolean getIsBoxCanWalkInWall()
	{
		return isBoxCanWalkOnWall;
	}

	/**
	 * SetBoxCanWalkInWall - gets a policy and initializes it to the local policy.
	 * @param isBoxCanWalkOnWall
	 * 			a policy
	 */
	public void setBoxCanWalkInWall(boolean isBoxCanWalkOnWall)
	{
		this.isBoxCanWalkOnWall = isBoxCanWalkOnWall;
	}

	/**
	 * GetIsBoxCanPushBox.
	 * @return true/false if a box can push boxes
	 */
	public boolean getIsBoxCanPushBox()
	{
		return isBoxCanPushBox;
	}

	/**
	 * SetBoxCanPushBox - gets a policy and initializes it to the local policy.
	 * @param isBoxCanPushBox
	 * 			a policy
	 */
	public void setBoxCanPushBox(boolean isBoxCanPushBox)
	{
		this.isBoxCanPushBox = isBoxCanPushBox;
	}

	/**
	 * IsBoxCanWalkOnWall.
	 * @return true/false if a box can walk on walls.
	 */
	public boolean getIsBoxCanWalkOnWall()
	{
		return isBoxCanWalkOnWall;
	}

	/**
	 * SetBoxCanWalkOnWall - gets a policy and initializes it to the local policy.
	 * @param isBoxCanWalkOnWall
	 * 			a policy
	 */
	public void setBoxCanWalkOnWall(boolean isBoxCanWalkOnWall)
	{
		this.isBoxCanWalkOnWall = isBoxCanWalkOnWall;
	}
}