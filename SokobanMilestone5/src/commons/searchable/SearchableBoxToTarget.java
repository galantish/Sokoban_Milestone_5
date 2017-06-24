package commons.searchable;

import java.util.HashMap;
import model.data.items.Position;
import search.Action;
import search.Solution;
import search.State;
import search.iSearcher;

public class SearchableBoxToTarget extends CommonSearchable
{
	private iSearcher<Position> search;
	private SearchablePlayerToBox searchablePlayerToBox;
	private Position playerinitPos;
	
	public SearchableBoxToTarget(char[][] board, Position fromPos, Position toPos, iSearcher<Position> search, SearchablePlayerToBox searchablePlayerToBox) 
	{
		super(board, fromPos, toPos);
		this.search = search;
		this.searchablePlayerToBox = searchablePlayerToBox;
		this.playerinitPos = removePlayerFromBoard();
	}

	@Override
	public HashMap<Action, State<Position>> getAllPossibleStates(State<Position> state) 
	{
		HashMap<Action, State<Position>> states = new HashMap<>();

		// get the position from the state
		Position currPos = state.getState();

		Position playerTarget;

		Solution playerSolution;
		Position playerPos = getPlayerPos(state.getAction(), currPos);
		if (playerPos == null)
			return null;

		// move up and down
		if (isValidPosition(currPos.getX() - 1, currPos.getY()) && isValidPosition(currPos.getX() + 1, currPos.getY())) 
		{
			if (isPossibleToMove(currPos.getX() - 1, currPos.getY())&& isPossibleToMove(currPos.getX() + 1, currPos.getY()))
			{
				// move up
				// checking if the player could reach to the opposite position
				playerTarget = new Position(currPos.getX() + 1, currPos.getY());
				playerSolution = isPlayerCanMoveToBox(currPos, playerPos, playerTarget);
				
				if (playerSolution != null || playerTarget.equals(playerPos)) 
				{
					Position newPos = new Position(currPos.getX() - 1, currPos.getY());
					State<Position> newState = new State<Position>(newPos, state.getCost() + 1);
					newState.setPreActions(playerSolution.getActionsList());
					states.put(new Action("up"), newState);
				}

				// move down
				playerTarget = new Position(currPos.getX() - 1, currPos.getY());
				playerSolution = isPlayerCanMoveToBox(currPos, playerPos, playerTarget);
				if (playerSolution != null || playerTarget.equals(playerPos)) 
				{
					Position newPos = new Position(currPos.getX() + 1, currPos.getY());
					State<Position> newState = new State<Position>(newPos, state.getCost() + 1);
					newState.setPreActions(playerSolution.getActionsList());
					states.put(new Action("down"), newState);
				}
			}
		}
		if(isValidPosition(currPos.getX(), currPos.getY() - 1)&&isValidPosition(currPos.getX(), currPos.getY() + 1)){
			if(isPossibleToMove(currPos.getX(),currPos.getY() - 1)&& isPossibleToMove(currPos.getX(), currPos.getY() + 1)){
				//move left
				playerTarget=new Position(currPos.getX(),currPos.getY()+1);
				playerSolution=isPlayerCanMoveToBox(currPos, playerPos, playerTarget);
				if(playerSolution!=null || playerTarget.equals(playerPos))
				{
					Position newPos=new Position(currPos.getX(), currPos.getY()-1);
					State<Position> newState = new State<Position>(newPos, state.getCost() + 1);
					newState.setPreActions(playerSolution.getActionsList());
					states.put(new Action("left"), newState);
				}
				
				//move right
				playerTarget=new Position(currPos.getX(), currPos.getY()-1);
				playerSolution=isPlayerCanMoveToBox(currPos, playerPos, playerTarget);
				if(playerSolution!=null || playerTarget.equals(playerPos))
				{
					Position newPos=new Position(currPos.getX(),currPos.getY()+1);
					State<Position> newState = new State<Position>(newPos, state.getCost() + 1);
					newState.setPreActions(playerSolution.getActionsList());
					states.put(new Action("right"), newState);
				}
				
				
			}
		
		
		}

		return states;
	}

	private Solution isPlayerCanMoveToBox(Position pos, Position playerPos, Position playerNewPos)
	{
		Solution sol;
		char[][] currentBoard;
	
		currentBoard = copyBoard(board);
		currentBoard[pos.getX()][pos.getY()] = '@';
		currentBoard[playerPos.getX()][playerPos.getY()] = 'A';
		this.searchablePlayerToBox.initParams(currentBoard, playerPos, playerNewPos);
		sol = this.search.search(this.searchablePlayerToBox);
		
		return sol;
	}
	
	private Position getPlayerPos(Action action, Position pos) 
	{
		if(action == null)
		{
			System.out.println("action is null");
			System.out.println(playerinitPos.toString());
			return this.playerinitPos;
		}
		
		String str = action.getAction();
		if(str.equals("up")) 
			return new Position(pos.getX() + 1, pos.getY());
		
		if (str.equals("down")) 
			return new Position(pos.getX() - 1, pos.getY());
			
		if (str.equals("left")) 
			return new Position(pos.getX(), pos.getY() + 1);
		
		if (str.equals("right")) 
			return new Position(pos.getX(), pos.getY() - 1);
		
		return null;
	}
	
	private Position removePlayerFromBoard()
	{
		for(int i=0; i<board.length; i++)
		{
			for(int j=0; j<board[0].length; j++)
			{
				if(board[i][j] == 'A')
				{
					board[i][j] = ' ';
					return new Position(i,j);
				}
			}
		}
		
		return null;
	}
	
	private boolean isPossibleToMove(int x, int y) 
	{
		return this.board[x][y]==' '||this.board[x][y]=='o';
	}
}