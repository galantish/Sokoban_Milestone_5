package commons.searchable;

import java.util.HashMap;
import commons.Level;
import model.data.items.Position;
import search.Action;
import search.State;
import search.iSearchable;

public class SearchableLevel implements iSearchable<SokobanState>
{
	private Level theLevel;
	private char[][] board;
	private Position currPos;
	private Position newPos;
	
	public SearchableLevel(Level theLevel, Position currPos, Position newPos) 
	{
		this.theLevel = theLevel;
		this.board = theLevel.getLevelBoard();
		this.currPos = currPos;
		this.newPos = newPos;
	}
	
	public void setPositions(Position currPos, Position newPos)
	{
		this.currPos = currPos;
		this.newPos = newPos;
	}

	@Override
	public State<SokobanState> getInitialState() 
	{
		SokobanState s = new SokobanState(board);
		State<SokobanState> state = new State<SokobanState>(s, 0);
		state.setCameFrom(null);
		
		return state;
	}

	@Override
	public State<SokobanState> getGoalState() 
	{
		char[][] currBoard = new char[this.board.length][this.board[0].length];
		
		for(int i=0; i<this.board.length; i++)
			for(int j=0; j<this.board[0].length; j++)
				currBoard[i][j] = this.board[i][j];
				
		currBoard[newPos.getX()][newPos.getY()] = this.board[currPos.getX()][currPos.getY()];
		currBoard[currPos.getX()][currPos.getY()] = ' ';

		SokobanState goalState = new SokobanState(currBoard);
		State<SokobanState> state = new State<SokobanState>(goalState, 0);
		
		return state;
	}

	@Override
	public HashMap<Action, State<SokobanState>> getAllPossibleStates(State<SokobanState> state) 
	{
		char[][] stateBoard = state.getState().board;
		HashMap<Action, State<SokobanState>> map = new HashMap<>();
			
		
		
		
		return null;
	}
}