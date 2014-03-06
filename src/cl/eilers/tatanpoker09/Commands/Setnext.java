package cl.eilers.tatanpoker09.commands;

import net.minecraft.util.org.apache.commons.lang3.StringUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import cl.eilers.tatanpoker09.Scrimmage;
import cl.eilers.tatanpoker09.utils.CodingUtils;

public class Setnext implements CommandExecutor {
	public static String nextMap;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		nextMap = null;
		if (args.length == 0) {
			sender.sendMessage("Not enough arguments!");
			return false;
		}
		String mapName = CodingUtils.StringConcatenating(args);
		if(Scrimmage.mapNames!=null){
			if(Scrimmage.mapNames.size()>0){
				for(String mapList : Scrimmage.mapNames){
					if(StringUtils.startsWithIgnoreCase(mapList, mapName)){
						nextMap = mapList;
					}
				}
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

