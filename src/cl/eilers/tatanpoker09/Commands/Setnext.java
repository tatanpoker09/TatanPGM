package cl.eilers.tatanpoker09.Commands;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import cl.eilers.tatanpoker09.Scrimmage;

public class Setnext implements CommandExecutor {
	private Scrimmage plugin;
	public Setnext(Scrimmage instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("Not enough arguments!");
			return false;
		}
		File mapDirExists = new File("maps");
		String mapName = "";
		//Checks if mapDirectory exists.
		if(mapDirExists.exists()){
			System.out.println("Found maps Directory!");
			int i = 0;
			while(i < args.length) {
				mapName += " " + args[i];
				i++;
			}
			mapName += mapName.substring(1);
			//Se necesita if para ver si existe
			plugin.getConfig().set("TatanPGM.NextMap", mapName);
			//Necesita un if! sender.sendMessage(ChatColor.RED + "Couldn't find map: " + ChatColor.BOLD + "'"+ mapName + "'");
		} else {
			sender.sendMessage("Maps directory didn't exist. Creating it for you");
			mapDirExists.mkdir();
		}
		return true;
	}

}
