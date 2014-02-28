package cl.eilers.tatanpoker09.listeners;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
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
	public void onBlockPlace(BlockPlaceEvent event){
		if(!Bukkit.getServer().getWorlds().get(0).equals(event.getPlayer().getWorld())){
			if(!Match.getMatchStatus().equalsIgnoreCase("PLAYING")){
				event.setCancelled(true);
			} else {
				if(Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(event.getPlayer()).getName().equals("Observers")){
					event.setCancelled(true);
				}
				try {
					Document mapXML = MapXMLLoading.LoadXML(Bukkit.getPluginManager().getPlugin("TatanPGM").getConfig().getString("TatanPGM.NextMap"));
					NodeList maxHeightNode = mapXML.getElementsByTagName("maxbuildheight");
					String maxHeightString = maxHeightNode.item(0).getTextContent();
					if(event.getBlock().getLocation().getY()-1>Integer.parseInt(maxHeightString)){
						event.setCancelled(true);
						event.getPlayer().sendMessage(ChatColor.RED+"You can not place blocks above height limit.");
					}
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
