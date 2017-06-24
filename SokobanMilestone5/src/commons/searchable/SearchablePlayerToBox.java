package commons.searchable;

import java.util.HashMap;
import model.data.items.Position;
import search.Action;
import search.State;

public class SearchablePlayerToBox extends CommonSearchable 
{
	public SearchablePlayerToBox(char[][] board, Position fromPos, Position toPos) 
	{
		super(board, fromPos, toPos);
	}
	
	public HashMap<Action, State<Position>> getAllPossibleStates(State<Position> state) 
	{
		Position pos = state.getState();
		
		HashMap<Action, State<Position>> map = new HashMap<>();

		//Move up
		if(isValidPosition(pos.getX() - 1, pos.getY()))
		{
			if((board[pos.getX() - 1][pos.getY()] == ' ') || (board[pos.getX() - 1][pos.getY()] == 'o'))
			{
				Position newPos = new Position(pos.getX() - 1, pos.getY());
				State<Position> newState = new State<Position>(newPos, state.getCost() + 1);
				map.put(new Action("up"), newState);
			}
		}
		
		//Move down
		if(isValidPosition(pos.getX() + 1, pos.getY()))
		{
			if((board[pos.getX() + 1][pos.getY()] == ' ') || (board[pos.getX() + 1][pos.getY()] == 'o'))
			{
				Position newPos = new Position(pos.getX() + 1, pos.getY());
				State<Position> newState = new State<Position>(newPos, state.getCost() + 1);
				map.put(new Action("down"), newState);
			}
		}
		
		//Move left
		if(isValidPosition(pos.getX(), pos.getY() - 1))
		{
			if((board[pos.getX()][pos.getY() - 1] == ' ') || (board[pos.getX()][pos.getY() - 1] == 'o'))
			{
				Position newPos = new Position(pos.getX(), pos.getY() - 1);
				State<Position> newState = new State<Position>(newPos, state.getCost() + 1);
				map.put(new Action("left"), newState);
			}
		}
		
		//Move right
		if(isValidPosition(pos.getX(), pos.getY() + 1))
		{
			if((board[pos.getX()][pos.getY() + 1] == ' ') || (board[pos.getX()][pos.getY() + 1] == 'o'))
			{
				Position newPos = new Position(pos.getX(), pos.getY() + 1);
				State<Position> newState = new State<Position>(newPos, state.getCost() + 1);
				map.put(new Action("right"), newState);
			}
		}
		
		return map;
	}
}