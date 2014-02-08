package cl.eilers.tatanpoker09.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class ChatListener implements Listener{
	ScoreboardManager manager = Bukkit.getScoreboardManager();
    Scoreboard board = manager.getNewScoreboard();
    Team blue = board.registerNewTeam("Blue Team");
    Team red = board.registerNewTeam("Red Team");
    String team;
    ChatColor color;
	@EventHandler
	public void OnPlayerChat(AsyncPlayerChatEvent event){
		if(event.getPlayer().getWorld().getName().equalsIgnoreCase("spawn")){
			team = "";
		} else {
			team = "[Team] ";
		}
		if(board.getPlayerTeam(event.getPlayer())==blue){
			color = ChatColor.BLUE;
		} else if(board.getPlayerTeam(event.getPlayer())==red) {
			color = ChatColor.RED;
		} else {
			color = ChatColor.AQUA;
		}
		String ChatMessage = event.getMessage();
		
		
		for(Player playersOnWorld : event.getPlayer().getWorld().getPlayers()){
			
			playersOnWorld.sendMessage(color+team+event.getPlayer().getName()+ChatColor.WHITE+": "+ChatMessage);
		}
		event.setCancelled(true);
	}
}