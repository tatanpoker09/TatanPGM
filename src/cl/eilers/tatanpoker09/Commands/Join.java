package cl.eilers.tatanpoker09.commands;


import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import cl.eilers.tatanpoker09.utils.ScoreboardUtils;

public class Join implements CommandExecutor{
	@SuppressWarnings("null")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(args.length>1){
			sender.sendMessage("Too many arguments!");
		}else if(args.length == 1){
				ScoreboardUtils.joinTeam((Player)sender, args[0]);
		}else{
			Set<Team> teams = ScoreboardUtils.objectivesBoard.getTeams();
			Team[] teamArray = null;
			teams.toArray(teamArray);
			int t1PlayerCount = teamArray[0].getPlayers().size();
			int t2PlayerCount = teamArray[1].getPlayers().size();
			if(t1PlayerCount == t2PlayerCount){
				ScoreboardUtils.objectivesBoard.getTeam("Team One").addPlayer((Player)sender);
			}else{
				if(Math.max(t1PlayerCount, t2PlayerCount) == t1PlayerCount){
					ScoreboardUtils.objectivesBoard.getTeam("First Team").addPlayer((Player)sender);
				}else{
					ScoreboardUtils.objectivesBoard.getTeam("Second Team").addPlayer((Player)sender);
				}
			}
		}
		return true;
	}

}
