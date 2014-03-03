package cl.eilers.tatanpoker09.utils;

import java.io.File;

public class MapUtils {
	public static File[] MapList(){
		File mapsFolder = new File("maps/");
		File[] maps = mapsFolder.listFiles();
		return maps;
	}
}
