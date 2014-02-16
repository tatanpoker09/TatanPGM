package cl.eilers.tatanpoker09.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import cl.eilers.tatanpoker09.Scrimmage;
import cl.eilers.tatanpoker09.listeners.ChatListener;
import cl.eilers.tatanpoker09.map.MapXMLLoading;

public class ScoreboardUtils {
	public static ScoreboardManager scoreBManager;
	public static Scoreboard objectivesBoard;
	
	public static void initScoreboard(String nextMapFolder){
		//Getting info from XML
		String[][] teamInfo = MapXMLLoading.getTeamInfo(nextMapFolder);
		Scrimmage.teamInfo = teamInfo;
		String teamOneColor = teamInfo[0][0];
		String teamTwoColor = teamInfo[0][1];
		String teamOneName = teamInfo[0][2];
		String teamTwoName = teamInfo[0][1];
		
		//Initializing Scoreboard.
		ScoreboardManager scoreBManager = Bukkit.getScoreboardManager();
		Scoreboard objectivesBoard = scoreBManager.getNewScoreboard();
		
		//Teams
		Team teamOne = ScoreboardUtils.objectivesBoard.registerNewTeam("FirstTeam");
		Team teamTwo = ScoreboardUtils.objectivesBoard.registerNewTeam("SecondTeam");
		Team observers = ScoreboardUtils.objectivesBoard.registerNewTeam("Observers");
		
		teamOne.setPrefix(ChatListener.stringToColor(teamOneColor)+"");
		teamTwo.setPrefix(ChatListener.stringToColor(teamTwoColor)+"");
		observers.setPrefix(ChatColor.AQUA+"");
		
		Objective mainObj = objectivesBoard.registerNewObjective("Objectives", "dummy");
		mainObj.setDisplaySlot(DisplaySlot.SIDEBAR);
		mainObj.setDisplayName(ChatColor.YELLOW+"Objectives");
		
		Score score = mainObj.getScore(Bukkit.getOfflinePlayer(teamOneName));
		score.setScore(5);
		score = mainObj.getScore(Bukkit.getOfflinePlayer(teamTwoName));
		score.setScore(4);
		
		for(Player playersOnWorld : Bukkit.getWorld(nextMapFolder).getPlayers()){
			playersOnWorld.setScoreboard(objectivesBoard);
			observers.addPlayer(playersOnWorld);
		}
	}
	
	public static boolean teamExists(Team team){
		return true;
	}
	
	public static void joinTeam(Player playerToJoin, String teamToJoin){
		if(teamExists(playerToJoin.getScoreboard().getTeam(teamToJoin))== true){
		playerToJoin.getScoreboard().getTeam(teamToJoin).addPlayer(playerToJoin);
		}
	}
}
