package cl.eilers.tatanpoker09.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cl.eilers.tatanpoker09.match.Match;

public class Start implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player playerSender = (Player)sender;
		if(!Bukkit.getServer().getWorlds().get(0).equals(playerSender.getWorld())){
			if(Match.getMatchStatus().equals("PREMATCH")){
				Match.startMatch();
			}
		} else {
			sender.sendMessage("Unknown command. Type "+'"'+"/help"+'"'+" for help.");
		}
			return true;
		}
	}
