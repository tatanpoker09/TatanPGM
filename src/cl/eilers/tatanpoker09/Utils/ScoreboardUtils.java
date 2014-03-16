package cl.eilers.tatanpoker09.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

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
						playerToJoin.sendMessage(ChatColor.GRAY+"You joined the "+getTeamName(team));
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
			playerToJoin.sendMessage(ChatColor.RED+"No teams matched query.");
		}
	}
	
	public static void organizeScoreboard(ArrayList<String> args, Scoreboard board){
		Collections.reverse(args);
		for(int amount = args.size();amount>0;amount--){
			Score score = board.getObjective(DisplaySlot.SIDEBAR).getScore(Bukkit.getOfflinePlayer(args.get(amount-1)));
			score.setScore(amount-1);
		}
	}
	
	public static String getMapName(File map){
		String mapName = null;
		Document mapXML = MapXMLLoading.LoadXML(map);
		NodeList name = mapXML.getElementsByTagName("name");
		mapName = name.item(0).getTextContent();
		return mapName;

	}
	
}