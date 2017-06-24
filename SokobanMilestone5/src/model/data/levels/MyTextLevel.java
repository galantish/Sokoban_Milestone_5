package model.data.levels;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;

import commons.Level;
import model.data.items.Box;
import model.data.items.MovableFactory;
import model.data.items.Player;
import model.data.items.Position;
import model.data.items.Target;
import model.data.items.UnmovableFactory;
import model.data.items.iMoveable;
import model.data.items.iUnmoveable;

/**
* The Class MyTextLevelLoader - The class that load and save a text file.
*/
public class MyTextLevel extends CommonLevelLoader implements iLevelLoader
{	
	@Override
	public Level LoadLevel(InputStream file) throws IOException, ClassNotFoundException
	{			
		//Reading the contents of a text file
		BufferedReader bf = new BufferedReader(new InputStreamReader(file));
		String line;
		ArrayList<String> linesArr = new ArrayList<String>();

		//Checking the size of the text (by row and column)
		int row = 0;
		int col = 0;
		
		//TO DO MIKRE KAZE
		String levelID = bf.readLine();
		
		while((line = bf.readLine()) != null)
		{
			if(line.length() > col)
				col = line.length();	
			row++;
			linesArr.add(line);
		}
		
		//Creating a 2D array which every cell is a char
		char[][] arr = new char[row][col];
		for(int i=0;i<row;i++)
			arr[i] = linesArr.get(i).toCharArray();
				
		//Creating 2 factories for movable and unmovable item
		MovableFactory movFactory = new MovableFactory();
		UnmovableFactory unmovFactory = new UnmovableFactory();
		
		//Creating a new level with the row and column we found earlier
		Level myLevel = new Level(row, col, levelID);
						
		//Initializing the 2D array from chars to objects
		for(int i = 0; i < row; i++)
		{
			for(int j = 0; j < col; j++)
			{
				//Using this method that returns the type of the item
				switch (getHashMapTypes().get(arr[i][j]))
				{
					case "movable":
						//Creating a movable item and sets the position
						iMoveable mov = movFactory.CreateMovable(arr[i][j], new Position(i,j));
						//Inserts the movable item to the 2D array list (Level Class)
						myLevel.setMovableItemInIndex(mov);
						//Adding the item to the fitting arraylist
						if(mov instanceof Player)
							myLevel.addPlayerToPlayers(mov);
						else
							myLevel.addBoxToBoxes(mov);
						break;
					
					case "unmovable":
						//Creating an unmovable item and sets the position
						iUnmoveable unmov = unmovFactory.CreateUnmovable(arr[i][j], new Position(i, j));
						//Inserts the unmovable item to the 2D array list (Level Class)
						myLevel.setUnmovableItemsInIndex(unmov);
						//Adding the item to the Targers arraylist if it is a type of target
						if(unmov instanceof Target)
							myLevel.addTargetToTargets(unmov);
					break;
					default:
						throw new IOException("ERROR: Invalid Input.");					
					}
			}
		}			
		return myLevel;
	}
	
	@Override
	public void SaveLevel(Level level, OutputStream file) throws IOException
	{
		//Reading the contents of level object and save it into buffer
		BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(file));
		Level myLevel = (Level) level;
		
		//Getting the size (row and column) of the level 
		int row = myLevel.getRow();
		int col = myLevel.getCol();
		
		bf.write(level.getLevelID());
		bf.newLine();
		
		//Writing the items which it's representative char
		for(int i = 0; i <row; i++)
		{
			for(int j = 0; j < col; j++)
			{			
				iMoveable m = myLevel.getItemsOnBoard()[i][j];
				if(m instanceof Player || m instanceof Box)
					bf.write(m.getTypeOfObject());
				else
					bf.write(myLevel.getBoard()[i][j].getTypeOfObject());				
			}	
			
			//Checking if it is the last row (so there will be no new line)
			if(i == (row-1))
				break;
			
			bf.newLine();
			bf.flush();
		}		
		//If the outputStream is type of a PrintStream (like "syso") - don't close the buffer
		if(!(file instanceof PrintStream))
			bf.close();
	}
}
	
	