package cl.eilers.tatanpoker09.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandsListener implements Listener{
	@EventHandler
	public void OnCommand(PlayerCommandPreprocessEvent event){
		String commandString = event.getMessage();
		commandString=commandString.substring(1);
		if(commandString.contains("kill")){
			if(!event.getPlayer().getWorld().getName().equalsIgnoreCase("spawn")){
				event.setCancelled(true);
			}
		}
	}
}