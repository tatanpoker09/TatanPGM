package cl.eilers.tatanpoker.map;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import cl.eilers.tatanpoker09.utils.FileUtils;

public class MapLoader {
	public static void Load(String nextMap){
		//Initializing Scoreboard.
		ScoreboardManager manager = Bukkit.getScoreboardManager();
	    Scoreboard board = manager.getNewScoreboard();
		File src = new File("maps/"+nextMap);
		/*Possible thing for same map?
		 * if(mapBefore.getName().equals(nextMap)) {
			nextMap = nextMap + "1";
		}*/
		//File mapDone = new File(mapBefore.getName());
		File dest = new File(nextMap);
		dest.mkdir();
		//Copies the map from /maps to the main folder.
		try {
			FileUtils.copyFolder(src, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Starts With XML Stuff
		String[] teamInfo = MapXMLLoading.teamXML(nextMap);
		String teamColor = teamInfo[0];
		String teamName = teamInfo[2];
		Team team1 = board.registerNewTeam(teamName);
		Team team2 = board.registerNewTeam(teamName);
		
		//Unloads last played world
		/*if(!mapBefore.getName().equals("spawn")){
			System.out.println("Unloading map!" + mapBefore.getName());
			Bukkit.unloadWorld(mapBefore, true);
			//Last played world is deleted
			FileUtils.delete(mapDone);
		}*/
		World nextWorld = new WorldCreator(nextMap).createWorld();
		for(Player playersOnWorld : Bukkit.getOnlinePlayers()){
			playersOnWorld.teleport(nextWorld.getSpawnLocation());
		}
	}
}
