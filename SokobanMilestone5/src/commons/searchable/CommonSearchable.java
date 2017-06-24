package commons.searchable;

import commons.Level;
import model.data.items.Position;
import search.State;
import search.iSearchable;

public abstract class CommonSearchable implements iSearchable<Position> 
{
	private Position fromPos;
	private Position toPos;
	protected char[][] board;
	protected Level level;
	
	public CommonSearchable(Position fromPosition, Position toPosition, char[][] board) 
	{
		initParams(board, fromPosition, toPosition);
	}
	
	public CommonSearchable(char[][] board, Position fromPos, Position toPos) 
	{
		this.board = copyBoard(board);
		this.fromPos = fromPos;
		this.toPos = toPos;
		this.board[fromPos.getX()][fromPos.getY()] = ' ';
	}
	
	public CommonSearchable(Level level, Position fromPos, Position toPos) 
	{
		this.board = copyBoard(level.getLevelBoard());
		this.fromPos = fromPos;
		this.toPos = toPos;
		this.board[fromPos.getX()][fromPos.getY()] = ' ';
	}
	
	@Override
	public State<Position> getInitialState()
	{
		State<Position> startPos = new State<Position>(fromPos, 0);
		startPos.setCameFrom(null);
		startPos.setAction(null);
		
		return startPos;
	}
	
	@Override
	public State<Position> getGoalState()
	{
		State<Position> goalState = new State<Position>(toPos, 0);
		
		//we need to init the data members?
		return goalState;
	}
	
	protected char[][] copyBoard(char[][] arr)
	{
		char[][] newArr = new char[arr.length][arr[0].length];
		
		for(int i=0; i<arr.length; i++)
			for(int j=0; j<arr[0].length; j++)
				newArr[i][j] = arr[i][j];
		
		return newArr;
	}
	
	protected boolean isValidPosition(int x, int y)
	{
		if((x < board.length) && (x >= 0) && (y < board[0].length) && (y >= 0))
			return true;
		
		return false;
	}
	
	public void initParams(char[][] board, Position fromPos, Position toPos) 
	{
		this.board = copyBoard(board);
		this.fromPos = fromPos;
		this.toPos = toPos;
		this.board[fromPos.getX()][fromPos.getY()] = ' ';
	}
}