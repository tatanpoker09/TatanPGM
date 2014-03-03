package cl.eilers.tatanpoker09.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandsListener implements Listener{
	@EventHandler
	public void OnCommand(PlayerCommandPreprocessEvent event){
		String commandString = event.getMessage();
		commandString=commandString.substring(1);
		if(event.getPlayer().getWorld().equals(Bukkit.getWorlds().get(0))){
			if(commandString.startsWith("kill") || commandString.startsWith("join") || commandString.startsWith("g") || commandString.startsWith("start")){
				event.setCancelled(true);
				event.getPlayer().sendMessage("Unknown command. Type "+'"'+"/help"+'"'+" for help.");
			}
		}
	}
}