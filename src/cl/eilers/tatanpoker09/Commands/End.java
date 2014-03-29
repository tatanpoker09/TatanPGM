package cl.eilers.tatanpoker09.commands;

import org.bukkit.command.Command; 
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import cl.eilers.tatanpoker09.match.Match;

public class End implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(Match.getMatchStatus().equalsIgnoreCase("PLAYING")){
		Match.endMatch();
		} else {
			sender.sendMessage("There is no match running.");
		}
		return true;
	}
}
