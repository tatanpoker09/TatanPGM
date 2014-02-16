package cl.eilers.tatanpoker09.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scoreboard.Team;

import cl.eilers.tatanpoker09.Scrimmage;

public class ChatListener implements Listener{
    String team;
    ChatColor color;
	@EventHandler
	public void OnPlayerChat(AsyncPlayerChatEvent event){
		if(event.getPlayer().getWorld().getName().equalsIgnoreCase("spawn")){
			team = "";
		} else {
			team = "[Team] ";
		}
		String ChatMessage = event.getMessage();
		Team playerTeam = event.getPlayer().getScoreboard().getPlayerTeam(event.getPlayer());
		String teamName = playerTeam.getName();
		switch(teamName){
		case("Observers"):
			color = ChatColor.AQUA;
		case("Team One"):
			color = stringToColor(Scrimmage.teamInfo[0][0]);
			break;
		case("Team Two"):
			color = stringToColor(Scrimmage.teamInfo[1][0]);
			break;
		default:
			color = ChatColor.AQUA;
		}
		for(Player playersOnWorld : event.getPlayer().getWorld().getPlayers()){
			
			playersOnWorld.sendMessage(color+team+event.getPlayer().getName()+ChatColor.WHITE+": "+ChatMessage);
		}
		event.setCancelled(true);
	}
	
	public static ChatColor stringToColor(String str){
		switch(str){
		case("black"):
			return ChatColor.BLACK;
		case("dark blue"):
			return ChatColor.DARK_BLUE;
		case("blue"):
			return ChatColor.BLUE;
		case("dark green"):
			return ChatColor.DARK_GREEN;
		case("dark aqua"):
			return ChatColor.DARK_AQUA;
		case("dark red"):
			return ChatColor.DARK_RED;
		case("dark purple"):
			return ChatColor.DARK_PURPLE;
		case("gold"):
			return ChatColor.GOLD;
		case("gray"):
			return ChatColor.GRAY;
		case("dark gray"):
			return ChatColor.DARK_GRAY;
		case("green"):
			return ChatColor.GREEN;
		case("aqua"):
			return ChatColor.AQUA;
		case("red"):
			return ChatColor.RED;
		case("light purple"):
			return ChatColor.LIGHT_PURPLE;
		case("yellow"):
			return ChatColor.YELLOW;
		case("white"):
			return ChatColor.WHITE;
		default:
			return ChatColor.AQUA;
		}
	}
}