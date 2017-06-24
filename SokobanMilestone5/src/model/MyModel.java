package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import commons.Level;
import commons.Record;
import model.data.levels.iLevelLoader;
import model.db.QueryParameters;
import model.db.SokobanDBManager;
import model.db.User;
import model.factories.LevelsExtensionFactory;
import model.policy.MySokobanPolicy;

/*
 * MyModel - a class that is a type of iModel and an observable to the SOKOBAN controller.
 */
public class MyModel extends Observable implements iModel 
{
	private Level theLevel;
	private MySokobanPolicy policy;
	private LevelsExtensionFactory levelExtension;
	private List<Record> recordsList;
	private SokobanDBManager manager;
	
	public MyModel() 
	{
		this.theLevel = new Level();
		this.levelExtension = new LevelsExtensionFactory();
		this.policy = new MySokobanPolicy();
		this.manager = SokobanDBManager.getInstance();
	}

	@Override
	public void loadLevel(String path) 
	{
		Thread t = new Thread(new Runnable() 
		{		
			@Override
			public void run() 
			{
				iLevelLoader levelLoader = levelExtension.CreateLevelLoader(path.toLowerCase());
				if(levelLoader == null)
				{
					setChanged();
					notifyObservers("error Invalid file.");
					return;
				}
				FileInputStream in = null;
				try 
				{
					in = new FileInputStream(new File(path));
					setTheLevel(levelLoader.LoadLevel(in));
					setChanged();
					notifyObservers("change");
					
					if(!manager.isExistLevel(theLevel.getLevelID()))
						manager.add(theLevel);
				} 
				catch (FileNotFoundException e) 
				{
					setChanged();
					notifyObservers("error Invalid file.");
					return;
				}
				catch (ClassNotFoundException | IOException e) 
				{
					setChanged();
					notifyObservers("error Invalid file.");
					return;
				}
			}
		});
		t.start();
		
		try 
		{
			t.join();
		} 
		catch (InterruptedException e) 
		{
			setChanged();
			notifyObservers("error " + e.getMessage());
		}
	}
	
	@Override
	public void saveLevel(String path) 
	{
		if(this.theLevel.isEmpty() == true)
		{
			setChanged();
			notifyObservers("error You need to load level first.");
			return;
		}
		
		Thread t = new Thread(new Runnable() 
		{		
			@Override
			public void run() 
			{
				iLevelLoader levelSaver = getLevelExtension().CreateLevelLoader(path.toLowerCase());
				if(levelSaver == null)
				{
					setChanged();
					notifyObservers("error Invalid file.");
					return;
				}	
				FileOutputStream out = null;
				try 
				{
					out = new FileOutputStream(new File(path));
					levelSaver.SaveLevel(getTheLevel(), out);
					setChanged();
					notifyObservers("change");
					return;
				}  
				catch (FileNotFoundException e) 
				{
					setChanged();
					notifyObservers("error Invalid file.");
					return;
				} 
				catch (IOException e) 
				{
					setChanged();
					notifyObservers("error Invalid file.");
					return;
				}
			}
		});
		t.start();
	}
	
	@Override
	public void move(String moveType) 
	{
		if(isValidMoveType(moveType) == false)
		{
			setChanged();
			notifyObservers("error Invalid move type.");
			return;
		}
		try
		{
			if(this.theLevel.isEmpty() == true)
			{
				setChanged();
				notifyObservers("error You need to load level first.");
				return;
			}
			
			if(moveType == null)
			{
				setChanged();
				notifyObservers("error Invalid move type.");
				return;
			}	

			boolean isCanMove = policy.move(this.theLevel, moveType);

			if(isCanMove == false)
				return;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		setChanged();
		notifyObservers("change");
	}
	
	/**
	 * IsValidMoveType - checking if a move type command is correct ot not.
	 * @param moveType
	 * @return
	 */
	public boolean isValidMoveType(String moveType)
	{
		ArrayList<String> moveList = new ArrayList<String>();
		moveList.add("up");
		moveList.add("down");
		moveList.add("right");
		moveList.add("left");
		
		if(moveList.contains(moveType.toLowerCase()) == true)
			return true;
		return false;
	}
	
	@Override
	public Level getCurrentLevel() 
	{
		return theLevel;
	}

	@Override
	public int getSteps() 
	{
		return this.theLevel.getPlayersSteps();				
	}	
	
	public Level getTheLevel() 
	{
		return theLevel;
	}

	public void setTheLevel(Level theLevel) 
	{
		this.theLevel = theLevel;
	}

	public LevelsExtensionFactory getLevelExtension() 
	{
		return levelExtension;
	}

	public void setLevelExtension(LevelsExtensionFactory levelExtension) 
	{
		this.levelExtension = levelExtension;
	}

	@Override
	public List<Record> getCurrentRecordList() 
	{
		return this.recordsList;
	}

	@Override
	public void createQuery(String params) 
	{
		String[] queryParams = params.split(" ");

		QueryParameters q = new QueryParameters(queryParams[0], queryParams[1], queryParams[2]);

		Thread t = new Thread(new Runnable() 
		{
			@Override
			public void run() 
			{
				recordsList = manager.recordsQuery(q);
				setChanged();
				notifyObservers("showdbresults");
			}
		});
		
		t.start();
		
	}

	@Override
	public void addUserToDB(String userName) 
	{
		if(!manager.isExistUser(userName))
			manager.add(new User(userName));
	}

	@Override
	public void addRecordToDB(String params) 
	{
		String s[] = params.split(" ");
		Record record = new Record(theLevel.getLevelID(), s[0], theLevel.getPlayersSteps(), s[1]);
		System.out.println(theLevel.getLevelID() + " " + s[0] + " " + theLevel.getPlayersSteps() + " " + s[1]);
		manager.add(record);
	}
}
