package cl.eilers.tatanpoker09.utils;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import cl.eilers.tatanpoker09.listeners.ChatListener;
import cl.eilers.tatanpoker09.map.MapXMLLoading;
import cl.eilers.tatanpoker09.match.Match;

public class ScoreboardUtils {
	public static ScoreboardManager scoreBManager = Bukkit.getScoreboardManager();
	public static Scoreboard mainBoard = scoreBManager.getMainScoreboard();

	public static boolean mainTeamsExist(){
		if(mainBoard.getTeams().size()>0){
			return true;
		}
		return false;
	}

	public static boolean teamExists(String teamString){
		for(Team team : mainBoard.getTeams()){
			if(StringUtils.containsIgnoreCase(team.getDisplayName(), teamString)){
				return true;
			}
		}
		return false;
	}

	public static String getTeamName(Team team){
		return team.getDisplayName();
	}

	public static void setTeamName(String currentName, String futureName){
		for(Team team : mainBoard.getTeams()){
			if(team.getDisplayName().contains(currentName)){
				team.setDisplayName(futureName);
			} else {
				System.out.println("Team not found.");
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static void joinTeam(Player playerToJoin, String teamToJoin){
		if(teamExists(teamToJoin)){
			for(Team team : mainBoard.getTeams()){
				if(net.minecraft.util.org.apache.commons.lang3.StringUtils.startsWithIgnoreCase(ChatColor.stripColor(team.getDisplayName()), teamToJoin)){
					if(!ScoreboardUtils.mainBoard.getPlayerTeam(playerToJoin).getDisplayName().equals(team.getDisplayName())){
						team.addPlayer(playerToJoin);
						playerToJoin.sendMessage("You joined the "+getTeamName(team));
						if(Match.getMatchStatus().equalsIgnoreCase("PLAYING")){
							playerToJoin.setHealth(0);
							if(team.getName().equals("Observers")){
								playerToJoin.setGameMode(GameMode.CREATIVE);
							} else {
								playerToJoin.setGameMode(GameMode.SURVIVAL);
							}
						}
						break;
					}
				}
			}
		} else {
			System.out.println("Player:"+ playerToJoin.getName()+" can't join the team '"+teamToJoin+"' because it doesn't exist.");
		}
	}

	public static void initScoreboard(String nextMapFolder){
		//Getting info from XML
		String[][] teamInfo = MapXMLLoading.getTeamInfo(nextMapFolder);
		String teamOneColor = teamInfo[0][0];
		//int teamOneMax = Integer.parseInt(teamInfo[0][1]);
		String teamOneName = teamInfo[0][2];
		String teamTwoColor = teamInfo[1][0];
		//int teamTwoMax = Integer.parseInt(teamInfo[1][1]);
		String teamTwoName = teamInfo[1][2];

		//Initializing Scoreboard.
		Scoreboard objectivesBoard = scoreBManager.getNewScoreboard();
		//Teams
		mainBoard.getTeam("FirstTeam").setPrefix(""+ChatListener.stringToColor(teamOneColor));
		mainBoard.getTeam("SecondTeam").setPrefix(""+ChatListener.stringToColor(teamTwoColor));
		mainBoard.getTeam("Observers").setPrefix(""+ChatColor.AQUA);
		mainBoard.getTeam("Observers").setDisplayName(ChatColor.AQUA+"Observers");

		mainBoard.getTeam("FirstTeam").setDisplayName(ChatListener.stringToColor(teamOneColor)+teamOneName);
		mainBoard.getTeam("SecondTeam").setDisplayName(ChatListener.stringToColor(teamTwoColor)+teamTwoName);

		Objective mainObj = objectivesBoard.registerNewObjective("Objectives", "dummy");
		mainObj.setDisplaySlot(DisplaySlot.SIDEBAR);
		mainObj.setDisplayName(ChatColor.GOLD+"Objectives");
		Score score = mainObj.getScore(Bukkit.getOfflinePlayer(mainBoard.getTeam("FirstTeam").getDisplayName()));
		score.setScore(5);
		score = mainObj.getScore(Bukkit.getOfflinePlayer(mainBoard.getTeam("SecondTeam").getDisplayName()));
		score.setScore(4);
		for(Player playersOnWorld : Bukkit.getWorld(nextMapFolder).getPlayers()){
			playersOnWorld.setScoreboard(objectivesBoard);
			mainBoard.getTeam("Observers").addPlayer(playersOnWorld);
			playersOnWorld.getInventory().clear();
			playersOnWorld.setGameMode(GameMode.CREATIVE);
		}
	}
}