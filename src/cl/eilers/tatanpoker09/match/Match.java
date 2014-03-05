package cl.eilers.tatanpoker09.match;


import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
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

	public static String getCurrentMap(){
		String currentMap = null;
		if(Bukkit.getOnlinePlayers().length>1){
			for(World maps : Bukkit.getWorlds()){
				System.out.println("1");
				if(maps.getName().startsWith("playing")){
					System.out.println("2");
					if(maps.getPlayers().size()>0){
						System.out.println("3");
						currentMap = maps.getName();
					}
				}
			}
		}
		return currentMap;
	}

		public static void endMatch(){
			if(Match.getMatchStatus().equals("PLAYING")){
				for(Team team : ScoreboardUtils.mainBoard.getTeams()){
					if(!team.getName().equals("Observers")){
						for(OfflinePlayer player : team.getPlayers()){
							if(player.getPlayer()!=null){
								player.getPlayer().setGameMode(GameMode.CREATIVE);
							}
						}
					}
				}
				hasStarted = true;
				hasEnded = true;
			}
		}
		public static void startMatch(){
			if(Match.getMatchStatus().equals("PREMATCH")){
				for(Team team : ScoreboardUtils.mainBoard.getTeams()){
					if(!team.getName().equals("Observers")){
						for(OfflinePlayer player : team.getPlayers()){
							if(player.getPlayer()!=null){
								player.getPlayer().setGameMode(GameMode.SURVIVAL);
								player.getPlayer().getInventory().clear();
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