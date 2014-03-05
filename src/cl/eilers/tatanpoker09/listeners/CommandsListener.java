package cl.eilers.tatanpoker09.listeners;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import cl.eilers.tatanpoker09.map.MapLoader;

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
		if(commandString.startsWith("stop")){
			for(Player player : Bukkit.getOnlinePlayers()){
				player.kickPlayer("Server is Restarting.");
			}
			for(World world : Bukkit.getWorlds()){
				if(world.getName().startsWith("playing")){
					MapLoader.deleteWorld(world.getName());
				}

			}
		}
	}
	@EventHandler
	public void OnConsoleCommand(ServerCommandEvent event){
		if(event.getCommand().startsWith("stop")){
			for(Player player : Bukkit.getOnlinePlayers()){
				player.kickPlayer("Server is Restarting.");
			}
			for(World world : Bukkit.getWorlds()){
				if(world.getName().startsWith("playing")){
					MapLoader.deleteWorld(world.getName());
				}

			}		
		}

	}
}