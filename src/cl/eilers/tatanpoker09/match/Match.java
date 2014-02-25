package cl.eilers.tatanpoker09.match;


import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Team;

import cl.eilers.tatanpoker09.map.MapXMLLoading;
import cl.eilers.tatanpoker09.utils.ScoreboardUtils;

public class Match {
	public static boolean hasStarted = false;
	public static boolean hasEnded = false;
	public static String getMatchStatus(){
		if(hasEnded){
			return "END";
		}else if(hasStarted==false){
			return "PREMATCH";
		}else if(hasStarted){
			return "PLAYING";
		}
		return "ERROR";
	}
	
	public static void endMatch(){
		hasStarted = false;
		hasEnded = true;
	}
	public static void startMatch(){
		if(Match.getMatchStatus().equals("PREMATCH")){
			for(Team team : ScoreboardUtils.mainBoard.getTeams()){
				if(!team.getName().equals("Observers")){
					for(OfflinePlayer player : team.getPlayers()){
						if(player.getPlayer()!=null){
							player.getPlayer().setGameMode(GameMode.SURVIVAL);
						}
					}
				}
			}
		hasEnded = false;
		hasStarted = true;
		MapXMLLoading.spawnTeleporting();
		}
	}
}