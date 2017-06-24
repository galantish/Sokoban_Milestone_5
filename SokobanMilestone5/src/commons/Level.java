package commons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import model.data.items.*;
import model.db.User;

/**
* The Class Level - The common class that build a level.
*/
@Entity(name="Levels")
public class Level implements Serializable 
{
	@Transient private int playersSteps;
	@Transient private ArrayList<Player> players;
	@Transient private ArrayList<Box> boxes;	
	@Transient private ArrayList<Target> targets;	
	@Transient private iMoveable[][] itemsOnBoard;
	@Transient private iUnmoveable[][] board;
	@Transient private int row;
	@Transient private int col;
	
	@Id
	@Column(name="LevelID")
	private String levelID;
	
	@OneToMany
	@JoinColumn(name="LevelID")
	private List<User> users = new ArrayList<User>();
	
	/**
	 * Initializes the level.
	 */
	public Level() 
	{
		this.row = 0;
		this.col = 0;
		this.playersSteps = 0;
		this.players = new ArrayList<Player>(); 
		this.boxes = new ArrayList<Box>();
		this.targets = new ArrayList<Target>();
		this.itemsOnBoard = new iMoveable[this.row][this.col];		
		this.board = new iUnmoveable[this.row][this.col];
		this.levelID = null;
	}
	
	
	
	/**
	 * Initializes the level by row and col.
	 * @param row
	 * 			2D arrays's length
	 * @param col
	 * 			2D arrays's width
	 */
	public Level(int row, int col)
	{
		this.row = row;
		this.col = col;
		this.playersSteps = 0;
		this.players = new ArrayList<Player>(); 
		this.boxes = new ArrayList<Box>();
		this.targets = new ArrayList<Target>();
		this.itemsOnBoard = new iMoveable[this.row][this.col];		
		this.board = new iUnmoveable[this.row][this.col];
		this.levelID = null;
		
		//Initializing the background array to Floor
		for(int i = 0; i < this.row; i++)
			for(int j = 0; j < this.col; j++)
				this.board[i][j] = new Floor();
	}
	
	public Level(int row, int col, String levelID)
	{
		this.row = row;
		this.col = col;
		this.playersSteps = 0;
		this.players = new ArrayList<Player>(); 
		this.boxes = new ArrayList<Box>();
		this.targets = new ArrayList<Target>();
		this.itemsOnBoard = new iMoveable[this.row][this.col];		
		this.board = new iUnmoveable[this.row][this.col];
		this.levelID = levelID;
		
		//Initializing the background array to Floor
		for(int i = 0; i < this.row; i++)
			for(int j = 0; j < this.col; j++)
				this.board[i][j] = new Floor();
	}
	
	public Level(String levelID)
	{
		this.row = 0;
		this.col = 0;
		this.playersSteps = 0;
		this.players = new ArrayList<Player>(); 
		this.boxes = new ArrayList<Box>();
		this.targets = new ArrayList<Target>();
		this.itemsOnBoard = new iMoveable[this.row][this.col];		
		this.board = new iUnmoveable[this.row][this.col];
		this.levelID = levelID;
	}
	
	/**
	 * Initializes the level by another level (copy-ctor)
	 * @param level
	 * 			a level
	 */
	public Level(Level level) 
	{
		this.row = level.row;
		this.col = level.col;
		this.itemsOnBoard = level.itemsOnBoard;
		this.board = level.board;
		this.players = level.players;
		this.boxes = level.boxes;
		this.targets = level.targets;
		this.playersSteps = level.playersSteps;
		this.levelID = level.levelID;
	}

	public List<User> getUsers() 
	{
		return users;
	}

	public void setUsers(List<User> users) 
	{
		this.users = users;
	}

	/**
	* GetItemsOnBoard.
	* @return the 2D array of movable items
	*/
	public iMoveable[][] getItemsOnBoard() 
	{
		return itemsOnBoard;
	}

