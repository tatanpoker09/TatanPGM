package cl.eilers.tatanpoker09.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import cl.eilers.tatanpoker09.utils.CodingUtils;

public class G implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String stringArgs = CodingUtils.StringConcatenating(args);
		Bukkit.broadcastMessage("<"+sender.getName()+">: "+stringArgs);
		return false;
	}
	

}
