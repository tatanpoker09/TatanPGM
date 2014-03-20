package cl.eilers.tatanpoker09.map;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import cl.eilers.tatanpoker09.listeners.ChatListener;
import cl.eilers.tatanpoker09.objectives.WoolObjective;
import cl.eilers.tatanpoker09.utils.ScoreboardUtils;

public class ScoreboardLoading {
	public static void initScoreboard(){
		Scoreboard objectivesBoard = ScoreboardUtils.scoreBManager.getNewScoreboard();	
		ArrayList<String> scoreboardDisplays = new ArrayList<String>();
		//Getting info from XML
		String[][] teamInfo = MapXMLLoading.getTeamInfo();
		//int teamOneMax = Integer.parseInt(teamInfo[0][1]);

		//int teamTwoMax = Integer.parseInt(teamInfo[1][1]);

		//Teams
		ScoreboardUtils.mainBoard.getTeam("FirstTeam").setPrefix(""+ChatListener.stringToColor(teamInfo[0][0]));
		ScoreboardUtils.mainBoard.getTeam("FirstTeam").setDisplayName(ChatListener.stringToColor(teamInfo[0][0])+teamInfo[0][2]);
		scoreboardDisplays.add(ChatListener.stringToColor(teamInfo[0][0])+teamInfo[0][2]);
		for(WoolObjective wool : WoolObjective.getWools()){
			if(wool.getTeam().equals(ScoreboardUtils.mainBoard.getTeam("FirstTeam"))){
				String woolDisplay = " "+ChatColor.RED+wool.getName();
				scoreboardDisplays.add(woolDisplay);
			}
		}
		ScoreboardUtils.mainBoard.getTeam("SecondTeam").setPrefix(""+ChatListener.stringToColor(teamInfo[1][0]));
		ScoreboardUtils.mainBoard.getTeam("SecondTeam").setDisplayName(ChatListener.stringToColor(teamInfo[1][0])+teamInfo[1][2]);
		scoreboardDisplays.add(ChatListener.stringToColor(teamInfo[1][0])+teamInfo[1][2]);
		for(WoolObjective wool : WoolObjective.getWools()){
			if(wool.getTeam().equals(ScoreboardUtils.mainBoard.getTeam("SecondTeam"))){
				String woolDisplay = " "+ChatColor.RED+wool.getName();
				scoreboardDisplays.add(woolDisplay);
			}
		}
		//=================================
		ScoreboardUtils.mainBoard.getTeam("Observers").setPrefix(""+ChatColor.AQUA);
		ScoreboardUtils.mainBoard.getTeam("Observers").setDisplayName(ChatColor.AQUA+"Observers");
		//=================================

		Objective mainObj = objectivesBoard.registerNewObjective("Objectives", "dummy");
		mainObj.setDisplaySlot(DisplaySlot.SIDEBAR);
		mainObj.setDisplayName(ChatColor.GOLD+"Objectives");
		
		ScoreboardUtils.organizeScoreboard(scoreboardDisplays, objectivesBoard);
		for(Player playersOnWorld : Bukkit.getWorld(MapXMLLoading.currentMap.getName()).getPlayers()){
			playersOnWorld.setScoreboard(objectivesBoard);
			ScoreboardUtils.mainBoard.getTeam("Observers").addPlayer(playersOnWorld);
			playersOnWorld.getInventory().clear();
			playersOnWorld.setGameMode(GameMode.CREATIVE);
		}
	}
}
