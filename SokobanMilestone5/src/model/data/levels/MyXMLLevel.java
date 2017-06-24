package model.data.levels;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import commons.Level;

/**
* The Class MyXMLLevelLoader - The class that load and save an XML file.
*/
public class MyXMLLevel implements iLevelLoader
{
	@Override
	public Level LoadLevel(InputStream file) throws IOException
	{
		XMLDecoder xmlDecoder = new XMLDecoder(new BufferedInputStream(file));		
		Level myLevel = (Level) xmlDecoder.readObject();		
		xmlDecoder.close();		
		return myLevel;
	}

	@Override
	public void SaveLevel(Level level, OutputStream file) throws IOException
	{
		XMLEncoder xmlEncoder = new XMLEncoder(new BufferedOutputStream(file));		
		xmlEncoder.writeObject(level);		
		xmlEncoder.close();		
	}

}
