package model.data.levels;

import java.util.HashMap;

/**
* The Abstract Class CommonLevelLoader - The class that match between char and object type.
*/
public abstract class CommonLevelLoader implements iLevelLoader
{
	/** The chars and type of object list */
	private HashMap<Character, String> hashMapTypes;		
	
	/**
	 * Initializes the list of chars and objects type.
	 */
	public CommonLevelLoader()
	{ 
		this.hashMapTypes = new HashMap<Character, String>();
		this.hashMapTypes.put('A', "movable");
		this.hashMapTypes.put('@', "movable");
		this.hashMapTypes.put('o', "unmovable");
		this.hashMapTypes.put('#', "unmovable");
		this.hashMapTypes.put(' ', "unmovable");	
	}

	/**
	 * Returns a list of chars and objects type.
	 * @return list
	 */
	public HashMap<Character, String> getHashMapTypes()
	{
		return hashMapTypes;
	}

	/**
	 * Gets a list of chars and objects type and initializes the local list.
	 * @param hashMapTypes
	 * 			list of representative chars
	 */
	public void setHashMapTypes(HashMap<Character, String> hashMapTypes)
	{
		this.hashMapTypes = hashMapTypes;
	}
}
