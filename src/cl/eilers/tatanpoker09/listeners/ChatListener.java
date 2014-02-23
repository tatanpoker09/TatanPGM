package cl.eilers.tatanpoker09.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class ChatListener implements Listener{
	String team;
	ChatColor color;
	@EventHandler
	public void OnPlayerChat(AsyncPlayerChatEvent event){
		if(event.getPlayer().getWorld().getName().equals(Bukkit.getServer().getWorlds().get(0).getName())){
			team = "";
		} else {
			team = "[Team] ";
		}
		String ChatMessage = event.getMessage();
		String playerTeam = Bukkit.getServer().getScoreboardManager().getMainScoreboard().getPlayerTeam(event.getPlayer()).getName();

		switch(playerTeam){
		case("Observers"):
			color = ChatColor.AQUA;
		break;
		case("FirstTeam"):
			color = ChatColor.BLUE;
		break;
		case("SecondTeam"):
			color = ChatColor.DARK_RED;
		break;
		}
		for(Player playersOnWorld : event.getPlayer().getWorld().getPlayers()){
			if(Bukkit.getServer().getScoreboardManager().getMainScoreboard().getPlayerTeam(playersOnWorld).equals(Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(event.getPlayer()))){
				playersOnWorld.sendMessage(color+team+event.getPlayer().getName()+ChatColor.WHITE+": "+ChatMessage);
				System.out.println(color+team+event.getPlayer().getName()+ChatColor.WHITE+": "+ChatMessage);
			}
		}
		event.setCancelled(true);
	}


	public static ChatColor stringToColor(String str){
		if(!str.equals(null)){
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
		return ChatColor.AQUA;
	}
}