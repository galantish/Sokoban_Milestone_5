package model.data.levels;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import commons.Level;

/**
* The Class MyObjectLevelLoader - The class that load and save an object file.
*/
public class MyObjectLevel implements iLevelLoader
{
	@Override	
	public Level LoadLevel(InputStream file) throws IOException, ClassNotFoundException
	{
		ObjectInputStream objectIn = new ObjectInputStream(new BufferedInputStream(file));
		Level myLevel = (Level) objectIn.readObject();
		objectIn.close();
		return myLevel;
	}

	@Override
	public void SaveLevel(Level level, OutputStream file) throws IOException
	{
		ObjectOutputStream objectOut = new ObjectOutputStream(new BufferedOutputStream(file));
		objectOut.writeObject(level);
		objectOut.close();
	}
}