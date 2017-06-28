package boot;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import db.CompressedLevel;
import db.Level;
import db.Record;
import db.User;

public class Mainush
{
	public static void main(String[] args)
	{
		char[][] levelBoard = {{'#','#','#','#','#'},
							   {'#','o','@','A','#'},
							   {'#','#','#','#','#'}};
		
		CompressedLevel compLevel = new CompressedLevel("Coldplay", levelBoard);
		
		System.out.println("Compressed Level ID = " + compLevel.getLevelID());
		
		Level level = compLevel.deCompressedLevel();
		
//		System.out.println("Level after decompression.. \n");
//		level.printLevel();
//		
//		Gson json;
//		GsonBuilder builder = new GsonBuilder();
//		json = builder.create();
//		
//		Type type = new TypeToken<List<Record>>(){}.getType();
//		System.out.println(type);
//
//		List<Record> recordsList = new ArrayList<>();
//		recordsList.add(new Record("1", "2", 3, "4"));
//		
//		String strToJson = json.toJson(recordsList);
//		
//		List<Record> pleaseWork = json.fromJson(strToJson, type);
//		
//		System.out.println("ADALE = " + pleaseWork);
	
	}
}
