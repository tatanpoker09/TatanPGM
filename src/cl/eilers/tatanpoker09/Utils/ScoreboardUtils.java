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

import cl.eilers.tatanpoker09.listeners.ChatListener;
import cl.eilers.tatanpoker09.map.MapXMLLoading;

public class ScoreboardUtils {
	public static ScoreboardManager scoreBManager = Bukkit.getScoreboardManager();
	public static Scoreboard objectivesBoard;
	
	public static Scoreboard mainScoreboard = scoreBManager.getMainScoreboard();
	
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


	public static String getTeamName(Team team){
		String teamName = team.getDisplayName();
		return teamName;
	}
	public static void setTeamName(Team teamNumber, String name){

	}

	public static void joinTeam(Player playerToJoin, String teamToJoin){
		if(teamExists(teamToJoin)){
			Team firstTeam = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("FirstTeam");
			Team secondTeam = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("SecondTeam");
			Team observers = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Observers");
			if(StringUtils.containsIgnoreCase(firstTeam.getDisplayName(), teamToJoin)){
				firstTeam.addPlayer(playerToJoin);
				playerToJoin.sendMessage("You joined the "+getTeamName(firstTeam));
				
			}else if(StringUtils.containsIgnoreCase(secondTeam.getDisplayName(), teamToJoin)){	
				secondTeam.addPlayer(playerToJoin);
				playerToJoin.sendMessage("You joined the "+getTeamName(secondTeam));
			
			}else if((StringUtils.containsIgnoreCase(Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Observers").getDisplayName(), teamToJoin))){
				observers.addPlayer(playerToJoin);
				playerToJoin.sendMessage("You joined the "+ChatColor.AQUA+getTeamName(observers));
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
		System.out.println(teamInfo.toString());
		String teamOneColor = teamInfo[0][0];
		String teamTwoColor = teamInfo[1][0];
		//int teamOneMax = Integer.parseInt(teamInfo[0][1]);
		//int teamTwoMax = Integer.parseInt(teamInfo[0][1]);
		String teamOneName = teamInfo[0][2];
		String teamTwoName = teamInfo[1][2];

		//Initializing Scoreboard.
		Scoreboard objectivesBoard = scoreBManager.getNewScoreboard();
		//Teams
		mainScoreboard.getTeam("FirstTeam").setPrefix(""+ChatListener.stringToColor(teamOneColor));
		mainScoreboard.getTeam("SecondTeam").setPrefix(""+ChatListener.stringToColor(teamTwoColor));
		mainScoreboard.getTeam("Observers").setPrefix(""+ChatColor.AQUA);
		
		mainScoreboard.getTeam("FirstTeam").setDisplayName(ChatListener.stringToColor(teamOneColor)+teamOneName);
		mainScoreboard.getTeam("SecondTeam").setDisplayName(ChatListener.stringToColor(teamTwoColor)+teamTwoName);
		
		Objective mainObj = objectivesBoard.registerNewObjective("Objectives", "dummy");
		mainObj.setDisplaySlot(DisplaySlot.SIDEBAR);
		mainObj.setDisplayName(ChatColor.GOLD+"Objectives");
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