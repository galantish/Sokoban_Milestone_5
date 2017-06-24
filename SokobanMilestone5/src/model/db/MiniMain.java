package model.db;

import java.sql.Time;
import commons.Level;
import commons.Record;

public class MiniMain 
{
	public static void main(String[] args) 
	{
		SokobanDBManager manager = SokobanDBManager.getInstance();
		
		User user1 = new User("User1");
		User user2 = new User("User2");
		manager.add(user1);
		manager.add(user2);
		
		Level level1 = new Level("Level1");
		manager.add(level1);
		Level level2 = new Level("Level2");
		manager.add(level2);
		
		Record record1 = new Record(level1.getLevelID(), user1.getName(), 1, "0");
		manager.add(record1);
		
		Record record2 = new Record(level2.getLevelID(), user2.getName(), 1, "0");
		manager.add(record2);
		
		QueryParameters q = new QueryParameters("Level1", null, "strps");
				
		
		manager.recordsQuery(q);
	}
}