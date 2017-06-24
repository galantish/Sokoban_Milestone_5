package model.policy;

import commons.Level;

/*
 * iSokobanPolicy - an interface that holds all the functions that a policy of a game (SOKOABN) should implement.
 */
public interface iSokobanPolicy 
{
	/*
	 * Move - this method checks all the boolean members in the class MySokobanPolicy and checks if a movable item can move to the new position.
	 */
	public boolean move(Level level, String moveType);
}
