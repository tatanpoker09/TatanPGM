package cl.eilers.tatanpoker09.commands;

import org.bukkit.Bukkit; 
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import cl.eilers.tatanpoker09.utils.CodingUtils;
import cl.eilers.tatanpoker09.utils.ScoreboardUtils;

public class G implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String stringArgs = CodingUtils.StringConcatenating(args);
		Player playerSender = (Player)sender;
		Team playerTeam = ScoreboardUtils.mainBoard.getPlayerTeam(playerSender);
		String color = ""+playerTeam.getDisplayName().charAt(0)+playerTeam.getDisplayName().charAt(1);
		Bukkit.broadcastMessage("<"+color+sender.getName()+ChatColor.WHITE+">: "+stringArgs);
		return false;
	}
	

}
