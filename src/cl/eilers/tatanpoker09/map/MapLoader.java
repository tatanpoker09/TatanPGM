package cl.eilers.tatanpoker09.map;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import cl.eilers.tatanpoker09.commands.Setnext;
import cl.eilers.tatanpoker09.utils.FileUtils;

public class MapLoader {
	public static ArrayList<String> mapNames = null;
	public static void Load(){
		System.out.println("MapLoading is starting");
		boolean fileNotExist = true;
		File src = Setnext.nextMapFolder;
		File dest = new File("playing"+Setnext.nextMap+"1");
		if(dest.exists()){
			for(int i = 1; fileNotExist; i++){
				if(dest.exists()){
					dest = new File("playing"+Setnext.nextMap+i);
					System.out.println("Value of 'i' is now:" + i);
				} else {
					dest = new File("playing"+Setnext.nextMap+ Integer.toString((int)i));
					fileNotExist = false;
				}
			}
		}

		dest.mkdir();
		//Copies the map from /maps to the main folder.
		try {
			FileUtils.copyFolder(src, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		MapXMLLoading.currentMap = dest;
		World nextWorld = new WorldCreator(dest.getName()).createWorld();
		nextWorld.setGameRuleValue("doMobSpawning", "false");
		for(Player playersOnWorld : Bukkit.getOnlinePlayers()){
			playersOnWorld.teleport(nextWorld.getSpawnLocation());
		}
		File mapXML = new File(MapXMLLoading.currentMap.getName()+"/map.xml");
		MapXMLLoading.loadExtras(mapXML);
		//Unloads last played map.
		for(World world : Bukkit.getWorlds()){
			if(world.getName().startsWith("playing")){
				if(world.getPlayers().size()==0){
					deleteWorld(world);
				}
			}
		}
		if(mapXML.exists()){
			ScoreboardLoading.initScoreboard(mapXML);
		} else {
			System.out.println(mapXML.getPath());
		}
	}

	public static boolean deleteWorld(World lastWorld){
		if(!lastWorld.getName().equals(Bukkit.getWorlds().get(0).getName())){
			System.out.println("Unloading map!" + lastWorld.getName());
			File lastWorldFile = new File(lastWorld.getName());
			Bukkit.getServer().unloadWorld(lastWorld.getName(), true);
			FileUtils.delete(lastWorldFile);
			return true;
		}
		return false;
	}	
}
