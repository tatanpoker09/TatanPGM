package cl.eilers.tatanpoker.Map;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import cl.eilers.tatanpoker09.utils.FileUtils;

public class MapLoader {
	public static void Load(String nextMap, World mapBefore){
		File src = new File("maps/"+nextMap);
		if(mapBefore.getName().equals(nextMap)) {
			nextMap = nextMap + "1";
		}
		File mapDone = new File(mapBefore.getName());
		File dest = new File(nextMap);
		dest.mkdir();
		//Copies the map from /maps to the main folder.
		try {
			FileUtils.copyFolder(src, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		//Unloads last played world
		if(!mapBefore.getName().equals("spawn")){
			System.out.println("Unloading map!" + mapBefore.getName());
			Bukkit.unloadWorld(mapBefore, true);
			//Last played world is deleted
			FileUtils.delete(mapDone);
		}
		World nextWorld = new WorldCreator(nextMap).createWorld();
		for(Player playersOnWorld : Bukkit.getOnlinePlayers()){
			playersOnWorld.teleport(nextWorld.getSpawnLocation());
		}
	}
}
