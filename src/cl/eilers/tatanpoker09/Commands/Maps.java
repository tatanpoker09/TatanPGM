package cl.eilers.tatanpoker09.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import cl.eilers.tatanpoker09.Scrimmage;

public class Maps implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(Scrimmage.mapNames!=null){
			if(Scrimmage.mapNames.size()>0){
				for(String map : Scrimmage.mapNames){
					sender.sendMessage(map);
				}
			} else {
				Scrimmage.mapsLoad();
				sender.sendMessage("Maps have been loaded. Re-use the command.");
			}
		} else {
			Scrimmage.mapsLoad();
			sender.sendMessage("Maps have been loaded. Re-use the command.");
		}
		return true;
	}
}