	/**
	* SetItemsOnBoard.
	* @param itemsOnBoard 
	* 			the current level time
	*/
	public void setItemsOnBoard(iMoveable[][] itemsOnBoard) 
	{
		this.itemsOnBoard = itemsOnBoard;
	}

	/**
	* GetBoard.
	* @return the 2D array of unmovable items
	*/
	public iUnmoveable[][] getBoard() 
	{
		return board;
	}

	/**
	* SetBoard.
	* @param board 
	* 			the 2D array of unmovable items
	*/
	public void setBoard(iUnmoveable[][] board) 
	{
		this.board = board;
	}

	/**
	* GetRow.
	* @return the length of the 2D arrays
	*/	
	public int getRow() 
	{
		return row;
	}

	/**
	* SetRow.
	* @param row
	* 			the length of the 2D arrays
	*/	
	public void setRow(int row) 
	{
		this.row = row;
	}

	/**
	* GetCol.
	* @return the width of the 2D arrays
	*/	
	public int getCol() 
	{
		return col;
	}

	/**
	* SetCol.
	* @param col
	* 			the width of the 2D arrays
	*/	
	public void setCol(int col) 
	{
		this.col = col;
	}
	
	/**
	* GetPlayers.
	* @return players list
	*/	
	public ArrayList<Player> getPlayers()
	{
		return players;
	}

	/**
	* SetPlayers.
	* @param players
	* 			players list
	*/
	public void setPlayers(ArrayList<Player> players)
	{
		this.players = players;
	}

	/**
	* GetBoxes.
	* @return boxes list
	*/
	public ArrayList<Box> getBoxes()
	{
		return boxes;
	}

	/**
	* SetBoxes.
	* @param boxes
	* 			boxes list
	*/
	public void setBoxes(ArrayList<Box> boxes)
	{
		this.boxes = boxes;
	}

	/**
	* GetTagets.
	* @return targets list
	*/
	public ArrayList<Target> getTargets()
	{
		return targets;
	}

	/**
	* SetTagets.
	* @param targets
	* 			targets list
	*/
	public void setTargets(ArrayList<Target> targets)
	{
		this.targets = targets;
	}

	/**
	* GetPlayersSteps.
	* @return player's steps
	*/
	public int getPlayersSteps()
	{
		return playersSteps;
	}

	/**
	* SetPlayersSteps.
	* @param playersSteps
	* 			player's steps
	*/
	public void setPlayersSteps(int playersSteps)
	{
		this.playersSteps = playersSteps;
	}

	/**
	* SetMovableItemInIndex.
	* @param mov
	* 			movable item
	*/
	public void setMovableItemInIndex(iMoveable mov)
	{
		this.itemsOnBoard[mov.getPosition().getX()][mov.getPosition().getY()] = mov;
	}
	
	/**
	* SetUnmovableItemsInIndex.
	* @param unmov
	* 			 unmovable item
	*/
	public void setUnmovableItemsInIndex(iUnmoveable unmov)
	{
		this.board[unmov.getPosition().getX()][unmov.getPosition().getY()] = unmov;
	}	

	/**
	* AddBoxToBoxes.
	* @param move
	* 			movable item
	*/
	public void addBoxToBoxes(iMoveable move)
	{
		this.boxes.add((Box)move);
	}
	
	/**
	* AddPlayerToPlayers.
	* @param move
	* 			movable item
	*/
	public void addPlayerToPlayers(iMoveable move)
	{
		this.players.add((Player)move);
	}
	
	/**
	* AddTargetToTargets.
	* @param unmov
	* 			unmovable item
	*/
	public void addTargetToTargets(iUnmoveable unmov)
	{
		this.targets.add((Target)unmov);
	}
	
	/**
	* IsBoxOnTarger.
	* @param box
	* 			box
	* @return true/false if a box is on target
	*/
	public boolean isBoxOnTarget(Box box)
	{
		for(int i=0;i<this.numOfTargets();i++)
			if(box.getPosition().isEqualPosition(getTargets().get(i).getPosition()) == true)
				return true;
		return false;
	}
	
