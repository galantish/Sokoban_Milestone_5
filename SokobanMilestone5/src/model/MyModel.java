package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import commands.Commands;
import db.CompressedLevel;
import db.Level;
import db.QueryParameters;
import db.Record;
import model.data.levels.iLevelLoader;
import db.User;
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
	private String serverIp;
	private int serverPort;
	private Gson json;
	private ModelClient modelClient;
	private List<String> clue;
	private boolean isClueable;
	
	public MyModel(String serverIp, int serverPort) 
	{
		this.theLevel = new Level();
		this.levelExtension = new LevelsExtensionFactory();
		this.policy = new MySokobanPolicy();
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		GsonBuilder builder = new GsonBuilder();
		this.json = builder.create();
		this.modelClient = new ModelClient(this.serverIp, this.serverPort);
		this.clue = new ArrayList<>();
		this.isClueable = false;  
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
					
					isClueable=false;
					Commands command = Commands.ADD_LEVEL;
					
					CompressedLevel compLevel = new CompressedLevel(theLevel.getLevelID(), theLevel.getLevelBoard());
					
					String levelJson = json.toJson(compLevel);
					
					modelClient.createServerSession(command, levelJson);
					
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
			
			if(!this.clue.isEmpty())
			{
				if(!(moveType.toLowerCase().equals(this.clue.get(0))))
					this.isClueable = false;
				
				else
				{	
					this.clue.remove(0);
					System.out.println("Helo");
				}
			}
			
			setChanged();
			notifyObservers("change");
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		

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
		
		String queryJson = this.json.toJson(q);
		Commands command = Commands.DB_QUERY;
		
		String result = this.modelClient.createServerSession(command, queryJson);
		
		Type type = new TypeToken<List<Record>>(){}.getType();
		this.recordsList = this.json.fromJson(result, type);

		setChanged();
		notifyObservers("showdbresults");
	}

	@Override
	public void addUserToDB(String userName) 
	{
		String s[] = userName.split(" ");
		User user = new User(s[0]);
		Commands command = Commands.ADD_USER;
		String userJson = this.json.toJson(user);
		
		this.modelClient.createServerSession(command, userJson);
	}

	@Override
	public void addRecordToDB(String params) 
	{
		String s[] = params.split(" ");
		Record record = new Record(theLevel.getLevelID(), s[0], theLevel.getPlayersSteps(), s[1]);
		
		Commands command = Commands.ADD_RECORD;
		String recordJson = this.json.toJson(record);
		
		this.modelClient.createServerSession(command, recordJson);
	}

	@Override
	public void restart() 
	{
		CompressedLevel compLevel = new CompressedLevel(this.theLevel.getLevelID(), this.theLevel.getStartBoard());
		Level newLevel = compLevel.deCompressedLevel();
		
		setTheLevel(newLevel);
		
		setChanged();
		notifyObservers("change");
	}

	@Override
	public void getClue() 
	{
		if(!this.isClueable)
		{
			CompressedLevel compLevel = new CompressedLevel(this.theLevel.getLevelID(), this.theLevel.getLevelBoard());
			Commands command = Commands.GET_CLUE;
			String levelJson = this.json.toJson(compLevel);
			String solJson = this.modelClient.createServerSession(command, levelJson);
			String sol = this.json.fromJson(solJson, String.class);
			
			this.clue = deCompressedSolution(sol);
			
			if(!this.clue.isEmpty())
			{
				setChanged();
				notifyObservers(this.clue.get(0));
				this.clue.remove(0);
				this.isClueable = true;
			}
		}
		
		else
		{
			if(!this.clue.isEmpty())
			{
				setChanged();
				notifyObservers(this.clue.get(0));
				this.clue.remove(0);
			}
		}
	}

	@Override
	public void getSolution() 
	{
		CompressedLevel compLevel = new CompressedLevel(this.theLevel.getLevelID(), this.theLevel.getStartBoard());
		Commands command = Commands.GET_SOLUTION;
		String levelJson = this.json.toJson(compLevel);
		String solJson = this.modelClient.createServerSession(command, levelJson);
		String sol = this.json.fromJson(solJson, String.class);
		List<String> list = deCompressedSolution(sol);
		Timer timer = new Timer();
		
		try 
		{
			Thread.sleep(1000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		timer.scheduleAtFixedRate(new TimerTask() 
		{
			@Override
			public void run() 
			{
				if(!list.isEmpty())
				{
					setChanged();
					notifyObservers(list.get(0).toLowerCase());
					list.remove(0);
				}
			}
		}, 0, 200);
		
	}
	
	private List<String> deCompressedSolution(String sol)
	{
		List<String> list = new ArrayList<>();
		
		StringBuilder builder = new StringBuilder(sol);
		char c;
		
		for(int i=0; i<sol.length(); i++)
		{
			c = builder.charAt(i);
			switch (c) 
			{
				case 'r':
					list.add("move right");
					break;
				
				case 'l':
					list.add("move left");
					break;
				
				case 'u':
					list.add("move up");
					break;
				
				case 'd':
					list.add("move down");
					break;
				
				default:
					break;
			}
		}
		
		return list;
	}
}