package cl.eilers.tatanpoker09.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import cl.eilers.tatanpoker09.match.Match;

public class Start implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
			if(Match.getMatchStatus().equals("PREMATCH")){
				Match.startMatch();
			}
			return true;
		}
	}
