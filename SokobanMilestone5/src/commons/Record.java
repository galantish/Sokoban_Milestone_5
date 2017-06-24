package commons;

import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="Records")
public class Record
{
	@Id
	@Column(name="RecordID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int record;
	
	@Column(name="LevelID")
	private String levelID;

	@Column(name="UserName")
	private String userName;
	
	@Column(name="Steps")
	private int steps;
	
	@Column(name="LevelTime")
	private String time;

	public Record() {}
	
	public Record(String levelID, String userName, int steps, String time) 
	{
		this.levelID = levelID;
		this.userName = userName;
		this.steps = steps;
		this.time = time;
	}

	@Override
	public String toString() 
	{
		return "Record [record=" + record + ", levelID=" + levelID + ", userName=" + userName + ", steps=" + steps
				+ ", time=" + time + "]";
	}

	public int getRecord() 
	{
		return record;
	}

	public void setRecord(int record) 
	{
		this.record = record;
	}

	public String getLevelID() 
	{
		return levelID;
	}

	public void setLevelID(String levelID) 
	{
		this.levelID = levelID;
	}

	public String getUserName() 
	{
		return userName;
	}

	public void setUserName(String userName) 
	{
		this.userName = userName;
	}

	public int getSteps() 
	{
		return steps;
	}

	public void setSteps(int steps) 
	{
		this.steps = steps;
	}

	public String getTime() 
	{
		return time;
	}

	public void setTime(String time) 
	{
		this.time = time;
	}
	
}
