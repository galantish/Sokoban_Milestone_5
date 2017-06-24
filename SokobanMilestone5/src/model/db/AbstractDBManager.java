package model.db;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class AbstractDBManager implements iDBManager 
{
	SessionFactory factory;
	
	public AbstractDBManager() 
	{
		//to show the severe message
		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		
		//reading the XML so he can connect to the DB
		Configuration configuration = new Configuration();
		configuration.configure();
		factory = configuration.buildSessionFactory();
	}
	
}
