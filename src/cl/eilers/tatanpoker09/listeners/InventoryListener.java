package cl.eilers.tatanpoker09.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import cl.eilers.tatanpoker09.match.Match;

public class InventoryListener implements Listener{
	@EventHandler
	public void onItemThrow(PlayerDropItemEvent event){
		if(!Bukkit.getServer().getWorlds().get(0).equals(event.getPlayer().getWorld())){
			if(Match.getMatchStatus().equals("PLAYING")){
				if(Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(event.getPlayer()).getName().equals("Observers")){
					event.setCancelled(true);
					event.getPlayer().getInventory().clear(event.getPlayer().getInventory().getHeldItemSlot());
				}
			} else {
				event.setCancelled(true);
				event.getPlayer().getInventory().clear(event.getPlayer().getInventory().getHeldItemSlot());
			}
		}
	}
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event){
		if(!Bukkit.getServer().getWorlds().get(0).equals(event.getPlayer().getWorld())){
			Player playerEventCaller = (Player)event.getPlayer();
			if(Match.getMatchStatus().equals("PLAYING")){
				if(Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(playerEventCaller).getName().equals("Observers")){
					event.setCancelled(true);
				}
			} else {
				event.setCancelled(true);
			}
		}
	}
	public void onInventoryAnimation(){

	}
}