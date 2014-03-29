package cl.eilers.tatanpoker09.listeners;

import javax.xml.parsers.ParserConfigurationException;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scoreboard.Team;

import cl.eilers.tatanpoker09.map.MapXMLLoading;
import cl.eilers.tatanpoker09.match.Match;
import cl.eilers.tatanpoker09.objectives.WoolObjective;
import cl.eilers.tatanpoker09.utils.ScoreboardUtils;

public class BlockListener implements Listener{
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) throws ParserConfigurationException{
		boolean cancelEvent = false;
		if(!Bukkit.getServer().getWorlds().get(0).equals(event.getPlayer().getWorld())){
			if(!Match.getMatchStatus().equalsIgnoreCase("PLAYING")){
				cancelEvent = true;
			} else {
				if(Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(event.getPlayer()).getName().equals("Observers")){
					cancelEvent = true;
				}
				if(!event.getPlayer().getWorld().equals(Bukkit.getWorlds().get(0))){
					if(event.getBlock().getLocation().getY()>MapXMLLoading.getHeightLimit()){
						cancelEvent = true;
						event.getPlayer().sendMessage(ChatColor.RED+"You can not place blocks above height limit.");
					}
					for(WoolObjective wool : WoolObjective.getWools()){
						if(event.getBlock().equals(wool.getBlock())){
							if(ScoreboardUtils.mainBoard.getPlayerTeam(event.getPlayer()).equals(wool.getTeam())){
								if(!event.getBlock().getType().equals(Material.WOOL)){
									cancelEvent = true;
								} else {
									if(DyeColor.getByWoolData(event.getBlock().getData()).equals(wool.getColor())){
										Team playerTeam = ScoreboardUtils.mainBoard.getPlayerTeam(event.getPlayer());
										String color = ""+playerTeam.getDisplayName().charAt(0)+playerTeam.getDisplayName().charAt(1);
										wool.setPlaced(true);
										Bukkit.broadcastMessage(color+event.getPlayer().getName()+ChatColor.WHITE+" placed "+wool.getName().toUpperCase()+" for the "+wool.getTeam().getDisplayName());
										if(WoolObjective.teamHasWon(playerTeam)){
											Match.endMatch();
										}
										cancelEvent = false;
									} else {
										cancelEvent = true;
									}
								}
							} else {
								cancelEvent = true;
							}
						}
					}
					if(cancelEvent){
						event.setCancelled(true);
						event.getPlayer().sendMessage(ChatColor.RED+"You can only place the wool in here");
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
				for(WoolObjective wools : WoolObjective.getWools()){
					if(event.getBlock().equals(wools.getBlock())){
						event.setCancelled(true);
					}
				}
			}
		}
	}


}