package cl.eilers.tatanpoker09.utils;

import org.apache.commons.lang.StringUtils;
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
import cl.eilers.tatanpoker09.map.MapXMLLoading;

public class ScoreboardUtils {
	public static ScoreboardManager scoreBManager = Bukkit.getScoreboardManager();
	public static Scoreboard objectivesBoard;
	
	public static Scoreboard mainScoreboard = scoreBManager.getMainScoreboard();
	public static ChatColor getTeamColor(){
		return ChatColor.AQUA;
	}
	public static boolean mainTeamsExist(){
		if(Bukkit.getPluginManager().getPlugin("TatanPGM").getConfig().getBoolean("TatanPGM.HasCreatedTeams")){
			return true;
		}
		return false;
	}

	public static boolean teamExists(String teamString){
		if(StringUtils.containsIgnoreCase(mainScoreboard.getTeam("FirstTeam").getDisplayName(), teamString)){
			return true;
		} else if(StringUtils.containsIgnoreCase(mainScoreboard.getTeam("SecondTeam").getDisplayName(), teamString)){
			return true;
		} else if(StringUtils.containsIgnoreCase(mainScoreboard.getTeam("Observers").getDisplayName(), teamString)){
			return true;
		}
		return false;
	}


	public static String getTeamName(Team team, String nextMap){
		String teamName = null;
		String[][] teamInfo = MapXMLLoading.getTeamInfo(nextMap);
		if(team.getName().equals("FirstTeam")){
			teamName = teamInfo[0][2];
		} else {
			teamName = teamInfo[1][2];
		}
		return teamName;
	}
	public static void setTeamName(Team teamNumber, String name){

	}

	public static void joinTeam(Player playerToJoin, String teamToJoin){
		if(teamExists(teamToJoin)){
			if(StringUtils.containsIgnoreCase(Bukkit.getScoreboardManager().getMainScoreboard().getTeam("FirstTeam").getDisplayName(), teamToJoin)){
				Bukkit.getScoreboardManager().getMainScoreboard().getTeam("FirstTeam").addPlayer(playerToJoin);
			}else if(StringUtils.containsIgnoreCase(Bukkit.getScoreboardManager().getMainScoreboard().getTeam("SecondTeam").getDisplayName(), teamToJoin)){
				Bukkit.getScoreboardManager().getMainScoreboard().getTeam("SecondTeam").addPlayer(playerToJoin);
			}else if((StringUtils.containsIgnoreCase(Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Observers").getDisplayName(), teamToJoin))){
				Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Observers").addPlayer(playerToJoin);
			} else {
				playerToJoin.sendMessage("An unexpected error has ocurred.");
			}
		} else {
			System.out.println("Player:"+ playerToJoin.getName()+" can't join the team '"+teamToJoin+"' because it doesn't exist.");
		}
	}

	public static void initScoreboard(String nextMapFolder){
		//Getting info from XML
		String[][] teamInfo = MapXMLLoading.getTeamInfo(nextMapFolder);
		Scrimmage.teamInfo = teamInfo;
		//String teamOneColor = teamInfo[1][0];
		//String teamTwoColor = teamInfo[1][0];
		//String teamOneName = teamInfo[1][2];
		//String teamTwoName = teamInfo[2][2];

		//Initializing Scoreboard.
		Scoreboard objectivesBoard = scoreBManager.getNewScoreboard();
		//Teams
		mainScoreboard.getTeam("FirstTeam").setPrefix(""+ChatColor.BLUE);
		mainScoreboard.getTeam("SecondTeam").setPrefix(""+ChatColor.DARK_RED);
		mainScoreboard.getTeam("Observers").setPrefix(""+ChatColor.AQUA);
		
		mainScoreboard.getTeam("FirstTeam").setDisplayName("Blue Team");
		mainScoreboard.getTeam("SecondTeam").setDisplayName("Red Team");
		
		Objective mainObj = objectivesBoard.registerNewObjective("Objectives", "dummy");
		mainObj.setDisplaySlot(DisplaySlot.SIDEBAR);
		mainObj.setDisplayName(ChatColor.YELLOW+"Objectives");

		Score score = mainObj.getScore(Bukkit.getOfflinePlayer(mainScoreboard.getTeam("FirstTeam").getDisplayName()));
		score.setScore(5);
		score = mainObj.getScore(Bukkit.getOfflinePlayer(mainScoreboard.getTeam("SecondTeam").getDisplayName()));
		score.setScore(4);


		for(Player playersOnWorld : Bukkit.getWorld(nextMapFolder).getPlayers()){
			playersOnWorld.setScoreboard(objectivesBoard);
			Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Observers").addPlayer(playersOnWorld);
		}
	}
}