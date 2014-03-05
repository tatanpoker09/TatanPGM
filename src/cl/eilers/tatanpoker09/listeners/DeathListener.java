package cl.eilers.tatanpoker09.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import cl.eilers.tatanpoker09.map.MapXMLLoading;
import cl.eilers.tatanpoker09.utils.ScoreboardUtils;

public class DeathListener implements Listener{
	public static String playerWorldName = null;
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event){
		Location spawnLocation = MapXMLLoading.getSpawnLocation(ScoreboardUtils.mainBoard.getPlayerTeam(event.getPlayer()));
		event.setRespawnLocation(spawnLocation);
		playerWorldName = event.getPlayer().getWorld().getName();
	}
	@EventHandler
	public void onPlayerDeath(EntityDeathEvent event){
	}
}
