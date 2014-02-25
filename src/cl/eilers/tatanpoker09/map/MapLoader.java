package cl.eilers.tatanpoker09.map;

import java.io.File; 
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import cl.eilers.tatanpoker09.match.Match;
import cl.eilers.tatanpoker09.utils.FileUtils;
import cl.eilers.tatanpoker09.utils.ScoreboardUtils;

public class MapLoader {
	
	public static void Load(String nextMap, World lastMap){
		System.out.println("MapLoading is starting");
		boolean fileNotExist = true;
		File src = new File("maps/"+nextMap);
		File dest = new File(nextMap+"1");
		if(dest.exists()){
			for(int i = 1; fileNotExist; i++){
				if(dest.exists()){
					dest = new File(nextMap+i);
					System.out.println("Value of 'i' is now:" + i);
				} else {
					dest = new File(nextMap + Integer.toString((int)i));
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

		Bukkit.getPluginManager().getPlugin("TatanPGM").getConfig().set("TatanPGM.CurrentMap", dest.getName());
		Bukkit.getPluginManager().getPlugin("TatanPGM").saveConfig();

		World nextWorld = new WorldCreator(dest.getName()).createWorld();
		nextWorld.setGameRuleValue("doMobSpawning", "false");
		for(Player playersOnWorld : Bukkit.getOnlinePlayers()){
			playersOnWorld.teleport(nextWorld.getSpawnLocation());
		}
		Match.hasEnded=false;
		File mapXML = new File("maps/"+nextMap+"/map.xml");
		//Unloads last played map.
		deleteWorld(lastMap.getName());
		if(mapXML.exists()){
			ScoreboardUtils.initScoreboard(dest.getName());
		}
	}

	public static boolean deleteWorld(String lastWorldName){
		if(!lastWorldName.equals(Bukkit.getWorlds().get(0).getName())){
			System.out.println("Unloading map!" + lastWorldName);
			File lastWorld = new File(lastWorldName);
			Bukkit.getServer().unloadWorld(lastWorldName, true);
			FileUtils.delete(lastWorld);
			return true;
		}
		return false;
	}	
}
