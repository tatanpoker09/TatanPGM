package cl.eilers.tatanpoker.map;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import cl.eilers.tatanpoker09.utils.FileUtils;

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



		World nextWorld = new WorldCreator(dest.getName()).createWorld();
		nextWorld.setGameRuleValue("doMobSpawning", "false");
		for(Player playersOnWorld : Bukkit.getOnlinePlayers()){
			playersOnWorld.teleport(nextWorld.getSpawnLocation());			
		}
		File mapXML = new File("maps/"+nextMap+"/map.xml");
		//Unloads last played map.
		deleteWorld(lastMap.getName());
		if(mapXML.exists()){
			initScoreboard(dest.getName());
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

	private static void initScoreboard(String nextMapFolder){
		//Getting info from XML
		String[][] teamInfo = MapXMLLoading.getTeamInfo(nextMapFolder);
		//String teamOneColor = teamInfo[1][0];
		String teamOneName = teamInfo[0][2];
		String teamTwoName = teamInfo[0][1];
		
		//Initializing Scoreboard.
		ScoreboardManager scoreBManager = Bukkit.getScoreboardManager();
		Scoreboard objectivesBoard = scoreBManager.getNewScoreboard();
		
		//Teams
		Team teamOne = objectivesBoard.registerNewTeam("First Team");
		Team teamTwo = objectivesBoard.registerNewTeam("Second Team");
		
		teamOne.setPrefix(ChatColor.BLUE+"");
		teamTwo.setPrefix(ChatColor.RED+"");
		
		Objective mainObj = objectivesBoard.registerNewObjective("Objectives", "dummy");
		mainObj.setDisplaySlot(DisplaySlot.SIDEBAR);
		mainObj.setDisplayName(ChatColor.YELLOW+"Objectives");
		
		Score score = mainObj.getScore(Bukkit.getOfflinePlayer(teamOneName));
		score.setScore(5);	
		score = mainObj.getScore(Bukkit.getOfflinePlayer(teamTwoName));
		score.setScore(4);
		
		for(Player playersOnWorld : Bukkit.getWorld(nextMapFolder).getPlayers()){
			playersOnWorld.setScoreboard(objectivesBoard);
		}
	}
}