	/**
	* IsBoxOnTarger.
	* @return numbers of boxes in targets
	*/
	public int numOfBoxesInTarget()
	{
		int count = 0;
		for(int i = 0; i <this.numOfBoxes(); i++)
			if(this.getBoxes().get(i).getIsBoxInTarget() == true)
				count++;
		return count;
	}
	
	/**
	* IsBoxOnTarger.
	* @param move
	* 			movable item
	* @param position
	* 			new position
	* @return true/false if the user can move to the position
	*/
	public boolean isCanMove(iMoveable move, Position position)
	{
		if((this.board[position.getX()][position.getY()]) instanceof Floor)
			return true;
		return false;
	}
	
	/**
	* IsFinished.
	* @return true/false if the level is finish (if the user win)
	*/
	public boolean isFinished() 
	{
		if (numOfBoxesInTarget() == numOfTargets())
			return true;
		return false;
	}

	/**
	* IsLevelSolvable.
	* @return true/false if the level is solvable
	*/
	public boolean isLevelSolvable() //TO DO
	{
		return false;
	}

	/**
	* NumOfBoxes.
	* @return number of boxes 
	*/
	public int numOfBoxes()
	{
		return this.boxes.size();
	}

	public String getLevelID()
	{
		return this.levelID;
	}
	
	public void setLevelID(String levelID)
	{
		this.levelID = levelID;
	}
	
	/**
	* NumOfPlayers.
	* @return number of players 
	*/
	public int numOfPlayers()
	{
		return this.players.size();
	}

	/**
	* NumOfTargets.
	* @return number of targets 
	*/
	public int numOfTargets()
	{
		return this.targets.size();
	}
	
	/**
	* GetItemInPosition.
	* @param position
	* 			position of an item
	* @return the item 
	*/
	public iGeneralItem getItemInPosition(Position position)
	{
		iGeneralItem item;		
		if(itemsOnBoard[position.getX()][position.getY()] != null)
			item = (iMoveable) itemsOnBoard[position.getX()][position.getY()];
		else
			item = (iUnmoveable) board[position.getX()][position.getY()];		
		return item;
	}

	/**
	* IsValidPosition.
	* @param position
	* 			position of an item
	* @return true/false if the position is correct
	*/
	public boolean isValidPosition(Position position)
	{
		if((position.getX() < this.row) && (position.getX() >= 0) && (position.getY() < this.col) && (position.getY() >= 0))
			return true;
		return false;
	}
	
	/**
	 * IsEmpty.
	 * @return true/false if the level is empty or not
	 */
	public boolean isEmpty()
	{
		if(this.numOfPlayers() == 0)
			return true;
		return false;
	}
	
	/**
	 * GetLevelBoard - return a 2D char array that represent a level.
	 * @return the 2D char array
	 */
	public char[][] getLevelBoard() 
	{
		char[][] levelArr = new char[getRow()][getCol()];
		for(int i=0; i<getRow(); i++)
			for(int j=0;j<getCol(); j++)
				levelArr[i][j] = getItemInPosition(new Position(i, j)).getTypeOfObject();
		return levelArr;
	}
	
	public void printLevel()
	{
		char[][] board = getLevelBoard();
		
		for(int i=0; i<getRow(); i++)
		{
			for(int j=0;j<getCol(); j++)
				System.out.print(board[i][j]);
			System.out.println();
		}
	}
	
	@Override
	public String toString() 
	{
		return "Level [playersSteps=" + playersSteps + ", players=" + players + ", boxes=" + boxes + ", targets="
				+ targets + ", itemsOnBoard=" + Arrays.toString(itemsOnBoard) + ", board=" + Arrays.toString(board)
				+ ", row=" + row + ", col=" + col + ", levelID=" + levelID + ", users=" + users + "]";
	}
}
