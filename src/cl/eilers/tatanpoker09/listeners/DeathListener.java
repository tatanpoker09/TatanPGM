package cl.eilers.tatanpoker09.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import cl.eilers.tatanpoker09.map.MapXMLLoading;
import cl.eilers.tatanpoker09.utils.ScoreboardUtils;

public class DeathListener implements Listener{
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event){
		event.setRespawnLocation(MapXMLLoading.getSpawnLocation(ScoreboardUtils.mainBoard.getPlayerTeam(event.getPlayer())));
	}
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		event.getEntity().sendMessage("LEL MORISTE ERES NUV");
	}
}
