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
}