package model;

import java.util.List;

import commons.Level;
import commons.Record;

/**
 * IModel - an interface that holds all the function that a model should implement.
 */
public interface iModel 
{
	public void loadLevel(String path);
	public void saveLevel(String path);
	public void move(String moveType);
	public Level getCurrentLevel();	
	public int getSteps();
	public List<Record> getCurrentRecordList();
	public void createQuery(String params);
	public void addUserToDB(String userID);
	public void addRecordToDB(String queryParams);
}
