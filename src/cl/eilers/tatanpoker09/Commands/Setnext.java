package cl.eilers.tatanpoker09.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import cl.eilers.tatanpoker09.Scrimmage;
import cl.eilers.tatanpoker09.utils.CodingUtils;

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
		String mapName = CodingUtils.StringConcatenating(args);
			File src = new File("maps/"+mapName);
			if(src.exists()){
				sender.sendMessage(mapName);
				plugin.getConfig().set("TatanPGM.NextMap", mapName);
				plugin.saveConfig();
				sender.sendMessage(ChatColor.GREEN+plugin.getConfig().getString("TatanPGM.NextMap") + " Has been set for the next match!");
			} else {
				sender.sendMessage(ChatColor.RED+"Couldn't find map by the name: '"+ mapName+"'");
			}
		return true;
	}
}

