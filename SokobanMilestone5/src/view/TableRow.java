package view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableRow 
{
	private final SimpleIntegerProperty record;
	private final SimpleStringProperty levelID;
	private final SimpleStringProperty userName;
	private final SimpleIntegerProperty steps;
	private final SimpleStringProperty time;
	
	public TableRow(SimpleIntegerProperty record, SimpleStringProperty levelID, SimpleStringProperty userName, SimpleIntegerProperty steps, SimpleStringProperty time) 
	{
		this.record = record;
		this.levelID = levelID;
		this.userName = userName;
		this.steps = steps;
		this.time = time;
	}

	public TableRow(Integer record, String levelID, String userName, Integer steps, String time) 
	{
		this.record =new SimpleIntegerProperty(record);
		this.levelID =new SimpleStringProperty(levelID);
		this.userName = new SimpleStringProperty(userName);
		this.steps = new SimpleIntegerProperty(steps);
		this.time = new SimpleStringProperty(time.toString());
	}

	public Integer getRecord() 
	{
		return record.get();
	}


	public String getLevelID() 
	{
		return levelID.get();
	}

	public String getUserName() 
	{
		return userName.get();
	}


	public Integer getSteps() 
	{
		return steps.get();
	}

	public String getTime() 
	{
		return time.get();
	}
}