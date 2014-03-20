package cl.eilers.tatanpoker09.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cl.eilers.tatanpoker09.map.MapXMLLoading;

public class Scrimmage implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(args.length>0){
			sender.sendMessage("Too many arguments!");
		} else {
			if(MapXMLLoading.getCurrentMap()!=null){
				Player playerSender = (Player)sender;
				playerSender.teleport(Bukkit.getWorld(MapXMLLoading.getCurrentMap().getName()).getSpawnLocation());
			}
		}
		return true;
	}
}
