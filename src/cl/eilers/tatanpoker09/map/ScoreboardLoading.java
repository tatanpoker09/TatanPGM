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
import cl.eilers.tatanpoker09.utils.ScoreboardUtils;

public class ScoreboardLoading {
	public static void initScoreboard(String nextMapFolder){
		Scoreboard objectivesBoard = ScoreboardUtils.scoreBManager.getNewScoreboard();	
		ArrayList<String> scoreboardDisplays = new ArrayList<String>();
		//Getting info from XML
		String[][] teamInfo = MapXMLLoading.getTeamInfo(nextMapFolder);
		String teamOneColor = teamInfo[0][0];
		//int teamOneMax = Integer.parseInt(teamInfo[0][1]);
		String teamOneName = teamInfo[0][2];
		String teamTwoColor = teamInfo[1][0];
		//int teamTwoMax = Integer.parseInt(teamInfo[1][1]);
		String teamTwoName = teamInfo[1][2];


		//Teams
		ScoreboardUtils.mainBoard.getTeam("FirstTeam").setPrefix(""+ChatListener.stringToColor(teamOneColor));
		ScoreboardUtils.mainBoard.getTeam("SecondTeam").setPrefix(""+ChatListener.stringToColor(teamTwoColor));
		ScoreboardUtils.mainBoard.getTeam("Observers").setPrefix(""+ChatColor.AQUA);
		ScoreboardUtils.mainBoard.getTeam("Observers").setDisplayName(ChatColor.AQUA+"Observers");

		ScoreboardUtils.mainBoard.getTeam("FirstTeam").setDisplayName(ChatListener.stringToColor(teamOneColor)+teamOneName);
		ScoreboardUtils.mainBoard.getTeam("SecondTeam").setDisplayName(ChatListener.stringToColor(teamTwoColor)+teamTwoName);

		Objective mainObj = objectivesBoard.registerNewObjective("Objectives", "dummy");
		mainObj.setDisplaySlot(DisplaySlot.SIDEBAR);
		mainObj.setDisplayName(ChatColor.GOLD+"Objectives");
		scoreboardDisplays.add(ChatListener.stringToColor(teamOneColor)+teamOneName);
		scoreboardDisplays.add(ChatListener.stringToColor(teamTwoColor)+teamTwoName);
		
		ScoreboardUtils.organizeScoreboard(scoreboardDisplays, objectivesBoard);
		for(Player playersOnWorld : Bukkit.getWorld(nextMapFolder).getPlayers()){
			playersOnWorld.setScoreboard(objectivesBoard);
			ScoreboardUtils.mainBoard.getTeam("Observers").addPlayer(playersOnWorld);
			playersOnWorld.getInventory().clear();
			playersOnWorld.setGameMode(GameMode.CREATIVE);
		}
	}
}
