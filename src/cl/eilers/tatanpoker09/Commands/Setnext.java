package cl.eilers.tatanpoker09.commands;

import net.minecraft.util.org.apache.commons.lang3.StringUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import cl.eilers.tatanpoker09.map.MapLoader;
import cl.eilers.tatanpoker09.utils.CodingUtils;

public class Setnext implements CommandExecutor {
	public static String nextMap;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("Not enough arguments!");
			return false;
		}
		String mapName = CodingUtils.StringConcatenating(args);
		for(String mapList : MapLoader.mapNames){
			if(StringUtils.startsWithIgnoreCase(mapList, args[0])){
				nextMap = mapList;
			}
		}
		if(nextMap!=null){
			sender.sendMessage(nextMap+" Has been set for the next match!");
		} else {
			sender.sendMessage(ChatColor.RED+"Couldn't find map by the name: '"+ mapName+"'");
		}
		return true;
	}
}

