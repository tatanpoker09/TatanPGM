package cl.eilers.tatanpoker09.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MapUtils {
	public static ArrayList<String> MapList(){
		File mapsFolder = new File("maps/");
		File[] maps = mapsFolder.listFiles();
		List<String> mapNames = new ArrayList<String>();
		for(File file : maps){
			String mapName = file.getName();
			mapNames.add(mapName);
		}
		return (ArrayList<String>) mapNames;
	}
}
