package model.data.levels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import commons.Level;

/**
* 1. We have built an interface that defines the functionality of data creator, so that data creator creates in external class that doesn’t depends 
* on data for the level we need to save, instead he gets the data as a variable. In the interface we created a method that return level as an object 
* (constitute the expression of the data). This way, when we would like to create a level, the data creator will be the only one who do it.<br>
* 2. This separation allows us to follow open/close principle so that in the future we could create another type of loading or saving in external way
* without damage the existing code. So how we do it? We create unique load level that implements the method we define in the interface in his own way
* including any type of new loading or saving. <br>
* 3. LISKOV separation principle is reflected in our design since all the classes that loading or saving implements the same interface, 
* there's no importance for the type of file we want to load or save, we don’t do DownCasting, instead those classes will implement the interface 
* and the load/save level method’s.
* <br>
* 4. We’ve chosen to use InputStream due to the fact that in this way we do not restrict ourselves to load or save files only, 
* we can load/save any kind of input. <br>
 */
public interface iLevelLoader
{
	/**
	 * Load level - Will load a new level.
	 * @param file
	 *            the path of the input-stream path
	 */
	public Level LoadLevel(InputStream file) throws IOException, ClassNotFoundException, FileNotFoundException;
	
	/**
	 * Save level - Will save the current level.
	 * @param level
	 *            the current level
	 * @param out
	 *            the path of the output-stream path
	 */
	public void SaveLevel(Level level, OutputStream out) throws IOException;
}
