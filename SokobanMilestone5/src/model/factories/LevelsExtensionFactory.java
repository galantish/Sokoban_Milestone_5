package model.factories;

import java.util.HashMap;

import model.data.levels.MyObjectLevel;
import model.data.levels.MyTextLevel;
import model.data.levels.MyXMLLevel;
import model.data.levels.iLevelLoader;

/**
* The Class LevelLoaderFactory - The Class where we hold the file extensions list. 
*/
public class LevelsExtensionFactory
{
	private HashMap<String, Creator> hashMap;
	
	/**
	* The interface Creator - An interface that all the suffix type will implement.
	*/
	private interface Creator
	{
		iLevelLoader CreateLevelLoader();
	}
	
	/**
	* The Class TextLevelCreator - The Class that create text level loader.
	*/
	private class TextLevelCreator implements Creator
	{
		@Override
		public iLevelLoader CreateLevelLoader()
		{
			return new MyTextLevel();
		}		
	}
	
	/**
	* The Class ObjLevelCreator - The Class that create object level loader.
	*/
	private class ObjLevelCreator implements Creator
	{
		@Override
		public iLevelLoader CreateLevelLoader()
		{
			return new MyObjectLevel();
		}		
	}
	
	/**
	* The Class XMLLevelCreator - The Class that create XML level loader.
	*/
	private class XMLLevelCreator implements Creator
	{
		@Override
		public iLevelLoader CreateLevelLoader()
		{
			return new MyXMLLevel();
		}		
	}
	
	/**
	* Initializes the commands map.
	*/
	public LevelsExtensionFactory()
	{
		this.hashMap = new HashMap<String, Creator>();
		hashMap.put("txt", new TextLevelCreator());
		hashMap.put("obj", new ObjLevelCreator());
		hashMap.put("xml", new XMLLevelCreator());
	}
	
	/**
	 * Gets string from the user.
	 *@param path
	 *			path of level file
	 *
	 * @return the fitting create file by the fitting suffix.
	 */
	public iLevelLoader CreateLevelLoader(String path)
	{
		if(hashMap.get(path.substring(path.lastIndexOf('.') + 1)) == null)
			return null;
		return hashMap.get(path.substring(path.lastIndexOf('.') + 1)).CreateLevelLoader();
	}
}
