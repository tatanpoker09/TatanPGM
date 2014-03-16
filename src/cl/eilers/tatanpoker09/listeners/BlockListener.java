package cl.eilers.tatanpoker09.listeners;

import javax.xml.parsers.ParserConfigurationException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import cl.eilers.tatanpoker09.map.MapXMLLoading;
import cl.eilers.tatanpoker09.match.Match;

public class BlockListener implements Listener{
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) throws ParserConfigurationException{
		if(!Bukkit.getServer().getWorlds().get(0).equals(event.getPlayer().getWorld())){
			if(!Match.getMatchStatus().equalsIgnoreCase("PLAYING")){
				event.setCancelled(true);
			} else {
				if(Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(event.getPlayer()).getName().equals("Observers")){
					event.setCancelled(true);
				}
				/*if(Objective.getType().equals("Capture The Wool")){
					//if(event.getBlock().equals())
				}*/
				if(!event.getPlayer().getWorld().equals(Bukkit.getWorlds().get(0))){
					if(event.getBlock().getLocation().getY()>MapXMLLoading.getHeightLimit()){
						event.setCancelled(true);
						event.getPlayer().sendMessage(ChatColor.RED+"You can not place blocks above height limit.");
					}
				}
			}
		}
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		if(!Bukkit.getServer().getWorlds().get(0).equals(event.getPlayer().getWorld())){
			if(!Match.getMatchStatus().equalsIgnoreCase("PLAYING")){
				event.setCancelled(true);
			} else {
				if(Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(event.getPlayer()).getName().equals("Observers")){
					event.setCancelled(true);
				}
			}
		}
	}
}